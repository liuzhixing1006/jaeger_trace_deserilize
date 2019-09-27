package com.huya.servicechain.utils.holder;

import com.huya.servicechain.domain.source.TafSourceEvent;
import com.huya.servicechain.domain.source.WebPrefectureSourceBean;
import com.huya.servicechain.domain.source.WebSourceBean;
import com.huya.servicechain.domain.target.TargetBean;
import com.huya.servicechain.function.web.WebFlatMapFunction;
import com.huya.servicechain.function.webprefecture.WebPrefectureKeyByFunction;
import com.huya.servicechain.function.webprefecture.WebPrefectureMapFunction;
import com.huya.servicechain.function.webprefecture.WebPrefectureWindowFunction;
import com.huya.servicechain.function.yome.TafFlatMapFunction;
import com.huya.servicechain.utils.MyConstant;
import com.huya.servicechain.utils.enums.BgType;
import com.huya.servicechain.utils.enums.CorpType;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.functions.timestamps.BoundedOutOfOrdernessTimestampExtractor;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;

/**
 * @ClassName TransferHolder
 * @Description 路由流相关信息
 * @Author liuzhixing
 * @Date 2019-08-28 16:41
 **/
public class TransferHolder {
    //web主站处理路由类
    public static SingleOutputStreamOperator<TargetBean> processWeb(SingleOutputStreamOperator<WebSourceBean> mappedStream) {
        SingleOutputStreamOperator<TargetBean> targetBeanSingleOutputStreamOperator = mappedStream.returns(WebSourceBean.class)
                .flatMap(new WebFlatMapFunction())
                .setParallelism(MyConstant.DEFAULT_PARALLELISM);;

        return targetBeanSingleOutputStreamOperator;
    }

    //web专区处理路由类
    public static SingleOutputStreamOperator<TargetBean> processWebPrefecture(SingleOutputStreamOperator<String> mappedStream) {
        SingleOutputStreamOperator<TargetBean> targetBeanSingleOutputStreamOperator = mappedStream.returns(String.class)
                .flatMap(new WebPrefectureMapFunction())
                .assignTimestampsAndWatermarks(new BoundedOutOfOrdernessTimestampExtractor<WebPrefectureSourceBean>(Time.seconds(MyConstant.MAX_LATENESS)) {
                    @Override
                    public long extractTimestamp(WebPrefectureSourceBean sourceBean) {
                        return sourceBean.getIts();
                    }
                })
                .keyBy(new WebPrefectureKeyByFunction())
                .window(TumblingEventTimeWindows.of(Time.minutes(1)))
                .apply(new WebPrefectureWindowFunction())
                .setParallelism(MyConstant.DEFAULT_PARALLELISM);

        return targetBeanSingleOutputStreamOperator;
    }

    //taf处理路由类
    public static SingleOutputStreamOperator<TargetBean> processTaf(SingleOutputStreamOperator<TafSourceEvent> mappedStream, CorpType corpType, BgType bgType) {
        SingleOutputStreamOperator<TargetBean> targetBeanSingleOutputStreamOperator = mappedStream.flatMap(new TafFlatMapFunction(corpType, bgType))
                .setParallelism(MyConstant.DEFAULT_PARALLELISM);

        return targetBeanSingleOutputStreamOperator;
    }
}
