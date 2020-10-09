package protocol.request;

import lombok.Data;
import protocol.Packet;

@Data
public class GroupMsgRequestPacket extends Packet {

    String groupId;
    String msg;

    @Override
    public Byte getCommand() {
        return CommandEnum.GROUP_MSG_REQUEST.getCommand();
    }
}
