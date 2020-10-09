package utils;

import io.netty.channel.group.ChannelGroup;
import protocol.response.GroupMsgResponsePacket;

public class GroupUtil {

    public static void SpreadToMember(String groupId, String fromName, String message){
        GroupMsgResponsePacket responsePacket = new GroupMsgResponsePacket();
        responsePacket.setMsg(message);
        responsePacket.setFromUserName(fromName);
        responsePacket.setGroupId(groupId);

        ChannelGroup channelGroup = SessionUtil.getChannelGroup(groupId);
        channelGroup.writeAndFlush(responsePacket);

    }
}
