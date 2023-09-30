# 더 자바, Java 8

## 함수형 인터페이스와 람다 표현식 소개

### 함수형 인터페이스의 정의

```
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
```

### 람다 표현식

```
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
```

자바에서는 람다 Expression으로 쓰면 마치 다른 언어의 함수를 정의한 것 처럼 보이지만  
실질적으로는 자바에서는 특수한 형태의 오브젝트라고 볼 수 있다.

함수형 인터페이스 (Functional Interface)
- 추상 메소드를 딱 하나만 가지고 있는 인터페이스
- SAM (Single Abstract Method) 인터페이스
- @FunctionalInterface 애노테이션을 가지고 있는 인터페이스

람다 표현식 (Lambda Expressions)
- 함수형 인터페이스의 인스턴스를 만드는 방법으로 쓰일 수 있다.
- 코드를 줄일 수 있다.
- 메소드 매개변수, 리턴 타입, 변수로 만들어 사용할 수도 있다.

자바에서 함수형 프로그래밍
- 함수를 First class object(함수가 함수를 매개변수 받는 것, 변수로 지정하는 것 등)으로 사용할 수 있다.
- 순수 함수 (Pure function)
	- 사이드 이펙트를 만들 수 없다. (함수 밖에 있는 값을 변경하지 못한다.)
	- 상태가 없다. (함수 밖에 정의되어 있는)
- 고차 함수 (High-Order Function)
	- 함수가 함수를 매개변수로 받을 수 있고 함수를 리턴할 수도 있다.
- 불변성

## 자바에서 제공하는 함수형 인터페이스



## 람다 표현식

## 메소드 레퍼런스