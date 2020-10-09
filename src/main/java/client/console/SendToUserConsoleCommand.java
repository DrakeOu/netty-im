package client.console;

import io.netty.channel.Channel;
import protocol.request.MessageRequestPacket;

import java.util.Scanner;

public class SendToUserConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("发送消息到用户: ");

        String toUserId = scanner.next();
        String message = scanner.next();

        MessageRequestPacket requestPacket = new MessageRequestPacket();
        requestPacket.setToUserId(toUserId);
        requestPacket.setMessage(message);

        channel.writeAndFlush(requestPacket);
    }
}
