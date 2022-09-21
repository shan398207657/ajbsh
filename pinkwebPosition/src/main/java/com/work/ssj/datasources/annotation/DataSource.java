package com.work.ssj.datasources.annotation;

import java.lang.annotation.*;

/**
 * 多数据源注解
 * @author zh
 * @email 45838976@qq.com
 * @date 2017/9/16 22:16
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DataSource {
    String name() default "";
}
