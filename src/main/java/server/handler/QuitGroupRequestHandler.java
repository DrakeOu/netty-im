package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import protocol.Session;
import protocol.request.QuitGroupRequestPacket;
import protocol.response.QuitGroupResponsePacket;
import utils.GroupUtil;
import utils.SessionUtil;

public class QuitGroupRequestHandler extends SimpleChannelInboundHandler<QuitGroupRequestPacket> {

    public static final QuitGroupRequestHandler INSTANCE =  new QuitGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupRequestPacket quitGroupRequestPacket) throws Exception {
        Session session = SessionUtil.getSession(ctx.channel());

        String groupId = quitGroupRequestPacket.getGroupId();

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        QuitGroupResponsePacket responsePacket = new QuitGroupResponsePacket();
        if(null != channelGroup){
            channelGroup.remove(ctx.channel());
            if(channelGroup.size() == 0){
                channelGroup.close();
            }else{
                GroupUtil.SpreadToMember(groupId, session.getUserName(), "退出了群聊");
            }
            responsePacket.setSuccess(true);
            responsePacket.setGroupId(groupId);
        }else{
            responsePacket.setSuccess(false);
        }

        System.out.println(SessionUtil.getSession(ctx.channel()).getUserName() + "退出成功, ID: " + groupId + " 目前成员: " + SessionUtil.getGroupMembers(groupId));
        ctx.channel().writeAndFlush(responsePacket);
    }
}
