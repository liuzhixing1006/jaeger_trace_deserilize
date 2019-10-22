package com.huya.servicechain.functions.webprefecture;

import com.huya.servicechain.domain.source.WebPrefectureSourceBean;
import com.huya.servicechain.domain.target.TargetBean;
import com.huya.servicechain.utils.BucketUtils;
import com.huya.servicechain.utils.enums.BgType;
import com.huya.servicechain.utils.enums.CorpType;
import org.apache.flink.api.java.tuple.Tuple7;
import org.apache.flink.streaming.api.functions.windowing.RichWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Map;

/**
 * @ClassName WebPrefectureWindowFunction
 * @Description web专区窗口处理函数
 * @Author liuzhixing
 * @Date 2019-09-26 14:16
 **/
public class WebPrefectureWindowFunction extends RichWindowFunction<WebPrefectureSourceBean, TargetBean, Tuple7<String, String, String, String, String, Integer, Integer>, TimeWindow> {
    @Override
    public void apply(Tuple7<String, String, String, String, String, Integer, Integer> keys, TimeWindow timeWindow, Iterable<WebPrefectureSourceBean> iterable, Collector<TargetBean> collector) throws Exception {
        long time = timeWindow.getStart();
        long totalTime = 0;
        long count = 0;
        long successCount = 0;
        Map<Integer, Long> bucket = BucketUtils.initialBucket();

        for (WebPrefectureSourceBean bean : iterable) {
            long currentTime = bean.getTotalTime();
            BucketUtils.addDurationToBucket(bucket, currentTime, 1);

            totalTime += currentTime;
            count += bean.getCount();
            successCount += bean.getSuccessCount();
        }

        TargetBean targetBean = new TargetBean(time,
                CorpType.HUYA.getType(),
                BgType.HUYA_WEB_SERVICE.getType(),
                keys.f0,
                keys.f1,
                keys.f2,
                keys.f3,
                keys.f4,
                keys.f5,
                keys.f6,
                count,
                successCount,
                totalTime,
                bucket.get(5),
                bucket.get(10),
                bucket.get(50),
                bucket.get(100),
                bucket.get(200),
                bucket.get(500),
                bucket.get(1000),
                bucket.get(2000),
                bucket.get(3000));
        collector.collect(targetBean);
    }
}
