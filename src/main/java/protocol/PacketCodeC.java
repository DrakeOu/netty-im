package protocol;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import protocol.command.LoginRequestPacket;
import protocol.command.LoginResponsePacket;
import protocol.command.MessageRequestPacket;
import protocol.command.MessageResponsePacket;
import serializer.impl.JSONSerializer;
import serializer.Serializer;

import java.util.HashMap;
import java.util.Map;

public class PacketCodeC {

    public static final PacketCodeC INSTANCE = new PacketCodeC();
    public static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Integer, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Packet.CommandEnum.LOGIN_REQUEST.getCommand(), LoginRequestPacket.class);
        packetTypeMap.put(Packet.CommandEnum.LOGIN_RESPONSE.getCommand(), LoginResponsePacket.class);
        packetTypeMap.put(Packet.CommandEnum.MESSAGE_REQUEST.getCommand(), MessageRequestPacket.class);
        packetTypeMap.put(Packet.CommandEnum.MESSAGE_RESPONSE.getCommand(), MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        serializerMap.put(Serializer.JSON_SERIALIZER, new JSONSerializer());
    }

    public void encode(ByteBuf byteBuf, Packet packet){
        //下面开始按协议要求编码

        //1.创建ByteBuf对象
//        ByteBuf byteBuf = allocator.ioBuffer();
        //2.序列化对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //3.编码
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

    }

    public Packet decode(ByteBuf byteBuf){
        //跳过magic-num
        byteBuf.skipBytes(4);

        //跳过版本号
        byteBuf.skipBytes(1);

        //读取序列化算法
        byte serializerAlgorithm = byteBuf.readByte();

        //读取指令
        byte command = byteBuf.readByte();

        //读取数据包长度
        int length = byteBuf.readInt();

        //读取数据
        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        //获取反序列化的字节码
        Class<? extends Packet> packetType = getPacketType(command);

        //获取序列化算法
        Serializer serializer = getSerializer(serializerAlgorithm);
        //反序列化对象
        Packet packet = null;
        if(null != packetType && null != serializer){
            packet = getSerializer(serializerAlgorithm).deserialize(packetType, bytes);
        }
        return packet;
    }

    private Class<? extends Packet> getPacketType(Byte command){
        return packetTypeMap.get(command);
    }

    private Serializer getSerializer(int serializerAlgorithm){
        return serializerMap.get(serializerAlgorithm);
    }

}
