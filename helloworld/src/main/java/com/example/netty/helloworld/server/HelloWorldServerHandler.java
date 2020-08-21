package com.example.netty.helloworld.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;

import javax.sound.midi.Soundbank;
import java.nio.charset.StandardCharsets;

/**
 * 服务端处理handler.
 *
 * @author duhongbo
 * @date 2020/8/21 11:39
 */
@Slf4j
public class HelloWorldServerHandler extends ChannelInboundHandlerAdapter {
    
    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        System.out.println("通道准备接收客户端数据....");
    }
    
    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        byte [] by = new byte [in.readableBytes()];
        in.readBytes(by);
        String body=new String(by, StandardCharsets.UTF_8);
        String response="这是服务端返回的数据"+body;
        System.out.println("服务器收到数据："+body);
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
    }
    
    @Override
    public void channelReadComplete(final ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端数据读取完毕.....");
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        cause.printStackTrace();
        log.error("客户端断开连接.....", cause);
        ctx.close();
    }
}
