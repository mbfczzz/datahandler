package com.datahandler.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Description: Redis序列化配置
 * @Author: Naccl
 * @Date: 2020-09-27
 */
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		// 设置 key 的序列化器
		template.setKeySerializer(new StringRedisSerializer());
		// 设置 value 的序列化器
		template.setValueSerializer(new StringRedisSerializer());
		return template;
	}
}