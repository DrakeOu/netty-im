package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.response.GroupMsgResponsePacket;

public class GroupMsgResponseHandler extends SimpleChannelInboundHandler<GroupMsgResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMsgResponsePacket groupMsgResponsePacket) throws Exception {
        System.out.println("收到群消息, ID:" + groupMsgResponsePacket.getGroupId());
        System.out.println(groupMsgResponsePacket.getFromUserName() + ": " + groupMsgResponsePacket.getMsg());
    }
}
