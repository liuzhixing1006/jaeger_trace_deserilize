package com.huya.servicechain.functions.map;

import com.alibaba.fastjson.JSON;
import com.huya.servicechain.domain.grpc.Span;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @ClassName TraceMapFunction
 * @Description Trace指标转换逻辑，主要是用于适配Ck
 * @Author liuzhixing
 * @Date 2019-09-18 17:59
 **/
@Slf4j
public class TraceMapFunction implements MapFunction<Span, String> {
    @Override
    public String map(Span span) throws Exception {
        long startTimeMicros = span.getStartTimeMicros();

        long second = startTimeMicros / (1000 * 1000);
        long mod = startTimeMicros % (1000 * 1000);

        span.setStartTimeMicros(second);
        span.setModTimeMicros(mod);

        return JSON.toJSONString(span);
    }
}
