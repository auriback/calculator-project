package com.auriback.calculator.models;

public record Expression(int firstNumber, Operator operator, int secondNumber) {
    @Override
    public String toString() {
        String secondOperand = secondNumber < 0 ? "(" + secondNumber + ")" : String.valueOf(secondNumber);
        return firstNumber + " " + operator.getSymbol() + " " + secondOperand;
    }
}
