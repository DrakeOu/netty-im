package protocol;

import lombok.Data;

@Data
public class LoginResponsePacket extends Packet{

    private Boolean success;

    private String msg;

    @Override
    public Byte getCommand() {
        return CommandEnum.LOGIN_RESPONSE.getCommand();
    }
}
