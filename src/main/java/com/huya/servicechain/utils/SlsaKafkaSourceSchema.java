package com.huya.servicechain.utils;

import com.huya.beelogsvr.model.LogSvrRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.util.serialization.KeyedDeserializationSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: topology_source_transfer
 * @description: 认证解析的kafka
 * @author: Stone
 * @create: 2019-09-24 15:31
 */
@Slf4j
public class SlsaKafkaSourceSchema implements KeyedDeserializationSchema<LogSvrRecord> {

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
