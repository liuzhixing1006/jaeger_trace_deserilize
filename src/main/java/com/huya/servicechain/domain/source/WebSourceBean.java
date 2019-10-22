package com.huya.servicechain.domain.source;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName WebSourceBean
 * @Description WEB主站原始日志
 * @Author liuzhixing
 * @Date 2019-09-02 12:07
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebSourceBean {
    long its;
    String providername;
    String providerip;
    String interfacename;
    String consumerip;
    String consumername;
    double px_suc;
    double invoke_suc;
    double maxtime;
    double sum;
    double invoketime;
    long invoketime_seg;
    double pxtime;
    long pxtime_seg;
    String periods;
}
