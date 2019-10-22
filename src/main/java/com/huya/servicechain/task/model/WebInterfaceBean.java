package com.huya.servicechain.task.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ClassName WebInterfaceBean
 * @Description web专区接口拉取原始数据类型
 * @Author liuzhixing
 * @Date 2019-09-26 19:38
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebInterfaceBean {
    String appName;
    String domain;
    int port;
}
