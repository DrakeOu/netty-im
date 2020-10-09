package protocol.response;

import protocol.Packet;

public class LogoutResponsePacket extends Packet {
    @Override
    public Byte getCommand() {
        return CommandEnum.LOGOUT_RESPONSE.getCommand();
    }
}
