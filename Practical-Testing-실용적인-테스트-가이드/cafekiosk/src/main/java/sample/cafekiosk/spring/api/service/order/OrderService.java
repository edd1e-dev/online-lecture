package sample.cafekiosk.spring.api.service.order;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import sample.cafekiosk.spring.api.controller.order.request.OrderCreateRequest;
import sample.cafekiosk.spring.api.service.order.response.OrderResponse;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;
import sample.cafekiosk.spring.domain.stock.Stock;
import sample.cafekiosk.spring.domain.stock.StockRepository;

@RequiredArgsConstructor
@Service
public class OrderService {

	private final ProductRepository productRepository;
	private final OrderRepository orderRepository;
	private final StockRepository stockRepository;

	/**
	 * 재고 감소 -> 동시성 문제
	 * Optimistic Lock / Pessimistic Lock / ...
	 */
	public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
		List<String> productNumbers = request.getProductNumbers();
		List<Product> duplicateProducts = findProductsBy(productNumbers);

		deductStockQuantities(duplicateProducts);

		Order order = Order.create(duplicateProducts, registeredDateTime);
		Order savedOrder = orderRepository.save(order);

		return OrderResponse.of(savedOrder);
	}

	private void deductStockQuantities(List<Product> duplicateProducts) {
		List<String> stockProductNumbers = extractStockProductNumbers(duplicateProducts);

		Map<String, Stock> stockMap = createStockMapBy(stockProductNumbers);
		Map<String, Long> productCountingMap = createCountingMapBy(stockProductNumbers);

		for (String stockProductNumber : new HashSet<>(stockProductNumbers)) {
			Stock stock = stockMap.get(stockProductNumber);
			int quantity = productCountingMap.get(stockProductNumber).intValue();

			if (stock.isQuantityLessThan(quantity)) {
				throw new IllegalArgumentException("재고가 부족한 상품이 있습니다.");
			}

			stock.deductQuantity(quantity);
		}
	}

	private List<String> extractStockProductNumbers(List<Product> duplicateProducts) {
		return duplicateProducts.stream()
			.filter(product -> ProductType.containsStockType(product.getType()))
			.map(Product::getProductNumber)
			.collect(Collectors.toList());
	}

	private Map<String, Stock> createStockMapBy(List<String> stockProductNumbers) {
		List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);
		Map<String, Stock> stockMap = stocks.stream()
			.collect(Collectors.toMap(Stock::getProductNumber, s -> s));
		return stockMap;
	}

	private static Map<String, Long> createCountingMapBy(List<String> stockProductNumbers) {
		return stockProductNumbers.stream()
			.collect(Collectors.groupingBy(p -> p, Collectors.counting()));
	}

	private List<Product> findProductsBy(List<String> productNumbers) {
		List<Product> products = productRepository.findAllByProductNumberIn(productNumbers);
		Map<String, Product> productMap = products.stream()
			.collect(Collectors.toMap(Product::getProductNumber, p -> p));
		List<Product> duplicateProducts = productNumbers.stream()
			.map(productNumber -> productMap.get(productNumber))
			.collect(Collectors.toList());
		return duplicateProducts;
	}
}
