package com.example.netty.helloworld.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;

/**
 * @author duhongbo
 * @date 2020/8/21 14:13
 */
public class HelloWorldClient {
    
    static final String HOST = "127.0.0.1";
    static final int PORT = 8080;
    
    @SneakyThrows
    public static void main(String[] args) {
        EventLoopGroup workerLoop = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            String msg = "hello world";
            bootstrap.group(workerLoop).channel(NioSocketChannel.class).handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(final SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new HelloWorldClientHandler());
                }
            }).option(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture f = bootstrap.connect(HOST, PORT).sync();
            f.channel().writeAndFlush(Unpooled.copiedBuffer(msg.getBytes()));
            f.channel().closeFuture().sync();
        }finally {
            workerLoop.shutdownGracefully();
        }
    }
    
}
