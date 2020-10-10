package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.Session;
import protocol.request.GroupMsgRequestPacket;
import protocol.response.GroupMsgResponsePacket;
import utils.SessionUtil;

public class GroupMsgRequestHandler extends SimpleChannelInboundHandler<GroupMsgRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GroupMsgRequestPacket groupMsgRequestPacket) throws Exception {
        Session session = SessionUtil.getSession(ctx.channel());
        System.out.println("处理用户: " + session.getUserName() + "的群聊请求, 内容: " + groupMsgRequestPacket.getMsg());

        //准备返回结果，尝试获取群聊
        GroupMsgResponsePacket responsePacket = new GroupMsgResponsePacket();
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupMsgRequestPacket.getGroupId());
        if(null != channelGroup){
            responsePacket.setFromUserName(session.getUserName());
            responsePacket.setMsg(groupMsgRequestPacket.getMsg());
            responsePacket.setSuccess(true);
            responsePacket.setGroupId(groupMsgRequestPacket.getGroupId());

            channelGroup.writeAndFlush(responsePacket);
        }else{
            responsePacket.setSuccess(false);
            ctx.channel().writeAndFlush(responsePacket);
        }
    }
}
