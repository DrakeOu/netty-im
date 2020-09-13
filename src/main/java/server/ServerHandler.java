package server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.LoginRequestPacket;
import protocol.LoginResponsePacket;
import protocol.Packet;
import protocol.PacketCodeC;

public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("开始读取客户端消息:");

        //反序列化数据
        ByteBuf byteBuf = (ByteBuf) msg;
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if(packet instanceof LoginRequestPacket){
            LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;

            System.out.println(loginRequestPacket.getUsername() + " 登陆");
            //构造响应体
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginRequestPacket.setVersion(loginRequestPacket.getVersion());
            boolean valid = valid(loginRequestPacket);
            loginResponsePacket.setSuccess(valid);
            loginResponsePacket.setMsg(valid?"登陆成功!":"登陆失败");

            //序列化响应
            ByteBuf resp = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);

            ctx.channel().writeAndFlush(resp);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        //预留校验登陆
        return true;
    }
}
