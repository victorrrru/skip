package com.muyuan.platform.skip.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetSocketAddress;

/**
 * @author 范文武
 * @date 2018/11/17 19:00
 */
@Slf4j
@Component
public class EchoServer {

    @Autowired
    private EchoServerHandler echoServerHandler;

    @Value("${skip.server.port}")
    private Integer port;

    @PostConstruct
    public void start() throws Exception {
        /**
         * boss 线程组用于处理连接工作
         */
        EventLoopGroup boss = new NioEventLoopGroup();
        /**
         * work 线程组用于数据处理
         */
        EventLoopGroup work = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .localAddress(new InetSocketAddress(port))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(echoServerHandler);
                    }
                });
        ChannelFuture future = serverBootstrap.bind().sync();
        if (future.isSuccess()) {
            log.info("-------------服务器启动----------------");
        }
    }
}
