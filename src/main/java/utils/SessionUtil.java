package utils;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import protocol.Attributes;
import protocol.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SessionUtil {

    private static final Map<String, Channel> userIdChannelMap = new ConcurrentHashMap<>();
    private static final Map<String, ChannelGroup> groupIdChannelGroupMap = new ConcurrentHashMap<>();

    public static void bindSession(Session session, Channel channel){
        //1. 将用户id和连接绑定
        //2. 将会话信息和连接绑定
        userIdChannelMap.put(session.getUserId(), channel);
        channel.attr(Attributes.SESSION).set(session);
    }

    public static void unbindSession(Channel channel){
        channel.attr(Attributes.SESSION).set(null);
    }

    public static boolean hasLogin(Channel channel){
        return channel.hasAttr(Attributes.SESSION);
    }

    public static Session getSession(Channel channel){
        return channel.attr(Attributes.SESSION).get();
    }

    public static Channel getChannel(String userId){
        return userIdChannelMap.get(userId);
    }

    public static ChannelGroup getChannelGroup(String groupId) {
        return groupIdChannelGroupMap.get(groupId);
    }

    public static boolean addToChannelGroup(Channel channel, String groupId){
        ChannelGroup channelGroup = groupIdChannelGroupMap.get(groupId);

        boolean result = false;
        if(null != channelGroup){
            channelGroup.add(channel);
            result = true;
        }
        return result;
    }

    public static boolean putChannelGroup(ChannelGroup channelGroup, String groupId){
        groupIdChannelGroupMap.put(groupId, channelGroup);
        return true;
    }

    public static List<String> getGroupMembers(String groupId){
        List<String> members = new ArrayList<>();
        if(groupIdChannelGroupMap.containsKey(groupId)){
            groupIdChannelGroupMap.get(groupId)
                    .forEach(channel -> members.add(SessionUtil.getSession(channel).getUserName()));
        }
        return members;
    }
}
