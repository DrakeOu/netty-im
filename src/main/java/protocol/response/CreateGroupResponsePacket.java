package protocol.response;

import lombok.Data;
import protocol.Packet;

import java.util.List;

@Data
public class CreateGroupResponsePacket extends Packet {
    String groupId;
    List<String> nameList;

    @Override
    public Byte getCommand() {
        return CommandEnum.CREATE_GROUP_RESPONSE.getCommand();
    }
}
