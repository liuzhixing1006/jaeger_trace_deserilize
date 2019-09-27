package com.huya.servicechain.function.webprefecture;

import com.huya.servicechain.domain.source.WebPrefectureSourceBean;
import com.huya.servicechain.utils.task.WebPrefectureTask;
import com.huya.servicechain.utils.task.model.WebInterfaceBean;
import lombok.extern.log4j.Log4j2;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.util.Collector;
import org.unidal.helper.Splitters;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @ClassName WebPrefectureMapFunction
 * @Description web专区转换函数
 * @Author liuzhixing
 * @Date 2019-09-26 10:30
 **/

@Log4j2
public class WebPrefectureMapFunction implements FlatMapFunction<String, WebPrefectureSourceBean> {
    @Override
    public void flatMap(String source, Collector<WebPrefectureSourceBean> collector) throws Exception {
        try {
            List<String> datas = Splitters.by("#_#").split(source);
            List<String> ipports = Splitters.by(":").split(datas.get(2));

            String time = datas.get(3);
            long its = transferTime(time.substring(1, time.length() - 1));

            String hostName = datas.get(0);
            String sourceIp = datas.get(1);
            String targetIp = ipports.get(0);
            int sourcePort = Integer.parseInt(datas.get(datas.size() - 1));
            int targetPort = Integer.parseInt(ipports.get(1));

            long count = 1;
            long successCount = "200".equals(datas.get(5)) ? 1 : 0;
            long totalTime = (long) (Float.parseFloat(datas.get(9)) * 1000L + Float.parseFloat(datas.get(10)) * 1000L);
            String target = findTargetNameByHost(hostName, targetPort);

            if (!target.isEmpty()) {
                WebPrefectureSourceBean sourceBean = new WebPrefectureSourceBean(its, hostName, target, "HTTP_CALL", sourceIp, targetIp, sourcePort, targetPort, count, successCount, totalTime);
                collector.collect(sourceBean);
            }
        } catch (Exception e) {
            log.error(e);
        }
    }

    private long transferTime(String time) {
        long result = 0;

        try {
            Date date = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss z", Locale.US).parse(time);
            result = date.getTime();
        } catch (Exception e) {
            log.error(e);
        }

        return result;
    }

    private String findTargetNameByHost(String hostName, int targetPort) {
        Map<String, String> webmapper = WebPrefectureTask.webmapper;
        String key = hostName + ":" + targetPort;

        if (webmapper.containsKey(key)) {
            return webmapper.get(key);
        }

        return "";
    }
}
