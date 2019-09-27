package com.huya.servicechain.utils;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;

import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class KafkaProducerSingleton {

    static ConcurrentHashMap producerMap = new ConcurrentHashMap<String, KafkaProducer<String, String>>();

    public static KafkaProducer getInstance(String bootstrapServers) {

        KafkaProducer result = (KafkaProducer) producerMap.get(bootstrapServers);
        if (result == null) result = initInstance(bootstrapServers);
        return result;
    }

    synchronized private static KafkaProducer<String, String> initInstance(String bootstrapServers) {
        KafkaProducer<String, String> producer = (KafkaProducer<String, String>) producerMap.get(bootstrapServers);
        if (producer == null) {
            Properties props = new Properties();
            props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
            props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
            props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
            props.put("acks", "1");
            props.put("retries", new Integer(5));
            props.put("batch.size", new Integer(10240));
            props.put("linger.ms", new Integer(50));
            props.put("buffer.memory", new Integer(33554432));
            producer = new KafkaProducer<String, String>(props);
            producerMap.put(bootstrapServers, producer);
        }
        return producer;
    }
}
