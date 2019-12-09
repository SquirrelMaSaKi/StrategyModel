package com.rj.service.impl;

import com.rj.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class CacheServiceImpl implements CacheService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public List<String> getList(String key) {
        List<String> list = redisTemplate.opsForList().range(key, 0, -1);
        return list;
    }

    @Override
    public String getValue(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }

    @Override
    public Boolean set(String key, long value, int expireTime) {
        try {
            String count = String.valueOf(value);
            redisTemplate.opsForValue().set(key, count, expireTime, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Long incr(String key, long value) {
        return redisTemplate.opsForValue().increment(key, value);
    }

    @Override
    public Object getObject(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
