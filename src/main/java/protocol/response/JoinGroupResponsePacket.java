package protocol.response;

import lombok.Data;
import protocol.Packet;

import java.util.List;

@Data
public class JoinGroupResponsePacket extends Packet {

    List<String> currentMembers;
    String groupId;

    @Override
    public Byte getCommand() {
        return CommandEnum.JOIN_GROUP_RESPONSE.getCommand();
    }
}
