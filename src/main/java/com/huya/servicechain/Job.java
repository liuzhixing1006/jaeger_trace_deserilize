package com.huya.servicechain;

import com.huya.servicechain.functions.serialize.TraceKeySerialization;
import com.huya.servicechain.handles.SourceStreamParseHandler;
import com.huya.servicechain.utils.GlobalConfig;
import lombok.extern.log4j.Log4j2;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

/**
 * @author by liuzhixing
 * @description:flink主方法入口
 * @Classname Job
 * @date 2019/6/11
 */
@Log4j2
public class Job {
    public static void main(String[] args) {
        try {
            StreamExecutionEnvironment env = GlobalConfig.getFlinkBoostrapEnv();
            FlinkKafkaConsumer011 openTracingConsumer = GlobalConfig.buildKafkaConsumer(GlobalConfig.OPENTRACING_BOOT_STRAP, GlobalConfig.OPENTRACING_TOPIC, GlobalConfig.OPENTRACING_GROUP);

            SingleOutputStreamOperator<String> traceStream = SourceStreamParseHandler.openTracingUnserialized(env, openTracingConsumer, 32);

            traceStream
                    .addSink(new FlinkKafkaProducer011<>(
                            GlobalConfig.CK_METRIC_KAFKA_TOPIC, new TraceKeySerialization(GlobalConfig.CK_TRACE_META), GlobalConfig.getTargetKafkaProps()
                    ))
                    .setParallelism(32)
                    .name("trace_data_to_kafka")
                    .disableChaining();

            env.execute("huya_service_chain");
        } catch (Exception e) {
            log.error(e);
        }
    }
}


