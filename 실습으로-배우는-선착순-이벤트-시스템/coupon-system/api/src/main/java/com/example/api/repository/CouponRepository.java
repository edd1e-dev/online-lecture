package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.api.domain.Coupon;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
