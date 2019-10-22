package com.huya.servicechain;

import com.huya.servicechain.domain.LogSreBean;
import com.huya.servicechain.domain.source.TafSourceEvent;
import com.huya.servicechain.domain.source.WebSourceBean;
import com.huya.servicechain.domain.target.TargetBean;
import com.huya.servicechain.functions.TargetEventToProtolBuffer;
import com.huya.servicechain.functions.TopologyKeySerialization;
import com.huya.servicechain.handles.SourceStreamParseHandler;
import com.huya.servicechain.handles.TransferHandler;
import com.huya.servicechain.utils.Constant;
import com.huya.servicechain.utils.enums.BgType;
import com.huya.servicechain.utils.enums.CorpType;
import lombok.extern.log4j.Log4j2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaProducer011;

/**
 * @description:flink主方法入口
 * @Classname Job
 * @author by liuzhixing
 * @date 2019/6/11
 */
@Log4j2
public class Job {
    public static void main(String[] args) {
        try {
            StreamExecutionEnvironment env = Constant.getEnv();
            FlinkKafkaConsumer011 webKafkaConsumer = Constant.getKafkaConsumer(Constant.WEB_SERVICE_CHAIN_BOOT_STRAP, Constant.WEB_SERVICE_CHAIN_TOPIC, Constant.WEB_SERVICE_CHAIN_GROUP);
            FlinkKafkaConsumer011 webPrefectureConsumer = Constant.getKafkaConsumerBySasl(Constant.WEB_PREFECTURE_BOOT_STRAP, Constant.WEB_PREFECTURE_TOPIC, Constant.WEB_PREFECTURE_GROUP, Constant.WEB_PREFECTURE_CONFIG);
            FlinkKafkaConsumer011 yomeKafkaConsumer = Constant.getKafkaConsumer(Constant.YOME_SERVICE_CHAIN_BOOT_STRAP, Constant.YOME_SERVICE_CHAIN_TOPIC, Constant.YOME_SERVICE_CHAIN_GROUP);
            FlinkKafkaConsumer011<LogSreBean> nimoKafkaConsumer = Constant.getKafkaConsumerByKeyMode(Constant.NIMO_SERVICE_CHAIN_BOOT_STRAP, Constant.NIMO_SERVICE_CHAIN_TOPIC, Constant.NIMO_SERVICE_CHAIN_GROUP);
            //FlinkKafkaConsumer011 huyaKafkaConsumer = Constant.getKafkaConsumer(Constant.HUYA_SERVICE_CHAIN_BOOT_STRAP, Constant.HUYA_SERVICE_CHAIN_TOPIC, Constant.HUYA_SERVICE_CHAIN_GROUP);

            //web主站相关日志
            SingleOutputStreamOperator<WebSourceBean> webMappedStream = SourceStreamParseHandler.getWebSourceEventStream(env, webKafkaConsumer);
            SingleOutputStreamOperator<TargetBean> webEvent = TransferHandler.processWeb(webMappedStream).name("web主站");

            //web专区相关日志
            SingleOutputStreamOperator<String> webPrefectureStream = SourceStreamParseHandler.getWebPrefectureStream(env, webPrefectureConsumer);
            SingleOutputStreamOperator<TargetBean> webPrefectureEvent = TransferHandler.processWebPrefecture(webPrefectureStream).name("web专区");

            //huya-taf相关日志
            /*SingleOutputStreamOperator<TafSourceEvent> huyaMappedStream = SourceStreamParseHandler.getTafSourceEventStream(env, huyaKafkaConsumer, 32);
            SingleOutputStreamOperator<TargetBean> huyaEvent = TransferHandler.processTaf(huyaMappedStream, CorpType.HUYA, BgType.HUYA_TAF, 32).name("huya-taf");*/

            //yome-taf相关日志
            SingleOutputStreamOperator<TafSourceEvent> yomeMappedStream = SourceStreamParseHandler.getTafSourceEventStream(env, yomeKafkaConsumer, 4);
            SingleOutputStreamOperator<TargetBean> yomeEvent = TransferHandler.processTaf(yomeMappedStream, CorpType.YOME, BgType.YOME_TAF, 4).name("yome-taf");

            //nimo-taf相关日志
            SingleOutputStreamOperator<TafSourceEvent> nimoMappedStream = SourceStreamParseHandler.getNimoSourceEventStream(env, nimoKafkaConsumer);
            SingleOutputStreamOperator<TargetBean> nimoEvent = TransferHandler.processTaf(nimoMappedStream, CorpType.NIMO, BgType.NIMO_TAF, 4).name("nimo-taf");

            //将所有流混合成一条
            DataStream<TargetBean> unionStream = webEvent.union(yomeEvent).union(nimoEvent).union(webPrefectureEvent);

            unionStream.map(new TargetEventToProtolBuffer())
                    .setParallelism(32)
                    .addSink(new FlinkKafkaProducer011<>(
                            Constant.TARGET_KAFKA_TOPIC, new TopologyKeySerialization(), Constant.getTargetKafkaProps()
                    ))
                    .setParallelism(32)
                    .name("topology_metric_minute_to_kafka");

            env.execute("huya_service_chain");
        } catch (Exception e) {
            log.error(e);
        }
    }
}
