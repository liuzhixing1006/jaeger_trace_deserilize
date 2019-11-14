package com.huya.servicechain.domain.grpc;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Collection;

/**
 * @ClassName Trace
 * @Description Trace指标转换逻辑
 * @Author liuzhixing
 * @Date 2019-09-18 17:59
 **/
@AllArgsConstructor
@NoArgsConstructor
public class Trace {
    public String traceId;
    public Collection<Span> spans;
}