package protocol;

import lombok.Data;

@Data
public abstract class Packet {

    /**
     * 协议版本
     */
    private Byte version = 1;

    /**
     *
     * @return 指令
     */
    public abstract Byte getCommand();

    protected Boolean success;

    protected enum CommandEnum{

        LOGIN_REQUEST(1), LOGIN_RESPONSE(2), MESSAGE_REQUEST(3), MESSAGE_RESPONSE(4),
        LOGOUT_REQUEST(5), LOGOUT_RESPONSE(6),
        CREATE_GROUP_REQUEST(7), CREATE_GROUP_RESPONSE(8);


        CommandEnum(Integer c){
            this.command = c.byteValue();
        }

        private Byte command;

        public Byte getCommand() {
            return command;
        }

        public void setCommand(Byte command) {
            this.command = command;
        }
    }
}
