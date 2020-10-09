package client.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import protocol.request.LoginRequestPacket;
import protocol.response.LoginResponsePacket;
import protocol.Packet;
import protocol.PacketCodeC;
import protocol.response.MessageResponsePacket;
import utils.LoginUtil;

import java.util.Date;
import java.util.UUID;

public class ClientHandler extends ChannelInboundHandlerAdapter {

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
//        ctx.channel().writeAndFlush(byteBuf);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("从服务器读取到数据...");

        ByteBuf byteBuf = (ByteBuf) msg;

        //反序列化
        Packet packet = PacketCodeC.INSTANCE.decode(byteBuf);

        if(packet instanceof LoginResponsePacket){
            LoginResponsePacket responsePacket = (LoginResponsePacket) packet;
            if(responsePacket.getSuccess()) {
                LoginUtil.maskAsLogin(ctx.channel());
                System.out.println("登陆成功: " + responsePacket.getMsg());
            }
            else System.err.println("登陆失败: " + responsePacket.getMsg());
        }else if(packet instanceof MessageResponsePacket){
            MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
            System.out.println("接收到服务器响应: " + messageResponsePacket.getMessage());
        }
    }
}
