package protocol.request;

import lombok.Data;
import protocol.Packet;

@Data
public class JoinGroupRequestPacket extends Packet {

    String groupId;

    @Override
    public Byte getCommand() {
        return CommandEnum.JOIN_GROUP_REQUEST.getCommand();
    }
}
