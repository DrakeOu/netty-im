package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.command.MessageRequestPacket;
import protocol.command.MessageResponsePacket;

public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {

        System.out.println("接收到客户端输入:" + messageRequestPacket.getMessage());
        //构造返回
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setMessage("【客户端收到】: " + messageRequestPacket.getMessage());

        ctx.channel().writeAndFlush(messageResponsePacket);
    }
}
