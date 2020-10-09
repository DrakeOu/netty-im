package protocol.request;

import lombok.Data;
import protocol.Packet;

@Data
public class QuitGroupRequestPacket extends Packet {

    String groupId;

    @Override
    public Byte getCommand() {
        return CommandEnum.QUIT_GROUP_REQUEST.getCommand();
    }
}
