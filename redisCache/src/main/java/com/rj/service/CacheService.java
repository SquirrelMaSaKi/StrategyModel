package com.rj.service;

import java.util.List;

public interface CacheService {
    List<String> getList(String key);

    String getValue(String key);

    Boolean set(String key, long value, int expireTime);

    Long incr(String key, long value);

    Object getObject(String key);
}
