package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.Packet;
import protocol.PacketCodeC;
import protocol.command.LoginRequestPacket;
import protocol.command.LoginResponsePacket;
import protocol.command.MessageResponsePacket;
import utils.LoginUtil;

import java.util.Date;
import java.util.UUID;

public class LoginResponseHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(new Date() + ": 客户端开始登陆...");

        //组装登陆对象
        LoginRequestPacket loginRequestPacket = LoginRequestPacket.builder()
                .userId(UUID.randomUUID().toString())
                .username("drake")
                .password("fff")
                .build();

        //序列化对象
//        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);

        //传输数据
        //注意，这里不再需要传入bytebuf了
        ctx.channel().writeAndFlush(loginRequestPacket);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginResponsePacket loginResponsePacket) throws Exception {
        System.out.println("从服务器读取到数据...");

        //不再需要反序列化了
        if(loginResponsePacket.getSuccess()) {
            LoginUtil.maskAsLogin(ctx.channel());
            System.out.println("登陆成功: " + loginResponsePacket.getMsg());
        }
        else System.err.println("登陆失败: " + loginResponsePacket.getMsg());

    }
}
