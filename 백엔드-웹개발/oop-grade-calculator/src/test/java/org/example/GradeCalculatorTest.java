package org.example;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 요구사항
 * 평균학점 계산 방법 = (학점수 * 교과목 평점)의 합계 / 수강신청 총학점 수
 * 일급 컬렉션 사용
 */
public class GradeCalculatorTest {
	// 학점 계산기 도메인: 이수한 과목, 학점 계산기

	@DisplayName("평균 학점을 계산한다.")
	@Test
	void calculateGradeTest() {
	    // given
		List<Course> courses = List.of(new Course("OOP", 3, "A+"),
			new Course("자료구조", 3, "A+"));

		GradeCalculator gradeCalculator = new GradeCalculator(courses);

	    // when
		double gradeResult = gradeCalculator.calculateGrade();

	    // then
		assertThat(gradeResult).isEqualTo(4.5);
	}
}
