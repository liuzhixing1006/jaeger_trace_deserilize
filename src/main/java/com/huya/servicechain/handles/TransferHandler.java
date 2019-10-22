package com.huya.servicechain.handles;

import com.huya.servicechain.domain.source.TafSourceEvent;
import com.huya.servicechain.domain.source.WebPrefectureSourceBean;
import com.huya.servicechain.domain.source.WebSourceBean;
import com.huya.servicechain.domain.target.TargetBean;
import com.huya.servicechain.functions.web.WebFlatMapFunction;
import com.huya.servicechain.functions.webprefecture.WebPrefectureKeyByFunction;
import com.huya.servicechain.functions.webprefecture.WebPrefectureMapFunction;
import com.huya.servicechain.functions.webprefecture.WebPrefectureWindowFunction;
import com.huya.servicechain.functions.TafFlatMapFunction;
import com.huya.servicechain.utils.Constant;
import com.huya.servicechain.utils.enums.BgType;
import com.huya.servicechain.utils.enums.CorpType;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * @ClassName TransferHandler
 * @Description 将所有相关的数据转换成统一格式的TargetBean
 * @Author liuzhixing
 * @Date 2019-08-28 16:41
 **/
public class TransferHandler {
    //web主站处理路由类
    public static SingleOutputStreamOperator<TargetBean> processWeb(SingleOutputStreamOperator<WebSourceBean> mappedStream) {
        SingleOutputStreamOperator<TargetBean> targetBeanSingleOutputStreamOperator = mappedStream.returns(WebSourceBean.class)
                .flatMap(new WebFlatMapFunction())
                .setParallelism(4);

        return targetBeanSingleOutputStreamOperator;
    }

    //web专区处理路由类
    public static SingleOutputStreamOperator<TargetBean> processWebPrefecture(SingleOutputStreamOperator<String> mappedStream) {
        SingleOutputStreamOperator<TargetBean> targetBeanSingleOutputStreamOperator = mappedStream.returns(String.class)
                .flatMap(new WebPrefectureMapFunction())
                .setParallelism(32)
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<WebPrefectureSourceBean>(Time.seconds(Constant.MAX_LATENESS)) {
                    @Override
                    public long extractTimestamp(WebPrefectureSourceBean sourceBean) {
                        return sourceBean.getIts();
                    }
                })
                .keyBy(new WebPrefectureKeyByFunction())
                .window(TumblingEventTimeWindows.of(Time.minutes(1)))
                .apply(new WebPrefectureWindowFunction())
                .setParallelism(32);

        return targetBeanSingleOutputStreamOperator;
    }

    //taf处理路由类
    public static SingleOutputStreamOperator<TargetBean> processTaf(SingleOutputStreamOperator<TafSourceEvent> mappedStream, CorpType corpType, BgType bgType, int parallelism) {
        SingleOutputStreamOperator<TargetBean> targetBeanSingleOutputStreamOperator = mappedStream.flatMap(new TafFlatMapFunction(corpType, bgType))
                .setParallelism(parallelism);

        return targetBeanSingleOutputStreamOperator;
    }
}
