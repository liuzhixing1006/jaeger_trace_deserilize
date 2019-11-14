package com.huya.servicechain.handles;

import com.huya.beelogsvr.model.LogSvrRecord;
import com.huya.servicechain.domain.grpc.Span;
import com.huya.servicechain.functions.map.TraceMapFunction;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;

/**
 * @author by liuzhixing
 * @description: 根据各个业务的源数据，序列化封装成各自的Bean对象
 * @Classname TopologyPraseUtil
 * @date 2019/6/3
 */
public class SourceStreamParseHandler {
    //OpenTracing数据源反序列化
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
