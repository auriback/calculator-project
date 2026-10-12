package com.auriback.calculator.models;

import java.util.Arrays;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public enum Operator {
    ADD("+"),
    SUBTRACT("-"),
    MULTIPLY("*"),
    DIVIDE("/");

    private final String symbol;
    public static final String ERROR_UNKNOWN_OPERATOR = "Ошибка: неизвестный оператор '%s'";

    private static final Map<String, Operator> SYMBOL_MAP = Arrays.stream(values())
            .collect(Collectors.toUnmodifiableMap(Operator::getSymbol, Function.identity()));

    Operator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Operator fromString(String symbol) {
        Operator operator = SYMBOL_MAP.get(symbol);

        if (operator == null) {
            throw new IllegalArgumentException(ERROR_UNKNOWN_OPERATOR.formatted(symbol));
        }

        return operator;
    }
}
