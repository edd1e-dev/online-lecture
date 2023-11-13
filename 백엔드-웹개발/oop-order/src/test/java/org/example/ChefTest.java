package org.example;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChefTest {
	@DisplayName("메뉴에 해당하는 음식을 만든다.")
	@Test
	void makeCookTest() {
	    // given
		Chef chef = new Chef();
		MenuItem menuItem = new MenuItem("돈까스", 5000);
	    // when
		Cook cook = chef.makeCook(menuItem);
	    // then
		assertThat(cook).isEqualTo(new Cook("돈까스", 5000));
	}
}
