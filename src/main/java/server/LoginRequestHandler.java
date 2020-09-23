package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.Session;
import protocol.command.LoginRequestPacket;
import protocol.command.LoginResponsePacket;
import utils.SessionUtil;

public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
        System.out.println(loginRequestPacket.getUsername() + " 登陆");
        //保存会话
        Session session = Session.builder()
                .userId(loginRequestPacket.getUserId())
                .userName(loginRequestPacket.getUsername()).build();
        SessionUtil.bindSession(session, ctx.channel());
        //构造响应体
        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        boolean valid = valid(loginRequestPacket);
        loginResponsePacket.setUserId(session.getUserId());
        loginResponsePacket.setUserName(session.getUserName());
        loginResponsePacket.setSuccess(valid);
        loginResponsePacket.setMsg(valid?"登陆成功!":"登陆失败");
        ctx.channel().writeAndFlush(loginResponsePacket);
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        //预留校验登陆
        return true;
    }
}
