package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.Session;
import protocol.request.GroupMsgRequestPacket;
import protocol.request.JoinGroupRequestPacket;
import protocol.response.GroupMsgResponsePacket;
import protocol.response.JoinGroupResponsePacket;
import protocol.response.MessageResponsePacket;
import utils.GroupUtil;
import utils.SessionUtil;

import java.util.List;

public class JoinGroupRequestHandler extends SimpleChannelInboundHandler<JoinGroupRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, JoinGroupRequestPacket joinGroupRequestPacket) throws Exception {
        Session session = SessionUtil.getSession(ctx.channel());
        String groupId = joinGroupRequestPacket.getGroupId();
        System.out.println("收到加入群聊请求, " + session.getUserName() + " 希望加入ID: " + groupId);

        //尝试加入
        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        JoinGroupResponsePacket responsePacket = new JoinGroupResponsePacket();
        if(null != channelGroup){
            GroupUtil.SpreadToMember(groupId, session.getUserName(), "加入了群聊");
            SessionUtil.addToChannelGroup(ctx.channel(), groupId);
            List<String> members = SessionUtil.getGroupMembers(groupId);
            responsePacket.setCurrentMembers(members);
            responsePacket.setGroupId(groupId);
            responsePacket.setSuccess(true);
            System.out.println("加入成功, ID: " + groupId + " 目前成员: " + members);
        }else{
            responsePacket.setSuccess(false);
            System.out.println("群聊不存在");
        }
        ctx.channel().writeAndFlush(responsePacket);
    }



}

