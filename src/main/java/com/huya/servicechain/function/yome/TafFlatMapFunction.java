package com.huya.servicechain.function.yome;

import com.alibaba.fastjson.JSON;
import com.huya.servicechain.domain.source.TafSourceEvent;
import com.huya.servicechain.domain.target.TargetBean;
import com.huya.servicechain.utils.enums.BgType;
import com.huya.servicechain.utils.enums.CorpType;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.util.Collector;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName YomeFlatMapFunction
 * @Description Yome数据源转换函数
 * @Author liuzhixing
 * @Date 2019-09-10 14:24
 **/
public class TafFlatMapFunction extends RichFlatMapFunction<TafSourceEvent, TargetBean> {
    private CorpType corpType;

    private BgType bgType;


    public TafFlatMapFunction(CorpType corpType, BgType bgType) {
        this.corpType = corpType;
        this.bgType = bgType;
    }

    @Override
    public void flatMap(TafSourceEvent yomeSourceBean, Collector<TargetBean> collector) throws Exception {
        TargetBean targetBean = new TargetBean();
        Map<Integer, Long> periods = new HashMap<>(16);

        targetBean.setCorp(corpType.getType());
        targetBean.setBg(bgType.getType());
        targetBean.setIts(yomeSourceBean.getIts() * 1000);
        targetBean.setSource(yomeSourceBean.getMaster_name());
        targetBean.setTarget(yomeSourceBean.getSlave_name());
        targetBean.setSourceIp(yomeSourceBean.getMaster_ip());
        targetBean.setTargetIp(yomeSourceBean.getSlave_ip());
        targetBean.setTargetInterface(yomeSourceBean.getInterface_name());
        targetBean.setTotalTime((long) yomeSourceBean.getTotal_time());
        targetBean.setCount((long) yomeSourceBean.getExce_count() + yomeSourceBean.getSucc_count() + yomeSourceBean.getTimeout_count());
        targetBean.setSuccessCount((long) yomeSourceBean.getSucc_count());

        buildPeriods(yomeSourceBean, periods);
        targetBean.setPeriods(JSON.toJSONString(periods));

        collector.collect(targetBean);
    }

    private void buildPeriods(TafSourceEvent yomeSourceBean, Map<Integer, Long> periods) {
        addPeriodInstance(periods, 5, yomeSourceBean.getSeg_5());
        addPeriodInstance(periods, 10, yomeSourceBean.getSeg_10());
        addPeriodInstance(periods, 50, yomeSourceBean.getSeg_50());
        addPeriodInstance(periods, 100, yomeSourceBean.getSeg_100());
        addPeriodInstance(periods, 200, yomeSourceBean.getSeg_200());
        addPeriodInstance(periods, 500, yomeSourceBean.getSeg_500());
        addPeriodInstance(periods, 1000, yomeSourceBean.getSeg_1000());
        addPeriodInstance(periods, 2000, yomeSourceBean.getSeg_2000());
        addPeriodInstance(periods, 3000, yomeSourceBean.getSeg_3000());
    }

    private void addPeriodInstance(Map<Integer, Long> periodsMap, int key, Long value) {
        if (value != 0) {
            if (!periodsMap.containsKey(key)) {
                periodsMap.put(key, value);
            } else {
                Long duration = periodsMap.get(key);
                duration += value;
                periodsMap.put(key, duration);
            }
        }
    }
}
