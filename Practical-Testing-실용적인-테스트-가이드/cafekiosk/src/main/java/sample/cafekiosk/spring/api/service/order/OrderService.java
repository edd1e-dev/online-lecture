package sample.cafekiosk.spring.api.service.order;

import java.time.LocalDateTime;
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

	public OrderResponse createOrder(OrderCreateRequest request, LocalDateTime registeredDateTime) {
		List<String> productNumbers = request.getProductNumbers();
		List<Product> duplicateProducts = findProductsBy(productNumbers);

		// 재고 차감 체크가 필요한 상품들 filter
		List<String> stockProductNumbers = duplicateProducts.stream()
			.filter(product -> ProductType.containsStockType(product.getType()))
			.map(Product::getProductNumber)
			.collect(Collectors.toList());

		// 재고 엔티티 조회
		List<Stock> stocks = stockRepository.findAllByProductNumberIn(stockProductNumbers);

		// 상품별 counting
		Map<String, Long> productCountingMap = stockProductNumbers.stream()
			.collect(Collectors.groupingBy(p -> p, Collectors.counting()));

		// 재고 차감 시도
		for (String)

		Order order = Order.create(duplicateProducts, registeredDateTime);
		Order savedOrder = orderRepository.save(order);

		return OrderResponse.of(savedOrder);
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
