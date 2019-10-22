package com.huya.servicechain.task;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.huya.servicechain.task.model.WebInterfaceBean;
import lombok.extern.log4j.Log4j2;
import org.unidal.helper.Files;
import org.unidal.helper.Urls;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WebPrefectureTask
 * @Description web专区域名和服务数据映射关系维护定时服务
 * @Author liuzhixing
 * @Date 2019-09-26 19:35
 **/
@Log4j2
public class WebPrefectureTask {
    public static Map<String, String> webmapper = new HashMap<>(16);

    private static String URL = "http://service-chain.huya.com/commons/huya/webZoneMapping";

    public static void WebMapperUpdateTask() {
        try {
            InputStream inputStream = Urls.forIO().connectTimeout(1000).readTimeout(10000).openStream(URL);
            String content = Files.forIO().readFrom(inputStream, "utf-8");

            JSONObject sourceObject = JSON.parseObject(content);
            if (sourceObject != null) {
                JSONObject data = sourceObject.getJSONObject("data");

                if (data != null) {
                    JSONArray array = data.getJSONArray("data");

                    for (int i = 0; i < array.size(); ++i) {
                        String str = JSON.toJSONString(array.getJSONObject(i));
                        WebInterfaceBean bean = JSON.parseObject(str, WebInterfaceBean.class);

                        if (!bean.getAppName().isEmpty()) {
                            webmapper.put(bean.getDomain() + ":" + bean.getPort(), bean.getAppName());
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e);
        }
    }
}
