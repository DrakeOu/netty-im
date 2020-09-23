package server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.Session;
import protocol.command.MessageRequestPacket;
import protocol.command.MessageResponsePacket;
import utils.SessionUtil;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {
        //1. 拿到发送方的会话
        Session session = SessionUtil.getSession(ctx.channel());

        //2. 构造发送给指定用户的消息
        MessageResponsePacket responsePacket = new MessageResponsePacket();
        System.out.println("Message:" + messageRequestPacket.getMessage());
        System.out.println("FromId:" + session.getUserId());
        System.out.println("ToId:"+messageRequestPacket.getToUserId());
        responsePacket.setMessage(messageRequestPacket.getMessage());
        responsePacket.setFromUser(session.getUserName());
        responsePacket.setFromUserId(session.getUserId());
        //3. 发送给指定用户
        Channel toUserChannel = SessionUtil.getChannel(messageRequestPacket.getToUserId());
        if(toUserChannel != null && SessionUtil.hasLogin(toUserChannel)){
            toUserChannel.writeAndFlush(responsePacket);
        }else{
            responsePacket.setMessage("该用户未登陆");
            ctx.channel().writeAndFlush(responsePacket);
        }
    }
}
