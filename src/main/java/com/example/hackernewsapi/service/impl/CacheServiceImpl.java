package com.example.hackernewsapi.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.example.hackernewsapi.service.CacheService;

@Service
public class CacheServiceImpl implements CacheService{
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	public void set(String key, String value) {
		redisTemplate.opsForValue().set(key, value, 10, TimeUnit.MINUTES);
	}

	public Object get(String key) {
		try {
			return redisTemplate.opsForValue().get(key);
		} catch (Exception e) {
			
		}
		return null;
	}


}
