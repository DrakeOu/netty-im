package server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.request.LoginRequestPacket;
import protocol.response.LoginResponsePacket;
import protocol.Packet;
import protocol.PacketCodeC;
import protocol.request.MessageRequestPacket;
import protocol.response.MessageResponsePacket;

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

        }else if(packet instanceof MessageRequestPacket){
            MessageRequestPacket messageRequestPacket = (MessageRequestPacket) packet;

            System.out.println("接收到客户端输入:" + messageRequestPacket.getMessage());
            //构造返回
            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            messageResponsePacket.setMessage("【客户端收到】: " + messageRequestPacket.getMessage());
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        //预留校验登陆
        return true;
    }
}
