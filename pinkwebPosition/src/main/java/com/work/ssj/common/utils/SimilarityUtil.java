package com.work.ssj.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author wjning
 * @date 2021/8/26 11:35
 * @description 相似度工具，计算得分
 */
public class SimilarityUtil {

    /**
     * 获取最大得分的值
     * @param str 比较者
     * @param list 被比较者集合
     * @return max
     */
    public static String getMaxScore (String str, List<String> list) {
        Map<String,Double> map = new HashMap<>();
        for (String s : list) {
            double similar = StrUtil.similar(s, str);
            map.put(s, similar);
        }
        Double max = CollUtil.max(map.values());
        if (max == 0) {
            return null;
        }
        String maxKay = null;
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            if (Objects.equals(entry.getValue(), max)) {
                maxKay =  entry.getKey();
                break;
            }
        }
        return maxKay;
    }
}
