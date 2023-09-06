package com.example.consumer.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class FailedEvent {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long userId;

	public FailedEvent() {

	}

	public FailedEvent(Long userId) {
		this.userId = userId;
	}
}
