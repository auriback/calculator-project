package com.auriback.calculator.util;

import com.auriback.calculator.models.Expression;

public class Calculator {
    public static final String ERROR_DIVISION_BY_ZERO = "Ошибка: деление на ноль";
    public static final String ERROR_ARITHMETIC_OVERFLOW = "Ошибка: результат операции вышел за пределы допустимых значений!";

    private Calculator() {
    }

    public static int calculate(Expression expression) {
        int firstNumber = expression.firstNumber();
        int secondNumber = expression.secondNumber();

        return switch (expression.operator()) {
            case ADD -> add(firstNumber, secondNumber);
            case SUBTRACT -> subtract(firstNumber, secondNumber);
            case MULTIPLY -> multiply(firstNumber, secondNumber);
            case DIVIDE -> divide(firstNumber, secondNumber);
        };
    }

    private static int add(int firstNumber, int secondNumber) {
        try {
            return Math.addExact(firstNumber, secondNumber);
        } catch (ArithmeticException e) {
            throw new ArithmeticException(ERROR_ARITHMETIC_OVERFLOW);
        }
    }

    private static int subtract(int firstNumber, int secondNumber) {
        try {
            return Math.subtractExact(firstNumber, secondNumber);
        } catch (ArithmeticException e) {
            throw new ArithmeticException(ERROR_ARITHMETIC_OVERFLOW);
        }
    }

    private static int multiply(int firstNumber, int secondNumber) {
        try {
            return Math.multiplyExact(firstNumber, secondNumber);
        } catch (ArithmeticException e) {
            throw new ArithmeticException(ERROR_ARITHMETIC_OVERFLOW);
        }
    }

    private static int divide(int firstNumber, int secondNumber) {
        if (secondNumber == 0) {
            throw new ArithmeticException(ERROR_DIVISION_BY_ZERO);
        }
        try {
            return Math.divideExact(firstNumber, secondNumber);
        } catch (ArithmeticException e) {
            throw new ArithmeticException(ERROR_ARITHMETIC_OVERFLOW);
        }
    }
}
