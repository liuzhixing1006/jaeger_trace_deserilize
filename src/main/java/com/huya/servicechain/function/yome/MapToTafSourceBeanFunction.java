package com.huya.servicechain.function.yome;

import com.alibaba.fastjson.JSON;
import com.huya.servicechain.domain.source.TafSourceEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @ClassName MapToYomeSourceBeanFunction
 * @Description Taf数据反序列化器
 * @Author jasper
 * @Date 2019-08-29 14:20
 **/
@Slf4j
public class MapToTafSourceBeanFunction implements MapFunction<String, TafSourceEvent> {
    @Override
    public TafSourceEvent map(String s) {
        try {
            return JSON.parseObject(s, TafSourceEvent.class);
        } catch (Exception e) {
            log.error("序列化失败！", e);
            return null;
        }
    }
}
