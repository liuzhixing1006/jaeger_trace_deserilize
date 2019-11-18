package com.huya.servicechain.functions.deserialize;

import com.google.protobuf.ByteString;
import com.google.protobuf.util.Timestamps;
import com.huya.servicechain.domain.grpc.Log;
import com.huya.servicechain.domain.grpc.Model;
import com.huya.servicechain.domain.grpc.Span;
import org.apache.flink.api.common.serialization.AbstractDeserializationSchema;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName ProtoBufDeserialize
 * @Description OpenTracing数据源的ProtoBuffer反序列化操作类
 * @Author liuzhixing
 * @Date 2019-11-13 11:45
 **/
public class ProtoBufDeserialize extends AbstractDeserializationSchema<Span> {
    private static final long serialVersionUID = 7952979116702613945L;

    public static ProtoBufDeserialize protoBufDeserialize = new ProtoBufDeserialize();

    @Override
    public Span deserialize(byte[] message) throws IOException {
        Model.Span protoSpan = Model.Span.parseFrom(message);
        return fromProto(protoSpan);
    }

    private Span fromProto(Model.Span protoSpan) {
        Span span = new Span();
        protoSpan.getDuration().getSeconds();
        span.traceId = asHexString(protoSpan.getTraceId()).toLowerCase().substring(16);
        span.spanId = asHexString(protoSpan.getSpanId()).toLowerCase();
        span.operationName = protoSpan.getOperationName();
        span.serviceName = protoSpan.getProcess().getServiceName();
        span.startTimeMicros = Timestamps.toMicros(protoSpan.getStartTime());
        span.duration = protoSpan.getDuration().getNanos();
        span.logs = new LinkedList<>();
        span.tags = new HashMap<>(16);
        span.process = new HashMap<>(16);

        //1.反序列化Process
        for (Model.KeyValue kv : protoSpan.getProcess().getTagsList()) {
            if (!Model.ValueType.STRING.equals(kv.getVType())) {
                continue;
            }
            String value = kv.getVStr();
            if (value != null) {
                span.process.put(kv.getKey(), value);
            }
        }

        //2.反序列化错误日志
        for (Model.Log kv : protoSpan.getLogsList()) {
            Log log = new Log(Timestamps.toMicros(kv.getTimestamp()), new HashMap<>(16));

            List<Model.KeyValue> fieldsList = kv.getFieldsList();
            if (fieldsList != null) {
                for (Model.KeyValue field : fieldsList) {
                    log.fields.put(field.getKey(), field.getVStr());
                }
            }
            span.logs.add(log);
        }

        //3.反序列化tags
        for (Model.KeyValue kv : protoSpan.getTagsList()) {
            if (!Model.ValueType.STRING.equals(kv.getVType())) {
                continue;
            }
            String value = kv.getVStr();
            if (value != null) {
                span.tags.put(kv.getKey(), value);
            }
        }

        return span;
    }

    private static final String HEXES = "0123456789ABCDEF";

    private String asHexString(ByteString id) {
        ByteString.ByteIterator iterator = id.iterator();
        StringBuilder out = new StringBuilder();
        while (iterator.hasNext()) {
            byte b = iterator.nextByte();
            out.append(HEXES.charAt((b & 0xF0) >> 4)).append(HEXES.charAt((b & 0x0F)));
        }
        return out.toString();
    }
}
