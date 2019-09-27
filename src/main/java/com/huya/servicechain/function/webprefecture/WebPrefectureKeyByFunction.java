package com.huya.servicechain.function.webprefecture;

import com.huya.servicechain.domain.source.WebPrefectureSourceBean;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.tuple.Tuple7;

/**
 * @ClassName WebPrefectureKeyByFunction
 * @Description web专区keyby函数
 * @Author liuzhixing
 * @Date 2019-09-26 10:37
 **/
public class WebPrefectureKeyByFunction implements KeySelector<WebPrefectureSourceBean, Tuple7<String, String, String, String, String, Integer, Integer>> {
    @Override
    public Tuple7<String, String, String, String, String, Integer, Integer> getKey(WebPrefectureSourceBean sourceBean) throws Exception {
        Tuple7<String, String, String, String, String, Integer, Integer> result = new Tuple7<>(sourceBean.getSource(),
                                                                sourceBean.getTarget(),
                                                                sourceBean.getTargetInterface(),
                                                                sourceBean.getSourceIp(),
                                                                sourceBean.getTargetIp(),
                                                                sourceBean.getSourcePort(),
                                                                sourceBean.getTargetPort());

        return result;
    }
}
