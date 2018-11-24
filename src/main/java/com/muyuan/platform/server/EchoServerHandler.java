package com.muyuan.platform.server;

import com.muyuan.platform.biz.Analysis;
import com.muyuan.platform.common.util.ByteUtils;
import com.muyuan.platform.common.util.HexTwoUtils;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 范文武
 * @date 2018/11/17 18:49
 */
@ChannelHandler.Sharable
@Slf4j
@Component
public class EchoServerHandler extends ChannelHandlerAdapter {

    @Autowired
    private Analysis analysis;

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        byte[] receiveMsgBytes = new byte[in.readableBytes()];
        in.readBytes(receiveMsgBytes);
        String hex = ByteUtils.byteToHexStr(receiveMsgBytes);
        log.info(getReturn(ctx) + "\nServer Received:" + hex);
        byte[] answer = analysis.commonAnalysis(hex);
        ctx.writeAndFlush(Unpooled.copiedBuffer(answer));
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
        log.info(getReturn(ctx) + " 已连接");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        channels.remove(ctx.channel());
        log.info(getReturn(ctx) + " 连接断开");
    }

    /**
     * 得到公共返回
     * @author fww
     */
    private String getReturn(ChannelHandlerContext ctx) {
        return format.format(new Date()) + "  " + ctx.channel().remoteAddress();
    }
}
