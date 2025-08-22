package com.datahandler.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // 存储数据
    public void setValue(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    // 获取数据
    public Object getValue(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // 删除数据
    public void deleteValue(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 查询符合指定前缀的所有键
     *
     * @param prefix 键的前缀
     * @return 符合条件的键集合
     */
    public Set<String> getKeysByPrefix(String prefix) {
        // 使用通配符 * 匹配所有符合前缀的键
        return redisTemplate.keys(prefix + "*");
    }

    /**
     * 判断某个键是否存在
     *
     * @param key 键
     * @return 是否存在
     */
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 批量获取多个 key 的值
     */
    public Map<String, String> multiGet(Set<String> keys) {
        if (keys == null || keys.isEmpty()) {
            return Collections.emptyMap();
        }

        List<Object> values = redisTemplate.opsForValue().multiGet(keys);
        Map<String, String> result = new HashMap<>();

        int i = 0;
        for (String key : keys) {
            result.put(key, (String) values.get(i++));
        }
        return result;
    }
}