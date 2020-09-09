package protocol;
import lombok.Data;
import protocol.Packet;

@Data
public class LoginRequestPacket extends Packet {

    private Integer userId;

    private String username;

    private String password;

    @Override
    public Byte getCommand() {
        return CommandEnum.LOGIN_REQUEST.getCommand();
    }
}
