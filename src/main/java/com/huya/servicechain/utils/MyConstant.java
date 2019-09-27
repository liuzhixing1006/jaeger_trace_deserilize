package com.huya.servicechain.utils;

import com.huya.beelogsvr.model.LogSvrRecord;
import org.apache.flink.api.common.ExecutionMode;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.CheckpointingMode;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.LocalStreamEnvironment;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

import java.util.Properties;

/**
 * @program: topology_overseas_analysis
 * @description: 公共基础类
 * @author: liuzhixing
 * @create: 2019-08-28 14:50
 */
public interface MyConstant {
    int AUTO_WATERMARK_INTERVAL = 6000;
    int DEFAULT_PARALLELISM = 8;
    boolean IS_LOCAL = false;
    int MAX_LATENESS = 30;

    /**
     * 国内huya-taf数据源kafka
     */
    String HUYA_SERVICE_CHAIN_BOOT_STRAP = "kafka4ops.huya.com:6399";
    String HUYA_SERVICE_CHAIN_TOPIC = "metric.taf.stat";
    String HUYA_SERVICE_CHAIN_GROUP = "topology";

    /**
     * 海外yome-taf数据源kafka
     */
    String YOME_SERVICE_CHAIN_BOOT_STRAP = "kafka.niko.local.config.hy:7519";
    String YOME_SERVICE_CHAIN_TOPIC = "metric.taf_stat";
    String YOME_SERVICE_CHAIN_GROUP = "topology";

    /**
     * 海外nimo-taf数据源kafka
     */
    String NIMO_SERVICE_CHAIN_BOOT_STRAP = "kafka.nimo.local.config.hy:7619";
    String NIMO_SERVICE_CHAIN_TOPIC = "metric_kafka_union";
    String NIMO_SERVICE_CHAIN_GROUP = "topology";
    String NIMO_SERVICE_KEY = "taf_stat|";

    /**
     * web主站数据源kafka
     */
    String WEB_SERVICE_CHAIN_BOOT_STRAP = "kafka4ops.huya.com:6399";
    String WEB_SERVICE_CHAIN_TOPIC = "metric.web.consul.microservice";
    String WEB_SERVICE_CHAIN_GROUP = "topology";

    /**
     * web专区数据源kafka
     */
    String WEB_PREFECTURE_BOOT_STRAP = "guangzhou-kafka0-subscribe.huya.com:9092,guangzhou-kafka1-subscribe.huya.com:9092,guangzhou-kafka2-subscribe.huya.com:9092,guangzhou-kafka3-subscribe.huya.com:9092,guangzhou-kafka4-subscribe.huya.com:9092,guangzhou-kafka5-subscribe.huya.com:9092";
    String WEB_PREFECTURE_TOPIC = "10021";
    String WEB_PREFECTURE_GROUP = "apm_web_topology";
    String WEB_PREFECTURE_CONFIG = "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"apm_trace_analysis\" password=\"cPDUNIGP\";";

    /**
     * 转换后打入的目标kafka
     */
    String TARGET_KAFKA_BOOT_STRAP = "shenzhen-realtimecp-kafka0.huya.comshenzhen-realtimecp-kafka1.huya.com:9092,shenzhen-realtimecp-kafka2.huya.com:9092";
    String TARGET_KAFKA_TOPIC = "topic_trace_analysis_routing";
    String TARGET_KEY = "apm|transform|server_minute";

    /**
     * 获取目标kafka
     *
     * @param bootstrap kafka地址
     * @param topic     kafka的topic
     * @param group     kafka的消费组
     * @return Flink的Kafka消费Client
     */
    static FlinkKafkaConsumer011 getKafkaConsumer(String bootstrap, String topic, String group) {
        Properties props = getBaseKafkaProperties(bootstrap, topic, group);
        return new FlinkKafkaConsumer011(topic, new SimpleStringSchema(), props);
    }

    /**
     * 获取目标kafka,支持Key类型筛选
     *
     * @param bootstrap kafka地址
     * @param topic     kafka的topic
     * @param group     kafka的消费组
     * @return Flink的Kafka消费Client
     */
    static FlinkKafkaConsumer011 getKafkaConsumerByKeyMode(String bootstrap, String topic, String group) {
        Properties props = getBaseKafkaProperties(bootstrap, topic, group);
        return new FlinkKafkaConsumer011(topic, new KafkaSourceParseSchema(), props);
    }


    /**
     * 得到认证的信息
     *
     * @param bootstrap kafka
     * @param topic     topic
     * @param group     消费组
     * @param config    认证信息
     * @return 返回创建的kafka实例
     */
    static FlinkKafkaConsumer011<LogSvrRecord> getKafkaConsumerBySasl(String bootstrap, String topic, String group, String config) {
        Properties props = getBaseKafkaProperties(bootstrap, topic, group);
        props.put("security.protocol", "SASL_PLAINTEXT");
        props.put("sasl.mechanism", "PLAIN");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
        props.put("sasl.jaas.config", config);
        return new FlinkKafkaConsumer011<LogSvrRecord>(topic, new SlsaKafkaSourceSchema(), props);
    }

    /**
     * 返回目标properties
     *
     * @param bootstrap kafka
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
     * 获取环境参数
     *
     * @return 环境参数
     */
    static StreamExecutionEnvironment getEnv() {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        if (MyConstant.IS_LOCAL) {
            env = new LocalStreamEnvironment();
        }
        env.getConfig().setRestartStrategy(RestartStrategies.fixedDelayRestart(Integer.MAX_VALUE, 5000));
        env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        env.setBufferTimeout(5000);
        env.getConfig().setExecutionMode(ExecutionMode.PIPELINED_FORCED);
        env.getConfig().setAutoWatermarkInterval(MyConstant.AUTO_WATERMARK_INTERVAL);

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
