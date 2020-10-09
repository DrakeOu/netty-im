package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.Session;
import protocol.response.LoginResponsePacket;
import utils.SessionUtil;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        System.out.println("从服务器读取到数据...");

        //不再需要反序列化了
        if(loginResponsePacket.getSuccess()) {
            Session session = Session.builder()
                    .userName(loginResponsePacket.getUserName())
                    .userId(loginResponsePacket.getUserId())
                    .build();
            SessionUtil.bindSession(session, ctx.channel());
            System.out.println(loginResponsePacket.getMsg() + ":"+ loginResponsePacket.getUserId() + "-" + loginResponsePacket.getUserName());
        }
        else System.err.println("登陆失败: " + loginResponsePacket.getMsg());

    }
}
