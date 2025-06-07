package com.springboot.journalApp.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@Disabled
@SpringBootTest
public class RedisTest {

	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	void testRedis() {
		redisTemplate.opsForValue().set("email", "gmail@email.com");
		System.out.println(redisTemplate.opsForValue().get("email"));
	}
}
