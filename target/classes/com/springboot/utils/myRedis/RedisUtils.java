package com.springboot.utils.myRedis;

import com.springboot.utils.myMap.MapUntils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import static com.springboot.config.StaticConfig.REDIS_EFFECTIVE_TIME;
import static com.springboot.config.StaticConfig.REDIS_EFFECTIVE_TIMEUNIT;

/**
 * redis操作工具类.</br>
 * (基于RedisTemplate)
 *
 */
@Component
public class RedisUtils {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public <T> T getBean(final String key, T bean) {
        MapUntils.mapToBean(MapUntils.stringToMap(redisTemplate.opsForValue().get(key)), bean);
        return bean;
    }

    /**
     * 写入缓存
     */
    public boolean set(final String key, String value) {
        boolean result = false;
        try {
            // 启动过期时间
            if (REDIS_EFFECTIVE_TIME == -1L) {
                // 设置没有过期时间
                redisTemplate.opsForValue().set(key, value);
            } else {
                redisTemplate.opsForValue().set(key, value, REDIS_EFFECTIVE_TIME, REDIS_EFFECTIVE_TIMEUNIT);
            }
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 更新缓存
     */
    public boolean getAndSet(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除缓存
     */
    public boolean delete(final String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}