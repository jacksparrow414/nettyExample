package com.example.netty.helloworld.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.SneakyThrows;

/**
 * 服务端server.
 *
 * @author duhongbo
 * @date 2020/8/21 11:50
 */
public class HelloWorldServer {
    
    private int port;
    
    public HelloWorldServer(final int port) {
        this.port = port;
    }
    
    @SneakyThrows
    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerLoop = new NioEventLoopGroup();
        try {
            ServerBootstrap server = new ServerBootstrap();
            server.group(bossGroup, workerLoop).channel(NioServerSocketChannel.class).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(final SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new HelloWorldServerHandler());
                }
            }).option(ChannelOption.SO_BACKLOG, 128).childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = server.bind(port).sync();
            f.channel().closeFuture().sync();
        }finally {
            workerLoop.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    
    }
    
    public static void main(String[] args) {
       int port = 8080;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        new HelloWorldServer(port).run();
    }
}
