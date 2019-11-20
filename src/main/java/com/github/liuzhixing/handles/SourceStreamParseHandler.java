package com.github.liuzhixing.handles;

import com.github.liuzhixing.domain.grpc.Span;
import com.github.liuzhixing.functions.map.TraceMapFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;

/**
 * @author by liuzhixing
 * @description: openTracing源数据的二次反序列化操作
 * @Classname SourceStreamParseHandler
 * @date 2019/6/3
 */
public class SourceStreamParseHandler {
    public static SingleOutputStreamOperator<Span> openTracingUnserialized(StreamExecutionEnvironment env, FlinkKafkaConsumerBase<Span> kafkaConsumer, int parallelism){
        SingleOutputStreamOperator filter = env.addSource(kafkaConsumer)
                .setParallelism(parallelism)
                .map(new TraceMapFunction())
                .setParallelism(parallelism)
                .filter(value -> value != null)
                .setParallelism(parallelism);

        return filter;
    }
}
