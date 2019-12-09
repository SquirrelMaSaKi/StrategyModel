package com.rj;

import com.rj.service.CacheService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = RedisCacheStartApp.class)
public class RedisCacheTest {
    @Autowired
    private CacheService cacheService;

    @Test
    public void testGetList() {
        List<String> filterlist = cacheService.getList("FILTERLIST");
        for (String s : filterlist) {
            System.err.println(s);
        }
    }
}
