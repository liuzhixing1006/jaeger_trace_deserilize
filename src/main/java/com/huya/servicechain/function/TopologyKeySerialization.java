package com.huya.servicechain.function;

import com.huya.servicechain.utils.MyConstant;
import org.apache.flink.streaming.util.serialization.KeyedSerializationSchema;

import java.util.Random;

/**
 * @ClassName TopologyKeySerialization
 * @Description 分钟级数据Sink序列化器，指定key和value
 * @Author liuzhixing
 * @Date 2019-09-30 15:03
 **/
public class TopologyKeySerialization implements KeyedSerializationSchema<byte[]> {
    private static final long serialVersionUID = -6095174255432490089L;

    private Random random = new Random();

    int randomLimit = 1000;

    /**
     * kafka的key序列化器
     */
    @Override
    public byte[] serializeKey(byte[] message) {
        return (MyConstant.TARGET_KEY + random.nextInt(randomLimit)).getBytes();
    }

    /**
     * kafka的value序列化器
     */
    @Override
    public byte[] serializeValue(byte[] message) {
        return message;
    }

    /**
     * kafka的topic指定器，如果返回null，就代表Sink那里已经带上了
     */
    @Override
    public String getTargetTopic(byte[] message) {
        return null;
    }
}