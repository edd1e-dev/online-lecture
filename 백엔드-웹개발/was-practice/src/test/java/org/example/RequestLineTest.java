package org.example;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RequestLineTest {
	@DisplayName("")
	@Test
	void create() {
	    // given
		RequestLine requestLine = new RequestLine("GET /calculate?operand1=11&operator=*&operand2=55 HTTP/1.1");
		assertThat(requestLine).isNotNull();
	    // when

	    // then
	}
}
