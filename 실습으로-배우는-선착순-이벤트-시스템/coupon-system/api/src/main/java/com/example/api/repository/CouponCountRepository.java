package com.example.api.repository;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import jakarta.annotation.PostConstruct;

@Repository
public class CouponCountRepository {

	private final RedisTemplate<String, String> redisTemplate;

	public CouponCountRepository(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@PostConstruct
	public void post() {
		redisTemplate.getConnectionFactory().getConnection().flushAll();
	}

	public Long increment() {
		return redisTemplate
			.opsForValue()
			.increment("coupon_count");
	}
}
