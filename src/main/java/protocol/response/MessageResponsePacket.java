package protocol.response;

import lombok.Data;
import protocol.Packet;

@Data
public class MessageResponsePacket extends Packet {

    private String fromUser;
    private String fromUserId;
    private String message;

    @Override
    public Byte getCommand() {
        return CommandEnum.MESSAGE_RESPONSE.getCommand();
    }
}
