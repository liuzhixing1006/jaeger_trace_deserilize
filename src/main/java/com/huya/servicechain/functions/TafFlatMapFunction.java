package com.huya.servicechain.functions;

import com.alibaba.fastjson.JSON;
import com.huya.servicechain.domain.source.TafSourceEvent;
import com.huya.servicechain.domain.target.TargetBean;
import com.huya.servicechain.utils.enums.BgType;
import com.huya.servicechain.utils.enums.CorpType;
import org.apache.flink.api.common.functions.RichFlatMapFunction;
import org.apache.flink.util.Collector;

/**
 * @ClassName TafFlatMapFunction
 * @Description TAF数据源转换函数
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

        targetBean.setCorp(corpType.getType());
        targetBean.setBg(bgType.getType());
        targetBean.setIts(yomeSourceBean.getIts());
        targetBean.setSource(yomeSourceBean.getMaster_name());
        targetBean.setTarget(yomeSourceBean.getSlave_name());
        targetBean.setSourceIp(yomeSourceBean.getMaster_ip());
        targetBean.setTargetIp(yomeSourceBean.getSlave_ip());
        targetBean.setTargetInterface(yomeSourceBean.getInterface_name());
        targetBean.setTotalTime((long) yomeSourceBean.getTotal_time());
        targetBean.setCount((long) yomeSourceBean.getExce_count() + yomeSourceBean.getSucc_count() + yomeSourceBean.getTimeout_count());
        targetBean.setSuccessCount((long) yomeSourceBean.getSucc_count());
        targetBean.setSeq_5(yomeSourceBean.getSeg_5());
        targetBean.setSeq_10(yomeSourceBean.getSeg_10());
        targetBean.setSeq_50(yomeSourceBean.getSeg_50());
        targetBean.setSeq_100(yomeSourceBean.getSeg_100());
        targetBean.setSeq_200(yomeSourceBean.getSeg_200());
        targetBean.setSeq_500(yomeSourceBean.getSeg_500());
        targetBean.setSeq_1000(yomeSourceBean.getSeg_1000());
        targetBean.setSeq_2000(yomeSourceBean.getSeg_2000());
        targetBean.setSeq_3000(yomeSourceBean.getSeg_3000());

        collector.collect(targetBean);
    }
}
