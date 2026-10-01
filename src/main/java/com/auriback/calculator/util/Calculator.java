package com.auriback.calculator.util;

import com.auriback.calculator.models.Expression;
import com.auriback.calculator.models.Operator;

public class Calculator {
    public static final String ERROR_DIVISION_BY_ZERO = "Ошибка: деление на ноль";
    public static final String ERROR_ARITHMETIC_OVERFLOW = "Ошибка: результат операции вышел за пределы допустимых значений!";

    private Calculator() {
    }

    public static int calculate(Expression expression) {
        int a = expression.a();
        int b = expression.b();
        Operator operator = expression.operator();

        if (operator == Operator.DIVIDE && b == 0) {
            throw new ArithmeticException(ERROR_DIVISION_BY_ZERO);
        }

        try {
            return switch (operator) {
                case ADD -> Math.addExact(a, b);
                case SUBTRACT -> Math.subtractExact(a, b);
                case MULTIPLY -> Math.multiplyExact(a, b);
                case DIVIDE -> Math.divideExact(a, b);
            };
        } catch (ArithmeticException e) {
            throw new ArithmeticException(ERROR_ARITHMETIC_OVERFLOW);
        }
    }
}
