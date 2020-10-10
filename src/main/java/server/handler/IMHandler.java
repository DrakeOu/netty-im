package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.Packet;

import java.util.HashMap;
import java.util.Map;

public class IMHandler extends SimpleChannelInboundHandler<Packet> {

    public static final IMHandler INSTANCE = new IMHandler();

    private Map<Byte, SimpleChannelInboundHandler<? extends Packet>> handlerMap;

    private IMHandler(){
        handlerMap = new HashMap<>();

        handlerMap.put(Packet.CommandEnum.LOGIN_REQUEST.getCommand(), LoginRequestHandler.INSTANCE);
        handlerMap.put(Packet.CommandEnum.LOGOUT_REQUEST.getCommand(), LogoutRequestHandler.INSTANCE);
        handlerMap.put(Packet.CommandEnum.CREATE_GROUP_REQUEST.getCommand(), CreateGroupRequestHandler.INSTANCE);
        handlerMap.put(Packet.CommandEnum.JOIN_GROUP_REQUEST.getCommand(), JoinGroupRequestHandler.INSTANCE);
        handlerMap.put(Packet.CommandEnum.QUIT_GROUP_REQUEST.getCommand(), QuitGroupRequestHandler.INSTANCE);
        handlerMap.put(Packet.CommandEnum.GROUP_MSG_REQUEST.getCommand(), GroupMsgRequestHandler.INSTANCE);
        handlerMap.put(Packet.CommandEnum.MESSAGE_REQUEST.getCommand(), MessageRequestHandler.INSTANCE);

    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Packet packet) throws Exception {
        handlerMap.get(packet.getCommand()).channelRead(ctx, packet);
    }
}
