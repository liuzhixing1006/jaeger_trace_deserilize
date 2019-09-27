package com.huya.servicechain.sinker;


import com.huya.servicechain.utils.KafkaProducerSingleton;
import com.huya.servicechain.utils.task.WebPrefectureTask;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.huya.servicechain.utils.task.WebPrefectureTask.WebMapperUpdateTask;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class KafkaSink extends RichSinkFunction<String> {
    String bootstrapServers;

    String topic;

    String key = "";

    private Random random = new Random();

    int randomLimit = 1000;

    private KafkaProducer producer = null;

    public KafkaSink(String bootstrapServers, String topic, String key) {
        this.topic = topic;
        this.bootstrapServers = bootstrapServers;
        this.key = key;
    }

    @Override
    public void open(Configuration parameters) throws Exception {
        producer = KafkaProducerSingleton.getInstance(bootstrapServers);
        super.open(parameters);

        ScheduledExecutorService appkeyMapper = new ScheduledThreadPoolExecutor(1, new BasicThreadFactory.Builder().namingPattern("appkeyMapper").daemon(true).build());
        appkeyMapper.scheduleAtFixedRate(() -> {
            WebPrefectureTask.WebMapperUpdateTask();
        }, 0, 2, TimeUnit.HOURS);
    }

    @Override
    public void invoke(String value, Context context) throws Exception {
        log.info("sinker into kafka begin!");
        try {
            if (producer == null) {
                producer = KafkaProducerSingleton.getInstance(bootstrapServers);
            }
            ProducerRecord<byte[], byte[]> producerRecord = new ProducerRecord<>(topic, (key + random.nextInt(randomLimit)).getBytes(), value.getBytes());
            producer.send(producerRecord);
            log.info("insert to kafka success!");
        } catch (Exception e) {
            log.error("insert to kafka failed!");
        }
    }
}
