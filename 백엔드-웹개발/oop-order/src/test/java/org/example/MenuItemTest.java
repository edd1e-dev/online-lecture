package org.example;

import static org.assertj.core.api.Assertions.*;

import java.awt.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MenuItemTest {
	@DisplayName("메뉴 항목을 생성한다.")
	@Test
	void createTest() {
	    // given

	    // when

	    // then
		assertThatCode(() -> new MenuItem("만두", 5000))
			.doesNotThrowAnyException();
	}
}
