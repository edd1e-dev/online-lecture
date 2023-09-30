package me.whiteship.java8to11;

public class Foo {
	public static void main(String[] args) {
		RunSomething runSomething = new RunSomething() { // 자바 8 이전에는 익명 내부 클래스 사용
			@Override
			public void doIt() {
				System.out.println("Hello");
			}
		};

		// 자바 8 이후에는 람다식 사용
		RunSomething runSomething1 = () -> System.out.println("Hello");
	}
}
