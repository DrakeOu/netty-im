package client.console;

import io.netty.channel.Channel;
import protocol.request.GroupMsgRequestPacket;

import java.util.Scanner;

public class SendToGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入群聊ID:");
        String groupId = scanner.next();
        System.out.println("请输入发送内容:");
        String msg = scanner.next();

        GroupMsgRequestPacket msgRequestPacket = new GroupMsgRequestPacket();
        msgRequestPacket.setMsg(msg);
        msgRequestPacket.setGroupId(groupId);
        channel.writeAndFlush(msgRequestPacket);

    }
}
