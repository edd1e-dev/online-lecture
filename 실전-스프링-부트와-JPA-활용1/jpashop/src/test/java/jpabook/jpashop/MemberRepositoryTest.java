package jpabook.jpashop;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Test
	void testMember() {
	    // given
		Member member = new Member();
		member.setUsername("memberA");

	    // when
		Long savedId = memberRepository.save(member);

	    // then
		Member findMember = memberRepository.find(savedId);
		assertThat(member.getId()).isEqualTo(findMember.getId());
		assertThat(member.getUsername()).isEqualTo(findMember.getUsername());
	}
}