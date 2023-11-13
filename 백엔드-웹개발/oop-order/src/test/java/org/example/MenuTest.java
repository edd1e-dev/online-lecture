package org.example;

import static org.assertj.core.api.Assertions.*;

import java.awt.*;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class MenuTest {
	@DisplayName("메뉴판에서 메뉴 이름에 해당하는 메뉴를 반환한다.")
	@Test
	void chooseTest() {
	    // given
		Menu menu = new Menu(List.of(new MenuItem("돈까스", 5000),
			new MenuItem("냉면", 7000)));

	    // when
		MenuItem menuItem = menu.choose("돈까스");

		// then
		assertThat(menuItem).isEqualTo(new MenuItem("돈까스", 5000));
	}

	@DisplayName("메뉴판에 없는 주문을 주문할 시 예외를 반환한다.")
	@Test
	void chooseTest2() {
		// given
		Menu menu = new Menu(List.of(new MenuItem("돈까스", 5000),
			new MenuItem("냉면", 7000)));

		// when

		// then
		assertThatCode(() -> menu.choose("통닭"))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("잘못된 메뉴 이름입니다.");
	}
}
