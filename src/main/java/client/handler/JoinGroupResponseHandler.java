package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.response.JoinGroupResponsePacket;

public class JoinGroupResponseHandler extends SimpleChannelInboundHandler<JoinGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupResponsePacket joinGroupResponsePacket) throws Exception {
        if(joinGroupResponsePacket.getSuccess()){
            System.out.println("加入成功, ID: " + joinGroupResponsePacket.getGroupId() + " 目前成员: " + joinGroupResponsePacket.getCurrentMembers());
        }else{
            System.out.println("加入失败!");
        }
    }
}
