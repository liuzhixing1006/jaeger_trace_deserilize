package com.huya.servicechain.functions.deserialize;

import com.huya.servicechain.domain.grpc.Span;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.util.serialization.KeyedDeserializationSchema;

/**
 * @program: KafkaSourceParseSchema
 * @description: kafka数据源反序列化
 * @author: liuzhixing
 * @create: 2019-11-13 12:31
 */
@Slf4j
public class KafkaSourceParseSchema implements KeyedDeserializationSchema<Span> {
    @Override
    public Span deserialize(byte[] messageKey, byte[] message, String topic, int partition, long offset) {
        try {
            return ProtoBufDeserialize.protoBufDeserialize.deserialize(message);
        } catch (Exception e) {
            log.error("--------MyKafkaSourceSchema deserialize解析异常", e);
            return null;
        }
    }

    @Override
    public boolean isEndOfStream(Span nextElement) {
        return false;
    }

    @Override
    public TypeInformation<Span> getProducedType() {
        return TypeInformation.of(Span.class);
    }
}
