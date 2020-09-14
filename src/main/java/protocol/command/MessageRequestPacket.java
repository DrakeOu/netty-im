package protocol.command;

import lombok.Data;
import protocol.Packet;

@Data
public class MessageRequestPacket extends Packet {

    private String message;

    @Override
    public Byte getCommand() {
        return CommandEnum.MESSAGE_REQUEST.getCommand();
    }
}
