package org.example;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CookTest {
	@DisplayName("요리를 생성한다.")
	@Test
	void createTest() {
	    // given

	    // when

	    // then
		assertThatCode(() -> new Cook("만두", 5000))
			.doesNotThrowAnyException();
	}
}
