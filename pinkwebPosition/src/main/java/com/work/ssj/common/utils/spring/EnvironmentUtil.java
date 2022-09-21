package com.work.ssj.common.utils.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wjning
 * @date 2021/4/13 17:19
 * @description 获取当前环境
 */
@Component
public class EnvironmentUtil {

    public static String thisSystemParam;

    @Autowired
    public void setThisSystemParam(@Value("${spring.profiles.active}")String thisSystemParam) {
        EnvironmentUtil.thisSystemParam = thisSystemParam;
    }
}
