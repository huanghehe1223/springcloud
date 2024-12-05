package com.upc.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;

    //value为java对象
    public <T> void setObject(String key, T value)
    {
        redisTemplate.opsForValue().set(key, value);

    }

    //TimeUnit时间粒度,TimeUnit.SECONDS,TimeUnit.MINUTES,TimeUnit.HOURS
    public <T> void setObject(String key, T value, Integer timeout, TimeUnit timeUnit)
    {
        redisTemplate.opsForValue().set(key, value,timeout,timeUnit);

    }

    //取到null说明没有key，或者key过期了
    public <T> T getObject(String key)
    {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    //value为String字符串
    public void  setString(String key, String value)
    {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    public void  setString(String key, String value,Integer timeout, TimeUnit timeUnit)
    {
        stringRedisTemplate.opsForValue().set(key, value,timeout,timeUnit);
    }

    public String getString(String key)
    {
        return stringRedisTemplate.opsForValue().get(key);
    }

    //设置某个key的过期时间
    public boolean setTimeout(String key,Integer timeout, TimeUnit timeUnit)
    {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    //检查key是否存在
    public boolean keyExists(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    //删除某个key
    public  boolean delete(String key)
    {
        Boolean delete = redisTemplate.delete(key);
        return delete;

    }


}
