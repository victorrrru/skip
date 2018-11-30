package com.muyuan.platform.skip.server;

import com.mchange.lang.ByteUtils;
import com.muyuan.platform.skip.biz.Analysis;
import com.muyuan.platform.skip.biz.ChannelMap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 范文武
 * @date 2018/11/17 18:49
 */
@Slf4j
@Component
@ChannelHandler.Sharable
public class EchoServerHandler extends ChannelHandlerAdapter {

    @Autowired
    private Analysis analysis;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        byte[] receiveMsgBytes = new byte[in.readableBytes()];
        in.readBytes(receiveMsgBytes);
        String receive = ByteUtils.toHexAscii(receiveMsgBytes);
        log.info(getReturn(ctx) + "\nServer Received:" + receive);
        String answer = analysis.commonAnalysis(getIp(ctx), receive);
        if (StringUtils.isNotBlank(answer)) {
            log.info(getReturn(ctx) + "\nServer answer:" + answer);
            ctx.writeAndFlush(Unpooled.copiedBuffer(ByteUtils.fromHexAscii(answer)));
        }
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        ChannelMap.addChannel(getIp(ctx), ctx.channel());
        log.info(getReturn(ctx) + " 已连接");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        ChannelMap.removeChannel(getIp(ctx));
        log.info(getReturn(ctx) + " 连接断开");
    }

    /**
     * 得到公共返回
     * @author fww
     */
    private String getReturn(ChannelHandlerContext ctx) {
        return format.format(new Date()) + "  " + ctx.channel().remoteAddress();
    }

    /**
     * 得到客户端ip
     * @author fww
     */
    private String getIp(ChannelHandlerContext ctx) {
        String address = ctx.channel().remoteAddress().toString();
        return address.substring(address.indexOf("/") + 1);
    }
}
