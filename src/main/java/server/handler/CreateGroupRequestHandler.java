package server.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import protocol.request.CreateGroupRequestPacket;
import protocol.response.CreateGroupResponsePacket;
import utils.IDUtil;
import utils.SessionUtil;

import java.util.ArrayList;
import java.util.List;

public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    public static final CreateGroupRequestHandler INSTANCE = new CreateGroupRequestHandler();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket createGroupRequestPacket) throws Exception {
        //netty中有channelGroup的概念，可以组装一个group并且按group写数据
        List<String> invitedIds = createGroupRequestPacket.getInvitedIds();
        CreateGroupResponsePacket responsePacket = new CreateGroupResponsePacket();

        // 人数不足或错误
        if(invitedIds == null || invitedIds.size()<2){
            responsePacket.setSuccess(false);
            ctx.channel().writeAndFlush(responsePacket);
            return;
        }

        //1.创建一个channelGroup
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());
        List<String> nameList = new ArrayList<>();

        //2.添加目标用户的channel
        invitedIds.stream()
                .filter(i -> SessionUtil.getChannel(i)!=null)
                .forEach(i -> {
                    Channel channel = SessionUtil.getChannel(i);
                    channelGroup.add(channel);
                    nameList.add(SessionUtil.getSession(channel).getUserName());
                });

        //3.创建响应结果
        String groupId = IDUtil.randomUserId();
        responsePacket.setSuccess(true);
        responsePacket.setGroupId(groupId);
        responsePacket.setNameList(nameList);

        //4.添加到MAP中
        SessionUtil.putChannelGroup(channelGroup, groupId);

        channelGroup.writeAndFlush(responsePacket);

        System.out.println("创建群聊成功! ID: " + responsePacket.getGroupId());
        System.out.println("群内成员: " + responsePacket.getNameList());
    }
}
