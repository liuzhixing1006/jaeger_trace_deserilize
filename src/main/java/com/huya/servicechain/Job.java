package com.huya.servicechain;

import com.alibaba.fastjson.JSON;
import com.huya.servicechain.domain.LogSreBean;
import com.huya.servicechain.domain.source.TafSourceEvent;
import com.huya.servicechain.domain.source.WebSourceBean;
import com.huya.servicechain.domain.target.TargetBean;
import com.huya.servicechain.sinker.KafkaSink;
import com.huya.servicechain.utils.MyConstant;
import com.huya.servicechain.utils.enums.BgType;
import com.huya.servicechain.utils.enums.CorpType;
import com.huya.servicechain.utils.holder.SourceStreamHolder;
import com.huya.servicechain.utils.holder.TransferHolder;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

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
            StreamExecutionEnvironment env = MyConstant.getEnv();
            FlinkKafkaConsumer011 webKafkaConsumer = MyConstant.getKafkaConsumer(MyConstant.WEB_SERVICE_CHAIN_BOOT_STRAP, MyConstant.WEB_SERVICE_CHAIN_TOPIC, MyConstant.WEB_SERVICE_CHAIN_GROUP);
            FlinkKafkaConsumer011 webPrefectureConsumer = MyConstant.getKafkaConsumerBySasl(MyConstant.WEB_PREFECTURE_BOOT_STRAP, MyConstant.WEB_PREFECTURE_TOPIC, MyConstant.WEB_PREFECTURE_GROUP, MyConstant.WEB_PREFECTURE_CONFIG);
            FlinkKafkaConsumer011 yomeKafkaConsumer = MyConstant.getKafkaConsumer(MyConstant.YOME_SERVICE_CHAIN_BOOT_STRAP, MyConstant.YOME_SERVICE_CHAIN_TOPIC, MyConstant.YOME_SERVICE_CHAIN_GROUP);
            FlinkKafkaConsumer011 huyaKafkaConsumer = MyConstant.getKafkaConsumer(MyConstant.HUYA_SERVICE_CHAIN_BOOT_STRAP, MyConstant.HUYA_SERVICE_CHAIN_TOPIC, MyConstant.HUYA_SERVICE_CHAIN_GROUP);
            huyaKafkaConsumer.setStartFromLatest();
            FlinkKafkaConsumer011<LogSreBean> nimoKafkaConsumer = MyConstant.getKafkaConsumerByKeyMode(MyConstant.NIMO_SERVICE_CHAIN_BOOT_STRAP, MyConstant.NIMO_SERVICE_CHAIN_TOPIC, MyConstant.NIMO_SERVICE_CHAIN_GROUP);

            //web主站相关日志
            SingleOutputStreamOperator<WebSourceBean> webMappedStream = SourceStreamHolder.getWebSourceEventStream(env, webKafkaConsumer);
            SingleOutputStreamOperator<TargetBean> webEvent = TransferHolder.processWeb(webMappedStream).name("web主站");

            //web专区相关日志
            SingleOutputStreamOperator<String> webPrefectureStream = SourceStreamHolder.getWebPrefectureStream(env, webPrefectureConsumer);
            SingleOutputStreamOperator<TargetBean> webPrefectureEvent = TransferHolder.processWebPrefecture(webPrefectureStream).name("web专区");

            //huya-taf相关日志
            SingleOutputStreamOperator<TafSourceEvent> huyaMappedStream = SourceStreamHolder.getTafSourceEventStream(env, huyaKafkaConsumer, 32);
            SingleOutputStreamOperator<TargetBean> huyaEvent = TransferHolder.processTaf(huyaMappedStream, CorpType.HUYA, BgType.HUYA_TAF, 50).name("huya-taf");

            //yome-taf相关日志
            SingleOutputStreamOperator<TafSourceEvent> yomeMappedStream = SourceStreamHolder.getTafSourceEventStream(env, yomeKafkaConsumer, 4);
            SingleOutputStreamOperator<TargetBean> yomeEvent = TransferHolder.processTaf(yomeMappedStream, CorpType.YOME, BgType.YOME_TAF, 4).name("yome-taf");

            //nimo-taf相关日志
            SingleOutputStreamOperator<TafSourceEvent> nimoMappedStream = SourceStreamHolder.getNimoSourceEventStream(env, nimoKafkaConsumer);
            SingleOutputStreamOperator<TargetBean> nimoEvent = TransferHolder.processTaf(nimoMappedStream, CorpType.NIMO, BgType.NIMO_TAF, 32).name("nimo-taf");

            //将所有流混合成一条
            DataStream<TargetBean> unionStream = webEvent.union(huyaEvent).union(yomeEvent).union(nimoEvent).union(webPrefectureEvent);

            unionStream.map(value -> JSON.toJSONString(value))
                    .setParallelism(50)
                    .addSink(new KafkaSink(MyConstant.TARGET_KAFKA_BOOT_STRAP, MyConstant.TARGET_KAFKA_TOPIC, MyConstant.TARGET_KEY))
                    .setParallelism(50)
                    .name("server_source_bean_to_kafka");

            env.execute("huya_service_chain");
        } catch (Exception e) {
            log.error(e);
        }
    }
}
