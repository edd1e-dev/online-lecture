package com.example.stock.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.stock.domain.Stock;
import com.example.stock.repository.StockRepository;

@Service
public class StockService {

	private final StockRepository stockRepository;

	public StockService(StockRepository stockRepository) {
		this.stockRepository = stockRepository;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void decrease(Long id, Long quantity) {
		// Stock 조회
		Stock stock = stockRepository.findById(id).orElseThrow();

		// 재고를 감소시킨 뒤
		stock.decrease(quantity);

		// 갱신된 값 저장
		stockRepository.saveAndFlush(stock);
	}
}
