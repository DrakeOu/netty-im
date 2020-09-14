package protocol.command;

import lombok.Data;
import protocol.Packet;

@Data
public class MessageResponsePacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return CommandEnum.MESSAGE_RESPONSE.getCommand();
    }
}
