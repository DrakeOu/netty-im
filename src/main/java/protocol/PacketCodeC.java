package protocol;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import serializer.impl.JSONSerializer;
import serializer.Serializer;

import java.util.HashMap;
import java.util.Map;

public class PacketCodeC {

    private static final int MAGIC_NUMBER = 0x12345678;
    private static final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private static final Map<Integer, Serializer> serializerMap;

    static {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(Packet.CommandEnum.LOGIN_REQUEST.getCommand(), LoginRequestPacket.class);

        serializerMap = new HashMap<>();
        serializerMap.put(Serializer.JSON_SERIALIZER, new JSONSerializer());
    }

    public ByteBuf encode(Packet packet){
        //下面开始按协议要求编码

        //1.创建ByteBuf对象
        ByteBuf byteBuf = ByteBufAllocator.DEFAULT.ioBuffer();
        //2.序列化对象
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        //3.编码
        byteBuf.writeInt(MAGIC_NUMBER);
        byteBuf.writeByte(packet.getVersion());
        byteBuf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        byteBuf.writeByte(packet.getCommand());
        byteBuf.writeInt(bytes.length);
        byteBuf.writeBytes(bytes);

        return byteBuf;
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
