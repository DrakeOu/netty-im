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

    protected enum CommandEnum{

        LOGIN_REQUEST(1);

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
