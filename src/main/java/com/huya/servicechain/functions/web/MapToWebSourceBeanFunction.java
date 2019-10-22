package com.huya.servicechain.functions.web;

import com.alibaba.fastjson.JSON;
import com.huya.servicechain.domain.source.WebSourceBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @ClassName MapToWebSourceBeanFunction
 * @Description web主站反序列化器
 * @Author jasper
 * @Date 2019-08-29 14:20
 **/
@Slf4j
public class MapToWebSourceBeanFunction implements MapFunction<String, WebSourceBean> {
    @Override
    public WebSourceBean map(String s) {
        String replaced = s.replace(",\"interface\":", ",\"interfacename\":");
        try {
            return JSON.parseObject(replaced, WebSourceBean.class);
        } catch (Exception e) {
            log.error("序列化失败！", e);
            return null;
        }
    }
}
