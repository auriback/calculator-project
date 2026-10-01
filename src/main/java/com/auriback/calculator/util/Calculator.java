package com.auriback.calculator.util;

import com.auriback.calculator.models.Expression;
import com.auriback.calculator.models.Operator;

public class Calculator {
    public static final String ERROR_DIVISION_BY_ZERO = "Ошибка: деление на ноль";
    public static final String ERROR_ARITHMETIC_OVERFLOW = "Ошибка: результат операции вышел за пределы допустимых значений!";

    private Calculator() {
    }

    public static int calculate(Expression expression) {
        int firstNumber = expression.firstNumber();
        int secondNumber = expression.secondNumber();
        Operator operator = expression.operator();

        if (operator == Operator.DIVIDE && secondNumber == 0) {
            throw new ArithmeticException(ERROR_DIVISION_BY_ZERO);
        }

        try {
            return switch (operator) {
                case ADD -> Math.addExact(firstNumber, secondNumber);
                case SUBTRACT -> Math.subtractExact(firstNumber, secondNumber);
                case MULTIPLY -> Math.multiplyExact(firstNumber, secondNumber);
                case DIVIDE -> Math.divideExact(firstNumber, secondNumber);
            };
        } catch (ArithmeticException e) {
            throw new ArithmeticException(ERROR_ARITHMETIC_OVERFLOW);
        }
    }
}
