package com.auriback.calculator.models;

import java.math.BigDecimal;
import java.util.Objects;

public record Expression(BigDecimal firstNumber, Operator operator, BigDecimal secondNumber) {
    public static final String ERROR_INCORRECT_VALUE = "Ошибка: Значение числа или оператора не может быть NULL";

    public Expression(BigDecimal firstNumber, Operator operator, BigDecimal secondNumber) {
        Objects.requireNonNull(firstNumber, ERROR_INCORRECT_VALUE);
        Objects.requireNonNull(secondNumber, ERROR_INCORRECT_VALUE);
        Objects.requireNonNull(operator, ERROR_INCORRECT_VALUE);

        this.firstNumber = firstNumber.stripTrailingZeros();
        this.operator = operator;
        this.secondNumber = secondNumber.stripTrailingZeros();
    }

    @Override
    public String toString() {
        String firstNumberStr = firstNumber.toPlainString();
        String secondNumberStr = secondNumber.toPlainString();

        String secondOperand = secondNumber.compareTo(BigDecimal.ZERO) < 0
                ? "(" + secondNumberStr + ")"
                : secondNumberStr;
        
        return firstNumberStr + " " + operator.getSymbol() + " " + secondOperand;
    }
}
