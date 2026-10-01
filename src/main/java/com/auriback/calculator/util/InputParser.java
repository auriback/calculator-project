package com.auriback.calculator.util;

import com.auriback.calculator.models.Expression;
import com.auriback.calculator.models.Operator;

import java.math.BigInteger;

public class InputParser {
    public static final String ERROR_EMPTY_INPUT_STRING = "Строка не может быть пустой!";
    public static final String ERROR_INCORRECT_INPUT_FORMAT = "Ошибка: неверный формат. Используйте: число оператор число";
    public static final String ERROR_NUMBER_OVERFLOW = "Ошибка: введено слишком большое или слишком маленькое число!";

    private InputParser() {
    }

    public static Expression parseInput(String inputString) {
        if (isEmptyInputString(inputString)) {
            throw new IllegalArgumentException(ERROR_EMPTY_INPUT_STRING);
        }

        String[] parts = inputString.strip().split("\\s+");
        if (isNotCorrectInputParts(parts)) {
            throw new IllegalArgumentException(ERROR_INCORRECT_INPUT_FORMAT);
        }

        String aStr = parts[0];
        String operatorStr = parts[1];
        String bStr = parts[2];

        Operator operator = Operator.fromString(operatorStr);

        try {
            BigInteger bigA = new BigInteger(aStr);
            BigInteger bigB = new BigInteger(bStr);

            int a = bigA.intValueExact();
            int b = bigB.intValueExact();

            return new Expression(a, operator, b);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_INCORRECT_INPUT_FORMAT);
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException(ERROR_NUMBER_OVERFLOW);
        }
    }

    private static boolean isEmptyInputString(String inputString) {
        return inputString == null || inputString.isBlank();
    }

    private static boolean isNotCorrectInputParts(String[] parts) {
        return parts.length != 3;
    }
}
