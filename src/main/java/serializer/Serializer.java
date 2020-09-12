package serializer;

import serializer.impl.JSONSerializer;

public interface Serializer {

    int JSON_SERIALIZER = 1;

    Serializer DEFAULT = new JSONSerializer();

    /**
     * 序列化算法
     */
    byte getSerializerAlgorithm();

    /**
     * 序列化对象
     */
    byte[] serialize(Object object);

    <T> T deserialize(Class<T> clazz, byte[] bytes);

}
