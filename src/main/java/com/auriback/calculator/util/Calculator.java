package com.auriback.calculator.util;

import com.auriback.calculator.models.Expression;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Calculator {
    public static final String ERROR_DIVISION_BY_ZERO = "Ошибка: деление на ноль";
    public static final String ERROR_INCORRECT_VALUE = "Ошибка: Значение выражения не может быть NULL";
    private static final int NUMBER_SCALE = 2;
    private static final RoundingMode NUMBER_ROUNDING_MODE = RoundingMode.HALF_EVEN;

    private Calculator() {
    }

    public static BigDecimal calculate(Expression expression) {
        Objects.requireNonNull(expression, ERROR_INCORRECT_VALUE);

        BigDecimal firstNumber = expression.firstNumber();
        BigDecimal secondNumber = expression.secondNumber();

        BigDecimal result = switch (expression.operator()) {
            case ADD -> firstNumber.add(secondNumber);
            case SUBTRACT -> firstNumber.subtract(secondNumber);
            case MULTIPLY -> firstNumber.multiply(secondNumber);
            case DIVIDE -> divide(firstNumber, secondNumber);
        };

        return result.setScale(NUMBER_SCALE, NUMBER_ROUNDING_MODE);
    }

    private static BigDecimal divide(BigDecimal firstNumber, BigDecimal secondNumber) {
        if (secondNumber.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException(ERROR_DIVISION_BY_ZERO);
        }
        return firstNumber.divide(secondNumber, NUMBER_SCALE, NUMBER_ROUNDING_MODE);
    }
}
