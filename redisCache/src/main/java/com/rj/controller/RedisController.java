package com.rj.controller;

import com.rj.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cache")
public class RedisController {
    @Autowired
    private CacheService cacheService;

    @RequestMapping("/getList/{key}")
    public List<String> getList(@PathVariable("key") String key) {
        return cacheService.getList(key);
    }

    @RequestMapping("/get/{key}")
    public String getValue(@PathVariable("key") String key) {
        return cacheService.getValue(key);
    }

    @RequestMapping("/set/limit")
    public Boolean set(@RequestParam("key") String key, @RequestParam("value") long value, @RequestParam("expireTime") int expireTime) {
        return cacheService.set(key, value, expireTime);
    }

    @RequestMapping("/set/incr")
    public Long incr(@RequestParam("key") String key, @RequestParam("value") long value) {
        return cacheService.incr(key, value);
    }

    @RequestMapping("/get/object/{key}")
    public Object getObject(@PathVariable("key") String key) {
        return cacheService.getObject(key);
    }
}
