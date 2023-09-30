package me.whiteship.java8to11;

/**
 * FunctionalInterface
 */
@FunctionalInterface
public interface RunSomething {
	// 추상 메서드가 하나만 있으면 함수형 인터페이스
	void doIt(); // abstract 생략 가능
	// void doItAgain(); // 하나만 가져야해서 주석

	static void printName() {
		System.out.println("Keesun");
	}

	default void printAge() {
		System.out.println("40");
	}
}
