package com.huya.servicechain.utils.holder;

import com.huya.beelogsvr.model.LogSvrRecord;
import com.huya.servicechain.domain.LogSreBean;
import com.huya.servicechain.domain.source.TafSourceEvent;
import com.huya.servicechain.domain.source.WebSourceBean;
import com.huya.servicechain.function.nimo.MapToNimoSourceBeanFunciton;
import com.huya.servicechain.function.nimo.NimoTafKeySelector;
import com.huya.servicechain.function.web.MapToWebSourceBeanFunction;
import com.huya.servicechain.function.yome.MapToTafSourceBeanFunction;
import com.huya.servicechain.utils.MyConstant;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.SplitStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumerBase;

/**
 * @author by liuzhixing
 * @description: 源数据序列化封装类
 * @Classname TopologyPraseUtil
 * @date 2019/6/3
 */
public class SourceStreamHolder {
    //web主站源数据转换
    public static SingleOutputStreamOperator<WebSourceBean> getWebSourceEventStream(StreamExecutionEnvironment env, FlinkKafkaConsumerBase<String> kafkaConsumer){
        SingleOutputStreamOperator filter = env.addSource(kafkaConsumer)
                .setParallelism(4)
                .map(new MapToWebSourceBeanFunction())
                .setParallelism(4)
                .filter(value -> value != null)
                .setParallelism(4);
        return filter;
    }

    //web专区源数据转换
    public static SingleOutputStreamOperator<String> getWebPrefectureStream(StreamExecutionEnvironment env, FlinkKafkaConsumerBase<LogSvrRecord> kafkaConsumer) {
        SingleOutputStreamOperator<String> filter = env.addSource(kafkaConsumer)
                .setParallelism(32)
                .filter(value -> value != null)
                .setParallelism(32)
                .map(value -> value.getData())
                .setParallelism(32);

        return filter;
    }

    //taf源数据转换
    public static SingleOutputStreamOperator<TafSourceEvent> getTafSourceEventStream(StreamExecutionEnvironment env, FlinkKafkaConsumerBase<String> kafkaConsumer, int parallelism){
        SingleOutputStreamOperator filter = env.addSource(kafkaConsumer)
                .setParallelism(parallelism)
                .map(new MapToTafSourceBeanFunction())
                .setParallelism(parallelism)
                .filter(value -> value != null)
                .setParallelism(parallelism);

        return filter;
    }

    //nimo-taf源数据转换
    public static SingleOutputStreamOperator<TafSourceEvent> getNimoSourceEventStream(StreamExecutionEnvironment env, FlinkKafkaConsumerBase<LogSreBean> kafkaConsumer) {
        SingleOutputStreamOperator<LogSreBean> sourceStream = env.addSource(kafkaConsumer)
                .setParallelism(32)
                .uid("Src_stream")
                .name("transform_src")
                .filter(value -> value != null)
                .setParallelism(32);

        SplitStream<LogSreBean> splitDatas = sourceStream.split(new NimoTafKeySelector());

        SingleOutputStreamOperator filter = splitDatas.select(MyConstant.NIMO_SERVICE_KEY)
                .map(new MapToNimoSourceBeanFunciton())
                .setParallelism(32)
                .map(new MapToTafSourceBeanFunction())
                .setParallelism(32)
                .filter(value -> value != null)
                .setParallelism(32);

        return filter;
    }
}
