package thefirstchange.example.com.communicationtext.netty.serialize.impl;


import com.alibaba.fastjson.JSON;

import thefirstchange.example.com.communicationtext.netty.serialize.Serializer;
import thefirstchange.example.com.communicationtext.netty.serialize.SerializerAlgorithm;

public class JSONSerializer implements Serializer {

    @Override
    public byte getSerializerAlgorithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object) {

        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {

        return JSON.parseObject(bytes, clazz);
    }
}
