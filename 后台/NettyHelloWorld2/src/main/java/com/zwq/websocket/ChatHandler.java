package com.zwq.websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.time.LocalDateTime;


/**
 * Created by IntelliJ IDEA
 *
 * @Description:
 * @Author: zwq
 * @Date: 2019年03月28日
 * @Time: 7:53
 */
//处理消息的Handler
    //TextWebSocketFrame: 在netty中是用于为websocket专门处理文本的对象,frame是消息的载体.
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{


    private static ChannelGroup clients =
            new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    /**
     * 当客户端连接服务端后,(打开连接)
     * 获取客户端的channel,并且放到channelGroup中进行管理
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        clients.add(ctx.channel());
    }

    /**
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        //当触发handlerRemoved,channelGroup会自动移除对应客户端的channel
       //clients.remove(ctx.channel());

        System.out.println("客户端断开,channel对应的长ID" + ctx.channel().id().asLongText());
        System.out.println("客户端断开,channel对应的短ID" + ctx.channel().id().asShortText());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {

        //获取客户端传输过来的信息
        String content = msg.text();
        System.out.println("接收到的数据:"+content);

//        for(Channel channel:clients){
//            clients.writeAndFlush(new TextWebSocketFrame("[服务器在]"
//                    + LocalDateTime.now()+"接收到消息,"+"消息为:"+content));
//        }
//下面这个方法和上面for循环一致
        clients.writeAndFlush(new TextWebSocketFrame("[服务器在]"
               + LocalDateTime.now()+"接收到消息,"+"消息为:"+content));

    }
}
