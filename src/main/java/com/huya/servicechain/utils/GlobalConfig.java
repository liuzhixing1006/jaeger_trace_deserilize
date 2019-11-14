package com.huya.servicechain.utils;

import com.huya.beelogsvr.model.LogSvrRecord;
import com.huya.servicechain.functions.deserialize.KafkaSourceParseSchema;
import org.apache.flink.api.common.ExecutionMode;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.ByteArraySerializer;

import java.util.Properties;

/**
 * @program: GlobalConfig
 * @description: 公共基础类
 * @author: liuzhixing
 * @create: 2019-08-28 14:50
 */
public interface GlobalConfig {
    int AUTO_WATERMARK_INTERVAL = 6000;
    boolean IS_LOCAL = false;

    /**
     * openTracing数据源kafka
     */
    String OPENTRACING_BOOT_STRAP = "10.64.148.133:9092,10.64.148.134:9092,10.64.148.135:9092";
    String OPENTRACING_TOPIC = "topic_trace_opentrcing_test";
    String OPENTRACING_GROUP = "group_trace_test";

    /**
     * CK接收数据指标Kafka
     */
    String CK_METRIC_KAFKA_BOOT_STRAP = "new-szcloud.kafka4metric.huya.com:9092";
    String CK_METRIC_KAFKA_TOPIC = "metric_hive_union";

    /**
     * Ck集群相关Meta信息
     */
    String CK_TRACE_META = "open_tracing_record";

    /**
     * 获取数据源kafka
     *
     * @param bootstrap kafka地址
     * @param topic     kafka的topic
     * @param group     kafka的消费组
     * @return Flink的Kafka消费Client
     */
    static FlinkKafkaConsumer011 getKafkaConsumer(String bootstrap, String topic, String group) {
        Properties props = getBaseKafkaProperties(bootstrap, topic, group);
        return new FlinkKafkaConsumer011(topic, new KafkaSourceParseSchema(), props);
    }

    /**
     * 返回数据源Kafka的properties的参数配置
     *
     * @param bootstrap schema
     * @param topic     topic
     * @param group     消费组
     * @return 返回目标properties
     */
    static Properties getBaseKafkaProperties(String bootstrap, String topic, String group) {
        Properties props = new Properties();
        props.setProperty("bootstrap.servers", bootstrap);
        props.setProperty("group.id", group);
        props.setProperty("enable.auto.commit", "true");
        props.setProperty("auto.commit.interval.ms", "1000");
        props.setProperty("isolation.level", "read_committed");
        props.setProperty("max.partition.fetch.bytes", "5242880");
        props.setProperty("max.poll.records", "3000");
        props.setProperty("receive.buffer.bytes", "1048576");
        return props;
    }

    /**
     * 返回目标kafka的producer参数配置
     *
     * @return 环境参数
     */
    static Properties getTargetKafkaProps() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, GlobalConfig.CK_METRIC_KAFKA_BOOT_STRAP);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, ByteArraySerializer.class);
        props.put("acks", "1");
        props.put("retries", new Integer(5));
        props.put("linger.ms", new Integer(500));
        props.put("batch.size", new Integer(1024 * 40));
        props.put("compression.type", "gzip");
        props.put("buffer.memory", new Integer(32 * 1024 * 1024));

        return props;
    }

    /**
     * 获取Flink环境参数配置
     *
     * @return 环境参数
     */
    static StreamExecutionEnvironment getEnv() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        if (GlobalConfig.IS_LOCAL) {
            env = new LocalStreamEnvironment();
        }
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(Integer.MAX_VALUE, 5000));
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setBufferTimeout(5000);
        env.getConfig().setExecutionMode(ExecutionMode.PIPELINED_FORCED);
        env.getConfig().setAutoWatermarkInterval(GlobalConfig.AUTO_WATERMARK_INTERVAL);

        env.enableCheckpointing(60000);
        env.getCheckpointConfig().setCheckpointingMode(CheckpointingMode.AT_LEAST_ONCE);
        env.getCheckpointConfig().setMinPauseBetweenCheckpoints(15000);
        env.getCheckpointConfig().setCheckpointTimeout(300000);
        env.getCheckpointConfig().setMaxConcurrentCheckpoints(1);
        env.getCheckpointConfig().setFailOnCheckpointingErrors(false);
        env.getCheckpointConfig().enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION);

        return env;
    }
}
