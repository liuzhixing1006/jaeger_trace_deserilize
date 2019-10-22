package com.huya.servicechain.functions.web;

import com.huya.servicechain.domain.source.WebSourceBean;
import com.huya.servicechain.domain.target.TargetBean;
import com.huya.servicechain.utils.BucketUtils;
import com.huya.servicechain.utils.enums.BgType;
import com.huya.servicechain.utils.enums.CorpType;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.util.Collector;
import org.unidal.helper.Splitters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName WebFlatMapFunction
 * @Description Web主站流转换函数
 * @Author liuzhixing
 * @Date 2019-08-28 17:36
 **/
public class WebFlatMapFunction extends RichFlatMapFunction<WebSourceBean, TargetBean> {
    @Override
    public void flatMap(WebSourceBean sourceBean, Collector<TargetBean> collector) {
        TargetBean targetBean = new TargetBean();

        targetBean.setCorp(CorpType.HUYA.getType());
        targetBean.setBg(BgType.HUYA_WEB_SERVICE.getType());
        targetBean.setIts(sourceBean.getIts());
        targetBean.setSource(sourceBean.getConsumername());
        targetBean.setTarget(sourceBean.getProvidername());
        targetBean.setSourceIp(sourceBean.getConsumerip());
        targetBean.setTargetIp(sourceBean.getProviderip());
        targetBean.setTargetInterface(sourceBean.getInterfacename());
        targetBean.setTotalTime((long) sourceBean.getInvoketime());
        targetBean.setCount((long) sourceBean.getSum());
        targetBean.setSuccessCount((long) sourceBean.getInvoke_suc());

        Map<Integer, Long> bucket = mergeBucket(sourceBean.getPeriods());
        targetBean.setSeq_5(bucket.get(5));
        targetBean.setSeq_10(bucket.get(10));
        targetBean.setSeq_50(bucket.get(50));
        targetBean.setSeq_100(bucket.get(100));
        targetBean.setSeq_200(bucket.get(200));
        targetBean.setSeq_500(bucket.get(500));
        targetBean.setSeq_1000(bucket.get(1000));
        targetBean.setSeq_2000(bucket.get(2000));
        targetBean.setSeq_3000(bucket.get(3000));

        collector.collect(targetBean);
    }

    private Map<Integer, Long> mergeBucket(String periods) {
        Map<Integer, Long> bucket = BucketUtils.initialBucket();
        Map<Integer, Long> data = parsePeriods(periods);

        for (Map.Entry<Integer, Long> ite : data.entrySet()) {
            Integer timeRange = ite.getKey();
            Long seqTime = ite.getValue();

            BucketUtils.addDurationToBucket(bucket, timeRange, seqTime);
        }

        return bucket;
    }

    private Map<Integer, Long> parsePeriods(String periods) {
        Map<Integer, Long> result = new HashMap<>(16);
        if (periods != null && !periods.isEmpty()) {
            List<String> mappers = Splitters.by(',').split(periods);

            for (String mapper : mappers) {
                List<String> iter = Splitters.by(':').split(mapper);
                result.put(Integer.valueOf(iter.get(0)), Long.valueOf(iter.get(1)));
            }
        }

        return result;
    }

}
