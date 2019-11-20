package com.github.liuzhixing.functions.serialize;

import org.apache.flink.streaming.util.serialization.KeyedSerializationSchema;

import java.util.Random;

/**
 * @ClassName TraceKeySerialization
 * @Description Trace数据打入Kafka
 * @Author liuzhixing
 * @Date 2019-09-30 15:03
 **/
public class TraceKeySerialization implements KeyedSerializationSchema<String> {
    private Random random = new Random();

    private int randomLimit = 1000;

    private String meta;

    public TraceKeySerialization(String metaName) {
        meta = metaName;
    }

    /**
     * kafka的key序列化器
     */
    @Override
    public byte[] serializeKey(String message) {
        return (meta + "|" + random.nextInt(randomLimit)).getBytes();
    }

    /**
     * kafka的value序列化器
     */
    @Override
    public byte[] serializeValue(String message) {
        return message.getBytes();
    }

    /**
     * kafka的topic指定器，如果返回null，就代表Sink那里已经带上了
     */
    @Override
    public String getTargetTopic(String message) {
        return null;
    }
}