package com.auriback.calculator.util;

public enum Operator {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/");

    private final String symbol;
    public static final String ERROR_UNKNOWN_OPERATOR = "Ошибка: неизвестный оператор '%s'";

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Operator fromString(String symbol) {
        return switch (symbol) {
            case "+" -> ADD;
            case "-" -> SUBTRACT;
            case "*" -> MULTIPLY;
            case "/" -> DIVIDE;
            default -> throw new IllegalArgumentException(ERROR_UNKNOWN_OPERATOR.formatted(symbol));
        };
    }
}
