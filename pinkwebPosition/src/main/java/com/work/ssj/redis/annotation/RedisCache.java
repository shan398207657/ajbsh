package com.work.ssj.redis.annotation;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RedisCache
{
    /**
     * 键名
     */
    String key() default "";

    /**
     * 主键
     */
    String fieldKey();

    /**
     * 过期时间
     */
    long expired() default 3600;

    /**
     * 是否为查询操作
     * 如果为写入数据库的操作，该值需置为 false
     */
    boolean read() default true;
}