package protocol.request;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;
import protocol.Packet;

@Data
@Builder
public class LoginRequestPacket extends Packet {

    private String userId;

    private String username;

    private String password;

    @Tolerate
    LoginRequestPacket(){

    }

    @Override
    public Byte getCommand() {
        return CommandEnum.LOGIN_REQUEST.getCommand();
    }
}
