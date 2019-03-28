package com.zwq.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by IntelliJ IDEA
 *
 * @Description:
 * @Author: zwq
 * @Date: 2019年03月27日
 * @Time: 22:06
 */
//客户端发请求,服务器返回hello netty
public class HelloServer {
    public static void main(String[] args) throws InterruptedException {
        //定义一对线程组
        //定义主线程组,用于接收客户端连接,但是不做任何处理,跟老板一样不做事
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //从线程组,老板线程组会把任务丢给他,让手下线程去做任务
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //netty服务器的创建,ServerBootstrap是一个启动类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup)//设置主线程组
                    .channel(NioServerSocketChannel.class)//设置nio的双向通道
                    .childHandler(new HelloServerInitializer());//子处理器,用于处理workerGroup
            //启动server,并设置8088为启动端口,同时启动方式为同步
            ChannelFuture channelFuture = null;

            channelFuture = serverBootstrap.bind(8088).sync();
            //监听关闭的channel,同时启动方式为同步
            channelFuture.channel().closeFuture().sync();
        } finally {
           bossGroup.shutdownGracefully();
           workerGroup.shutdownGracefully();
        }


    }
}
