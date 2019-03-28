package com.zwq.websocket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;


/**
 * Created by IntelliJ IDEA
 *
 * @Description:
 * @Author: zwq
 * @Date: 2019年03月27日
 * @Time: 23:46
 */
public class WSServerInitializer extends ChannelInitializer<SocketChannel> {


    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //websocket基于http协议,所有要有http编解码器
        pipeline.addLast(new HttpServerCodec());
        //对写大数据流的支持
        pipeline.addLast(new ChunkedWriteHandler());

        //对httpMessage进行聚合,聚合成FullHTTPRequest或FullHTTPResponse,几乎netty的编程中,都会用到此Handler
        pipeline.addLast(new HttpObjectAggregator(1024 * 64));
//=====================================以上是用于支持http协议================================================

/**
 * websocket服务器处理的协议, 用于指定给客户端连接访问的路由: /ws
 * 本Handler会帮你处理繁重的复杂的事
 * 会帮你处理握手动作:handshaking(close,ping,pong) ping+pong=心跳
 * 对于websocket来讲,都是以frames进行传输,不同数据类型对应frames不同
 *
 */
 pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

 //自定义Handler
        pipeline.addLast(new ChatHandler());



 }

 }