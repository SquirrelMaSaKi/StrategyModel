package com.rj.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "REDISCACHE")
public interface MyFeign {
    @RequestMapping("/cache/getList/{key}")
    List<String> getList(@PathVariable("key") String key);

    @RequestMapping("/cache/get/{key}")
    String getValue(@PathVariable("key") String key);

    @RequestMapping("/cache/set/limit")
    Boolean set(@RequestParam("key") String key, @RequestParam("value") long value, @RequestParam("expireTime") int expireTime);

    @RequestMapping("/cache/set/incr")
    Long incr(@RequestParam("key") String key, @RequestParam("value") long value);

//    @RequestMapping("/get/object/{key}")
//    Object getObject(@PathVariable("key") String key);
}
