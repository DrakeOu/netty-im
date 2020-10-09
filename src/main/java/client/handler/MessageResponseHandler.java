package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.response.MessageResponsePacket;

public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageResponsePacket messageResponsePacket) throws Exception {
        String fromUser = messageResponsePacket.getFromUser();
        String fromUserId = messageResponsePacket.getFromUserId();

        System.out.println("接收到:" + fromUserId + "-" + fromUser + "的消息" + messageResponsePacket.getMessage());
    }
}
