package protocol.request;

import lombok.Data;
import protocol.Packet;

import java.util.List;

@Data
public class CreateGroupRequestPacket extends Packet {

    /**
     * 受邀请的用户
     */
    List<String> invitedIds;

    @Override
    public Byte getCommand() {
        return CommandEnum.CREATE_GROUP_REQUEST.getCommand();
    }
}
