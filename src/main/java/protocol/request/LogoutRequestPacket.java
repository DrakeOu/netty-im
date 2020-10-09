package protocol.request;

import protocol.Packet;

public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return CommandEnum.LOGOUT_REQUEST.getCommand();
    }


}
