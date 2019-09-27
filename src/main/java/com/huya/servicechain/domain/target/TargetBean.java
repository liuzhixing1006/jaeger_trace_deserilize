package com.huya.servicechain.domain.target;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName TargetBean
 * @Description 调用链公共目标对象
 * @Author jasper
 * @Date 2019-08-29 15:04
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TargetBean {
    long its;
    int corp;
    int bg;
    String source;
    String target;
    String targetInterface;
    String sourceIp;
    String targetIp;
    int sourcePort;
    int targetPort;
    long count;
    long successCount;
    long totalTime;
    String periods;
}
