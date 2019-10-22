package com.huya.servicechain.utils.schema;

import com.huya.servicechain.domain.LogSreBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.util.serialization.KeyedDeserializationSchema;

/**
 * @program: KafkaSourceParseSchema
 * @description: kafka数据源解析
 * @author: Stone
 * @create: 2019-09-24 15:31
 */
@Slf4j
public class KafkaSourceParseSchema implements KeyedDeserializationSchema<LogSreBean> {

    @Override
    public LogSreBean deserialize(byte[] messageKey, byte[] message, String topic, int partition, long offset) {

        try {
            return LogSreBean.parseRawRecord(messageKey, message);
        } catch (Exception e) {
            log.error("--------MyKafkaSourceSchema deserialize解析异常", e);
            return null;
        }
    }

    @Override
    public boolean isEndOfStream(LogSreBean nextElement) {
        return false;
    }

    @Override
    public TypeInformation<LogSreBean> getProducedType() {
        return TypeInformation.of(LogSreBean.class);
    }
}
