package com.huya.servicechain.function.web;

import com.alibaba.fastjson.JSON;
import com.huya.servicechain.domain.source.WebSourceBean;
import com.huya.servicechain.domain.target.TargetBean;
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
 * @Author jasper
 * @Date 2019-08-28 17:36
 **/
public class WebFlatMapFunction extends RichFlatMapFunction<WebSourceBean, TargetBean> {
    @Override
    public void flatMap(WebSourceBean sourceBean, Collector<TargetBean> collector) {
        TargetBean targetBean = new TargetBean();

        targetBean.setCorp(CorpType.HUYA.getType());
        targetBean.setBg(BgType.HUYA_WEB_SERVICE.getType());
        targetBean.setIts(sourceBean.getIts() * 1000);
        targetBean.setSource(sourceBean.getConsumername());
        targetBean.setTarget(sourceBean.getProvidername());
        targetBean.setSourceIp(sourceBean.getConsumerip());
        targetBean.setTargetIp(sourceBean.getProviderip());
        targetBean.setTargetInterface(sourceBean.getInterfacename());
        targetBean.setTotalTime((long) sourceBean.getInvoketime());
        targetBean.setCount((long) sourceBean.getSum());
        targetBean.setSuccessCount((long) sourceBean.getInvoke_suc());

        Map<Integer, Long> periods = parsePeriods(sourceBean.getPeriods());
        targetBean.setPeriods(JSON.toJSONString(periods));

        collector.collect(targetBean);
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
