package com.work.ssj.redis.util;

import com.alibaba.fastjson.JSON;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
public class RedisUtils
{
    @Resource(name = "redisTemplate")
    private RedisTemplate<String, Object> redisTemplate;

    /**  默认过期时长，单位：秒 */
    public final static long                DEFAULT_EXPIRE = 60 * 60 * 24;

    /**  不设置过期时长 */
    public final static long                NOT_EXPIRE     = -1;

    public String getStr(String key) {
        return get(key, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = (String) redisTemplate.opsForValue().get(key);
        if(expire != NOT_EXPIRE){
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }
    /**
     * 插入缓存默认时间
     * @param key 键
     * @param value 值
     * @author shansj
     */
    public void set(String key, Object value)
    {
        set(key, value, DEFAULT_EXPIRE);
    }

    /**
     * 插入缓存
     * @param key 键
     * @param value 值
     * @param expire 过期时间(s)
     * @author shansj
     */
    public void set(String key, Object value, long expire)
    {
        String s = toJson(value);
        redisTemplate.opsForValue().set(key, s);
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    /**
     * 插入缓存
     * @param key 键
     * @param value 值
     * @author shansj
     */
    public void setPop(String key, Object value)
    {
        String s = toJson(value);
        key = "pop" + ":" + key;
        redisTemplate.opsForSet().add(key,s);
        redisTemplate.expire(key, 6, TimeUnit.HOURS);
    }

    /**
     * 插入缓存
     * @param key 键
     * @author shansj
     */
    public <T> T pop(String key,Class<T> clazz)
    {
        key = "pop" + ":" + key;
        String value = (String) redisTemplate.opsForSet().pop(key);
        return value == null ? null : fromJson(value, clazz);
    }

    /**
     * 返回字符串结果
     * @param key 键
     * @return String
     * @author shansj
     */
    public String get(String key)
    {
        return (String) redisTemplate.opsForValue().get(key);
    }

    /**
     * 返回指定类型结果
     * @param key 键
     * @param clazz 类型class
     * @return T
     * @author shansj
     */
    public <T> T get(String key, Class<T> clazz)
    {
        String value = (String) redisTemplate.opsForValue().get(key);
        return value == null ? null : fromJson(value, clazz);
    }

    /**
     * 删除缓存
     * @param key 键
     * @author shansj
     */
    public void delete(String key)
    {
        redisTemplate.delete(key);
    }

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object)
    {
        if (object instanceof Integer || object instanceof Long || object instanceof Float || object instanceof Double
                || object instanceof Boolean || object instanceof String)
        {
            return String.valueOf(object);
        }
        return JSON.toJSONString(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz)
    {
        return JSON.parseObject(json, clazz);
    }
}