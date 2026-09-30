package com.auriback.calculator.util;

public class Calculator {
    public static final String ERROR_DIVISION_BY_ZERO = "Ошибка: деление на ноль";
    public static final String ERROR_ARITHMETIC_OVERFLOW = "Ошибка: результат операции вышел за пределы допустимых значений!";

    private Calculator() {
    }

    public static int calculate(int a, int b, Operator operator) {
        try {
            return switch (operator) {
                case ADD -> Math.addExact(a, b);
                case SUBTRACT -> Math.subtractExact(a, b);
                case MULTIPLY -> Math.multiplyExact(a, b);
                case DIVIDE -> {
                    if (b == 0) {
                        throw new ArithmeticException(ERROR_DIVISION_BY_ZERO);
                    }
                    yield Math.divideExact(a, b);
                }
            };
        } catch (ArithmeticException e) {
            if (ERROR_DIVISION_BY_ZERO.equals(e.getMessage())) {
                throw e;
            }
            throw new ArithmeticException(ERROR_ARITHMETIC_OVERFLOW);
        }
    }
}
