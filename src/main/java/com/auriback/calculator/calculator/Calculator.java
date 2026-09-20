package com.auriback.calculator.calculator;

public class Calculator {

    private Calculator() {}

    public static int calculate(int a, int b, String operator) {
        if (operator.equals("/") && b == 0) {
            throw new ArithmeticException("Ошибка: деление на ноль");
        }
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> 0;
        };
    }
}
