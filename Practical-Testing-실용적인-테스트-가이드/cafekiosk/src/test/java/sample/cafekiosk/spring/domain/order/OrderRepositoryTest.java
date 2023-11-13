package sample.cafekiosk.spring.domain.order;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static sample.cafekiosk.spring.domain.order.OrderStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductSellingStatus;
import sample.cafekiosk.spring.domain.product.ProductType;

@DataJpaTest
@ActiveProfiles("test")
class OrderRepositoryTest {
	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private OrderRepository orderRepository;

	@DisplayName("결제완료 상태의 주문을 생성한 뒤 주문상태와 기간에 해당하는 주문들을 조회한다.")
	@Test
	void findOrdersBy() {
	    // given
		Product product1 = createProduct("001", HANDMADE, SELLING, "아메리카노", 4000);
		Product product2 = createProduct("002", HANDMADE, HOLD, "카페라떼", 4000);
		Product product3 = createProduct("003", HANDMADE, STOP_SELLING, "팥빙수", 7000);
		List<Product> products = List.of(product1, product2, product3);
		
		productRepository.saveAll(products);

		LocalDateTime now = LocalDateTime.now();
		
		Order order1 = createOrder(now, PAYMENT_COMPLETED, products);
		Order order2 = createOrder(now, INIT, products);
		Order order3 = createOrder(now.plusDays(1), PAYMENT_COMPLETED, products);

		orderRepository.saveAll(List.of(order1, order2, order3));
		
		// when
		List<Order> orders = orderRepository.findOrdersBy(
			now.toLocalDate().atStartOfDay(),
			now.toLocalDate().plusDays(1).atStartOfDay(),
			PAYMENT_COMPLETED
		);

		// then
		
		assertThat(orders).hasSize(1)
			.extracting("orderStatus", "totalPrice", "registeredDateTime")
			.contains(tuple(PAYMENT_COMPLETED, 15000, now));
	}

	private static Order createOrder(LocalDateTime localDateTime, OrderStatus orderStatus, List<Product> products) {
		return Order.builder()
			.registeredDateTime(localDateTime)
			.orderStatus(orderStatus)
			.products(products)
			.build();
	}

	private Product createProduct(String productNumber, ProductType type, ProductSellingStatus status,
		String name, int price) {
		return Product.builder()
			.productNumber(productNumber)
			.type(HANDMADE)
			.sellingStatus(status)
			.name(name)
			.price(price)
			.build();
	}
}