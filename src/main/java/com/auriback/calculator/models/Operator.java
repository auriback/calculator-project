package com.auriback.calculator.models;

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
        for (Operator op : values()) {
            if (op.symbol.equals(symbol)) {
                return op;
            }
        }
        throw new IllegalArgumentException(ERROR_UNKNOWN_OPERATOR.formatted(symbol));
    }
}
