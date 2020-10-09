package server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.Session;
import protocol.response.LogoutResponsePacket;
import utils.SessionUtil;

public class LogoutRequestHandler extends SimpleChannelInboundHandler<LogoutRequestHandler> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LogoutRequestHandler logoutRequestHandler) throws Exception {
        Session session = SessionUtil.getSession(ctx.channel());
        System.out.println("用户: " + session.getUserName() + " 登出");
        SessionUtil.unbindSession(ctx.channel());
        LogoutResponsePacket logoutResponsePacket = new LogoutResponsePacket();
        logoutResponsePacket.setSuccess(true);
        ctx.channel().writeAndFlush(logoutResponsePacket);
    }
}
