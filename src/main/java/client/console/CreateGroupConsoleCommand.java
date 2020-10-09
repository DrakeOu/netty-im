package client.console;

import io.netty.channel.Channel;
import protocol.request.CreateGroupRequestPacket;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CreateGroupConsoleCommand implements ConsoleCommand {
    @Override
    public void exec(Scanner scanner, Channel channel) {
        System.out.println("请输入邀请加入群聊的id，用;分隔");

        //获取受邀请的用户
        String ids = scanner.next();
        List<String> invited = Arrays.asList(ids.split(";"));

        CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();
        createGroupRequestPacket.setInvitedIds(invited);
        channel.writeAndFlush(createGroupRequestPacket);
    }
}
