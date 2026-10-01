package com.auriback.calculator.util;

import com.auriback.calculator.models.Expression;
import com.auriback.calculator.models.Operator;

import java.math.BigInteger;

public class InputParser {
    public static final String ERROR_EMPTY_INPUT_STRING = "Строка не может быть пустой!";
    public static final String ERROR_INCORRECT_INPUT_FORMAT = "Ошибка: неверный формат. Используйте: число оператор число";
    public static final String ERROR_NUMBER_OVERFLOW = "Ошибка: введено слишком большое или слишком маленькое число!";
    public static final String SPLIT_REGEX = "\\s+";

    private InputParser() {
    }

    public static Expression parseInput(String inputString) {
        if (isEmptyInputString(inputString)) {
            throw new IllegalArgumentException(ERROR_EMPTY_INPUT_STRING);
        }

        String[] parts = inputString.strip().split(SPLIT_REGEX);
        if (isNotCorrectInputParts(parts)) {
            throw new IllegalArgumentException(ERROR_INCORRECT_INPUT_FORMAT);
        }

        String firstNumberStr = parts[0];
        String operatorStr = parts[1];
        String secondNumberStr = parts[2];

        Operator operator = Operator.fromString(operatorStr);

        try {
            BigInteger bigFirstNumber = new BigInteger(firstNumberStr);
            BigInteger bigSecondNumber = new BigInteger(secondNumberStr);

            int firstNumber = bigFirstNumber.intValueExact();
            int secondNumber = bigSecondNumber.intValueExact();

            return new Expression(firstNumber, operator, secondNumber);
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
