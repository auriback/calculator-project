package com.auriback.calculator.models;

import java.math.BigDecimal;

public record Operation(Expression expression, BigDecimal result) {
    @Override
    public String toString() {
        return expression + " = " + result.toPlainString();
    }
}
