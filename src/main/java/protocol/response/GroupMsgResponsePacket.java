package protocol.response;

import lombok.Data;
import protocol.Packet;

@Data
public class GroupMsgResponsePacket extends Packet {

    String fromUserName;
    String msg;
    String groupId;

    @Override
    public Byte getCommand() {
        return CommandEnum.GROUP_MSG_RESPONSE.getCommand();
    }
}
