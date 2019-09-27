package com.huya.servicechain.domain.source;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName SourceBean
 * @Description YOME原始日志
 * @Author jasper
 * @Date 2019-08-28 14:55
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TafSourceEvent {
    long its;
    String master_name;
    String master_set;
    String slave_name;
    String slave_set;
    String interface_name;
    String master_ip;
    String slave_ip;
    int slave_port;
    String taf_version;
    int return_value;
    int succ_count;
    int timeout_count;
    int exce_count;
    int total_time;
    int maxrsp_time;
    int minrsp_time;
    long seg_5;
    long seg_10;
    long seg_50;
    long seg_100;
    long seg_200;
    long seg_500;
    long seg_1000;
    long seg_2000;
    long seg_3000;
}
