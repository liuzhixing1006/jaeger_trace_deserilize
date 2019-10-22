package com.huya.servicechain.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName BucketUtils
 * @Description 分桶相关的处理类
 * @Author liuzhixing
 * @Date 2019-10-16 11:50
 **/
public class BucketUtils {
    public static Map<Integer, Long> initialBucket() {
        Map<Integer, Long> bucket = new HashMap<>(16);
        bucket.put(5, 0L);
        bucket.put(10, 0L);
        bucket.put(50, 0L);
        bucket.put(100, 0L);
        bucket.put(200, 0L);
        bucket.put(500, 0L);
        bucket.put(1000, 0L);
        bucket.put(2000, 0L);
        bucket.put(3000, 0L);

        return bucket;
    }

    public static void addDurationToBucket(Map<Integer, Long> bucket, long duration, long step) {
        if (duration < 5) {
            bucket.put(5, bucket.get(5) + step);
        } else if (duration < 10) {
            bucket.put(10, bucket.get(10) + step);
        } else if (duration < 50) {
            bucket.put(50, bucket.get(50) + step);
        } else if (duration < 100) {
            bucket.put(100, bucket.get(100) + step);
        } else if (duration < 200) {
            bucket.put(200, bucket.get(200) + step);
        } else if (duration < 500) {
            bucket.put(500, bucket.get(500) + step);
        } else if (duration < 1000) {
            bucket.put(1000, bucket.get(1000) + step);
        } else if (duration < 2000) {
            bucket.put(2000, bucket.get(2000) + step);
        } else {
            bucket.put(3000, bucket.get(3000) + step);
        }
    }
}
