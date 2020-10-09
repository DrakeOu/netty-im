package protocol.response;

import lombok.Data;
import protocol.Packet;

@Data
public class LoginResponsePacket extends Packet {

    private String userId;
    private String userName;

    private String msg;

    @Override
    public Byte getCommand() {
        return CommandEnum.LOGIN_RESPONSE.getCommand();
    }
}
