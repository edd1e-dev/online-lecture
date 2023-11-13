package sample.cafekiosk.spring.domain.product;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

class ProductTypeTest {
	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
	@Test
	void containsStockType() {
	    // given
		ProductType givenType = ProductType.HANDMADE;

	    // when
		boolean result = ProductType.containsStockType(givenType);

	    // then
		assertThat(result).isFalse();
	}

	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
	@Test
	void contatinsStockType2() {
		// given
		ProductType givenType1 = ProductType.BOTTLE;
		ProductType givenType2 = ProductType.BAKERY;

		// when
		boolean result1 = ProductType.containsStockType(givenType1);
		boolean result2 = ProductType.containsStockType(givenType2);

		// then
		assertThat(result1).isTrue();
		assertThat(result2).isTrue();
	}

	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
	@CsvSource({"HANDMADE,false","BOTTLE,true","BAKERY,true"})
	@ParameterizedTest
	void contatinsStockType3(ProductType productType, boolean expected) {
		// given

		// when
		boolean result = ProductType.containsStockType(productType);

		// then
		assertThat(result).isEqualTo(expected);
	}

	private static Stream<Arguments> provideProductTypesForCheckingStockType() {
		return Stream.of(
			Arguments.of(ProductType.HANDMADE, false),
			Arguments.of(ProductType.BOTTLE, true),
			Arguments.of(ProductType.BAKERY, true)
		);
	}

	@DisplayName("상품 타입이 재고 관련 타입인지를 체크한다.")
	@MethodSource("provideProductTypesForCheckingStockType")
	@ParameterizedTest
	void containsStockType4(ProductType productType, boolean expected) {
		// when
		boolean result = ProductType.containsStockType(productType);

		// then
		assertThat(result).isEqualTo(expected);
	}

}