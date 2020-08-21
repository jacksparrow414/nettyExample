package com.example.netty.helloworld.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

/**
 * @author duhongbo
 * @date 2020/8/21 14:04
 */
@Slf4j
public class HelloWorldClientHandler extends ChannelInboundHandlerAdapter {
    
    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        byte [] by = new byte [in.readableBytes()];
        in.readBytes(by);
        String body=new String(by, StandardCharsets.UTF_8);
        System.out.println("接收服务器端返回信息："+body);
    }
    
    @Override
    public void channelReadComplete(final ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端读取数据完毕.....");
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        cause.printStackTrace();
        log.error("无法连接到服务器....", cause);
        ctx.close();
    }
}
