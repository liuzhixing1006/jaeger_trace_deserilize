package com.huya.servicechain.domain.grpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @ClassName Span
 * @Description Trace指标转换逻辑
 * @Author liuzhixing
 * @Date 2019-09-18 17:59
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Span implements Serializable {
    public String traceId;
    public String spanId;
    public String serviceName;
    public String operationName;
    public long duration;
    public long startTimeMicros;
    public long modTimeMicros;
    public List<Log> logs;
    public Map<String, String> process;
    public Map<String, String> tags;
}