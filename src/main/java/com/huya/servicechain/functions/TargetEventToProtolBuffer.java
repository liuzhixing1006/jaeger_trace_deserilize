package com.huya.servicechain.functions;

import com.huya.servicechain.domain.target.TargetBean;
import com.huya.servicechain.domain.target.protobuf.TargetProtoBuf;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.functions.MapFunction;

/**
 * @ClassName TargetEventToProtolBuffer
 * @Description targetEvent转换成protolBuffer
 * @Author liuzhixing
 * @Date 2019-08-29 14:20
 **/
@Slf4j
public class TargetEventToProtolBuffer implements MapFunction<TargetBean, byte[]> {
    @Override
    public byte[] map(TargetBean targetBean) {
        try {
            TargetProtoBuf.VersionMessage.Builder builder = TargetProtoBuf.VersionMessage.newBuilder();

            builder.setIts(targetBean.getIts());
            builder.setCorp(targetBean.getCorp());
            builder.setBg(targetBean.getBg());
            builder.setSource(targetBean.getSource());
            builder.setTarget(targetBean.getTarget());
            builder.setTargetInterface(targetBean.getTargetInterface());
            builder.setSourceIp(targetBean.getSourceIp());
            builder.setTargetIp(targetBean.getTargetIp());
            builder.setCount(targetBean.getCount());
            builder.setSuccessCount(targetBean.getSuccessCount());
            builder.setTotalTime(targetBean.getTotalTime());
            builder.setSeq5(targetBean.getSeq_5());
            builder.setSeq10(targetBean.getSeq_10());
            builder.setSeq50(targetBean.getSeq_50());
            builder.setSeq100(targetBean.getSeq_100());
            builder.setSeq200(targetBean.getSeq_200());
            builder.setSeq500(targetBean.getSeq_500());
            builder.setSeq1000(targetBean.getSeq_1000());
            builder.setSeq2000(targetBean.getSeq_2000());
            builder.setSeq3000(targetBean.getSeq_3000());

            TargetProtoBuf.VersionMessage build = builder.build();
            return build.toByteArray();
        } catch (Exception e) {
            log.error("序列化失败！", e);
            return null;
        }
    }
}
