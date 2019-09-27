package com.huya.servicechain.function.webprefecture;

import com.alibaba.fastjson.JSON;
import com.huya.servicechain.domain.source.WebPrefectureSourceBean;
import com.huya.servicechain.domain.target.TargetBean;
import com.huya.servicechain.utils.enums.BgType;
import com.huya.servicechain.utils.enums.CorpType;
import org.apache.flink.api.java.tuple.Tuple7;
import org.apache.flink.streaming.api.functions.windowing.RichWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.HashMap;
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
        Map<Integer, Integer> periods = new HashMap<>(16);

        for (WebPrefectureSourceBean bean : iterable) {
            long currentTime = bean.getTotalTime();
            int duration = computeDuration((int) currentTime);

            if (!periods.containsKey(duration)) {
                periods.put(duration, 1);
            } else {
                periods.put(duration, periods.get(duration) + 1);
            }

            totalTime += currentTime;
            count += bean.getCount();
            successCount += bean.getSuccessCount();
        }

        TargetBean targetBean = new TargetBean(time, CorpType.HUYA.getType(), BgType.HUYA_WEB_SERVICE.getType(), keys.f0, keys.f1, keys.f2, keys.f3, keys.f4, keys.f5, keys.f6, count, successCount, totalTime, JSON.toJSONString(periods));
        collector.collect(targetBean);
    }

    private static int computeDuration(int duration) {
        if (duration < 1) {
            return 1;
        } else if (duration < 20) {
            return duration;
        } else if (duration < 200) {
            return duration - duration % 5;
        } else if (duration < 500) {
            return duration - duration % 20;
        } else if (duration < 2000) {
            return duration - duration % 50;
        } else if (duration < 20000) {
            return duration - duration % 500;
        } else if (duration < 1000000) {
            return duration - duration % 10000;
        } else {
            int dk = 524288;

            if (duration > 3600 * 1000) {
                dk = 3600 * 1000;
            } else {
                while (dk < duration) {
                    dk <<= 1;
                }
            }
            return dk;
        }
    }
}
