package com.huya.servicechain.utils.schema;

import com.huya.beelogsvr.model.LogSvrRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.util.serialization.KeyedDeserializationSchema;

/**
 * @program: SlsaKafkaSourceParseSchema
 * @description: 认证解析的kafka
 * @author: Stone
 * @create: 2019-09-24 15:31
 */
@Slf4j
public class SlsaKafkaSourceParseSchema implements KeyedDeserializationSchema<LogSvrRecord> {

    @Override
    public LogSvrRecord deserialize(byte[] messageKey, byte[] message, String topic, int partition, long offset)  {

        if (message == null) {
            return null;
        }
        try {
            LogSvrRecord logSvrRecord = LogSvrRecord.parseRawRecord(messageKey, message).get(0);
            return logSvrRecord;
        } catch (Exception e) {
            log.error("--------MyKafkaSourceSchema 解析异常");
            return null;
        }

    }

    @Override
    public boolean isEndOfStream(LogSvrRecord nextElement) {
        return false;
    }


    @Override
    public TypeInformation<LogSvrRecord> getProducedType() {
        return TypeInformation.of(LogSvrRecord.class);
    }


}
