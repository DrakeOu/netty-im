package protocol.response;

import lombok.Data;
import protocol.Packet;

@Data
public class QuitGroupResponsePacket extends Packet {

    String groupId;

    @Override
    public Byte getCommand() {
        return CommandEnum.QUIT_GROUP_RESPONSE.getCommand();
    }
}
