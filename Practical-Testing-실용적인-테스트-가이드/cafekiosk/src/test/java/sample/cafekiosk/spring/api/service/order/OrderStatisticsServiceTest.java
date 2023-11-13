package sample.cafekiosk.spring.api.service.order;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static sample.cafekiosk.spring.domain.product.ProductSellingStatus.*;
import static sample.cafekiosk.spring.domain.product.ProductType.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import sample.cafekiosk.spring.client.mail.MailSendClient;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistory;
import sample.cafekiosk.spring.domain.history.mail.MailSendHistoryRepository;
import sample.cafekiosk.spring.domain.order.Order;
import sample.cafekiosk.spring.domain.order.OrderRepository;
import sample.cafekiosk.spring.domain.order.OrderStatus;
import sample.cafekiosk.spring.domain.orderproduct.OrderProductRepository;
import sample.cafekiosk.spring.domain.product.Product;
import sample.cafekiosk.spring.domain.product.ProductRepository;
import sample.cafekiosk.spring.domain.product.ProductType;

@SpringBootTest
class OrderStatisticsServiceTest {

	@Autowired
	private OrderStatisticsService orderStatisticsService;

	@Autowired
	private OrderProductRepository orderProductRepository;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private MailSendHistoryRepository mailSendHistoryRepository;

	@MockBean
	private MailSendClient mailSendClient;

	@AfterEach
	void tearDown() {
		orderProductRepository.deleteAllInBatch();
		orderRepository.deleteAllInBatch();
		productRepository.deleteAllInBatch();
		mailSendHistoryRepository.deleteAllInBatch();
	}

	@DisplayName("결제완료 주문들을 조회하여 매출 통계 메일을 전송한다.")
	@Test
	void sendOrderStaticsMail() {
		// given
		LocalDateTime now = LocalDateTime.of(2023, 3, 5, 0, 0);

		Product product1 = createProduct("001", HANDMADE, 1000);
		Product product2 = createProduct("002", HANDMADE, 3000);
		Product product3 = createProduct("003", HANDMADE, 5000);
		productRepository.saveAll(List.of(product1, product2, product3));

		List<Product> products = List.of(product1, product2, product3);
		Order order1 = createPaymentCompletedOrder(products,
			LocalDateTime.of(2023, 3, 4, 23, 59, 59, 59));
		Order order2 = createPaymentCompletedOrder(products, now);
		Order order3 = createPaymentCompletedOrder(products,
			LocalDateTime.of(2023, 3, 5, 23, 59, 59, 59));
		Order order4 = createPaymentCompletedOrder(products,
			LocalDateTime.of(2023, 3, 6, 0, 0, 0, 0));

		// stubbing
		when(mailSendClient.sendEmail(any(String.class), anyString(), anyString(), anyString())).thenReturn(true);

		// when
		boolean result = orderStatisticsService.sendOrderStatisticsMail(
			LocalDate.of(2023, 3, 5),
			"test@test.com"
		);

		// then
		assertThat(result).isTrue();

		List<MailSendHistory> histories = mailSendHistoryRepository.findAll();
		assertThat(histories).hasSize(1)
			.extracting("fromEmail", "toEmail", "subject", "content")
			.contains(
				tuple(
					"no-reply@cafekiosk.com",
					"test@test.com",
					String.format("[매출통계] %s", now.toLocalDate()),
					"총 매출 합계는 18000원 입니다."
				)
			);
	}

	private Order createPaymentCompletedOrder(List<Product> products, LocalDateTime now) {
		Order order = Order.builder()
			.products(products)
			.orderStatus(OrderStatus.PAYMENT_COMPLETED)
			.registeredDateTime(now)
			.build();
		return orderRepository.save(order);
	}

	private Product createProduct(String productNumber, ProductType type, int price) {
		return Product.builder()
			.productNumber(productNumber)
			.type(type)
			.price(price)
			.sellingStatus(SELLING)
			.name("메뉴 이름")
			.build();
	}
}