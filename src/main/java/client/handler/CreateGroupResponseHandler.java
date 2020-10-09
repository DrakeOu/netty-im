package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.response.CreateGroupResponsePacket;

public class CreateGroupResponseHandler extends SimpleChannelInboundHandler<CreateGroupResponsePacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupResponsePacket createGroupResponsePacket) throws Exception {
        if(createGroupResponsePacket.getSuccess()){
            System.out.println("创建群聊成功! ID: " + createGroupResponsePacket.getGroupId());
            System.out.println("群内成员: " + createGroupResponsePacket.getNameList());
        }
    }
}
