package com.huya.servicechain.functions.nimo;

import com.huya.servicechain.domain.LogSreBean;
import com.huya.servicechain.utils.Constant;
import org.apache.flink.streaming.api.collector.selector.OutputSelector;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: transform_service
 * @description: 拆流
 * @author: liuzhixing
 * @create: 2019-09-18 17:20
 */
public class NimoTafKeySelector implements OutputSelector<LogSreBean> {
    @Override
    public Iterable<String> select(LogSreBean bean) {
        List<String> tags = new ArrayList<>();
        String key = bean.getMessageKey();

        if (key.startsWith(Constant.NIMO_SERVICE_KEY)) {
            tags.add(Constant.NIMO_SERVICE_KEY);
        }

        return tags;
    }
}
