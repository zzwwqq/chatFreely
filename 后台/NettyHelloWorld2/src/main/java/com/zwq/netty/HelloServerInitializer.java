package com.zwq.netty;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * Created by IntelliJ IDEA
 *
 * @Description:
 * @Author: zwq
 * @Date: 2019年03月27日
 * @Time: 22:24
 */

//初始化器,channel注册后,会执行里面的初始化方法
public class HelloServerInitializer extends ChannelInitializer {

    @Override
    protected void initChannel(Channel channel) throws Exception {
        //通过SocketChannel去获得相应的管道
        ChannelPipeline pipeline = channel.pipeline();

        //通过管道,添加Handler
        //HttpServerCodec是有netty自己提供的助手类,可以理解为拦截器
        //当请求到服务端,需要做解码,响应到客户端做编码
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());

        //添加自定义的助手类,返回"hello netty"
        pipeline.addLast("customHandler",new CustomerHandler() );




    }
}
