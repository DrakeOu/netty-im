package client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import protocol.Packet;
import protocol.response.QuitGroupResponsePacket;

public class QuitGroupResponseHandler extends SimpleChannelInboundHandler<QuitGroupResponsePacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, QuitGroupResponsePacket quitGroupResponsePacket) throws Exception {
        if(quitGroupResponsePacket.getSuccess()){
            System.out.println("成功退出群聊: " + quitGroupResponsePacket.getGroupId());
        }else{
            System.out.println("退出失败!");
        }
    }
}
