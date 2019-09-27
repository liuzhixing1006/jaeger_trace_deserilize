package com.huya.servicechain.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: LogSreBean
 * @description: 原始日志类
 * @author: liuzhixing
 * @create: 2019-09-16 17:20
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogSreBean {
    String messageKey;
    String message;

    public static LogSreBean parseRawRecord(byte[] messageKey, byte[] message) {
        if (messageKey != null || null != message) {
            return new LogSreBean(new String(messageKey), new String(message));
        }
        return null;
    }
}
