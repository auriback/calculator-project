package com.auriback.calculator.models;

public record Expression(int a, Operator operator, int b) {
    @Override
    public String toString() {
        String secondOperand = b < 0 ? "(" + b + ")" : String.valueOf(b);
        return a + " " + operator.getSymbol() + " " + secondOperand;
    }
}
