package com.huya.servicechain.function.nimo;

import com.huya.servicechain.domain.LogSreBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @ClassName MapToNimoSourceBeanFunciton
 * @Description 将带有NIMO-Key的流转换成TafSourceEvent类型的流
 * @Author liuzhixing
 * @Date 2019-09-19 14:46
 **/
@Slf4j
public class MapToNimoSourceBeanFunciton implements MapFunction<LogSreBean, String> {
    @Override
    public String map(LogSreBean logSreBean) {
        return logSreBean.getMessage();
    }
}
