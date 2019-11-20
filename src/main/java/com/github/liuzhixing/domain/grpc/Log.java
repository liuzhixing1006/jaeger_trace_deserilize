package com.github.liuzhixing.domain.grpc;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

/**
 * @ClassName Log
 * @Description Trace日志格式
 * @Author liuzhixing
 * @Date 2019-09-18 17:59
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log implements Serializable {
    public long timestamp;
    public Map<String, String> fields;
}