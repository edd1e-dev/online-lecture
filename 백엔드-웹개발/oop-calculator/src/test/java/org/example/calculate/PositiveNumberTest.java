package org.example.calculate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PositiveNumberTest {

	@DisplayName("")
	@ParameterizedTest
	@ValueSource(ints = {0, -1})
	void createTest(int value) {
	    // given

	    // when

	    // then
		assertThatCode(() -> new PositiveNumber(value))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("0 또는 음수를 전달할 수 없습니다.");
	}
}