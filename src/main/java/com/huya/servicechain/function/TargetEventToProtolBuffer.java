package com.huya.servicechain.function;

import com.huya.servicechain.domain.target.TargetBean;
import com.huya.servicechain.domain.target.TargetBuffer;
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
            TargetBuffer.VersionMessage.Builder builder = TargetBuffer.VersionMessage.newBuilder();

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
            builder.setPeriods(targetBean.getPeriods());

            TargetBuffer.VersionMessage build = builder.build();
            return build.toByteArray();
        } catch (Exception e) {
            log.error("序列化失败！", e);
            return null;
        }
    }
}
