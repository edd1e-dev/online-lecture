package org.example.calculate;

import java.util.Arrays;

public enum ArithmeticOperator {
	ADD("+") {
		@Override
		public int arithmeticCalculate(int operand1, int operand2) {
			return operand1 + operand2;
		}
	},
	SUBTRACT("-") {
		@Override
		public int arithmeticCalculate(int operand1, int operand2) {
			return operand1 - operand2;
		}
	},
	MULTIPLY("*") {
		@Override
		public int arithmeticCalculate(int operand1, int operand2) {
			return operand1 * operand2;
		}
	},
	DIVIDE("/") {
		@Override
		public int arithmeticCalculate(int operand1, int operand2) {
			return operand1 / operand2;
		}
	};

	private final String operator;

	ArithmeticOperator(String operator) {
		this.operator = operator;
	}

	public static int calculate(int operand1, String operator, int operand2) {
		return Arrays.stream(values())
			.filter(v -> v.operator.equals(operator))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("올바른 사칙연산이 아닙니다."))
			.arithmeticCalculate(operand1, operand2);
	}

	protected abstract int arithmeticCalculate(final int operand1, final int operand2);
}
