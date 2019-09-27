package com.huya.servicechain.domain.source;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName WebPrefectureSourceBean
 * @Description web专区日志数据
 * @Author liuzhixing
 * @Date 2019-09-26 10:21
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebPrefectureSourceBean {
    long its;
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
}
