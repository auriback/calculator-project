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
        checkEmpty(inputString);
        String[] stringParts = splitString(inputString);
        checkLengthPartsOfInputString(stringParts);
        Operator operator = Operator.fromString(stringParts[1]);
        BigInteger bigFirstNumber = getBigIntegerFromString(stringParts[0]);
        BigInteger bigSecondNumber = getBigIntegerFromString(stringParts[2]);
        int firstNumber = getNumberFromBigInteger(bigFirstNumber);
        int secondNumber = getNumberFromBigInteger(bigSecondNumber);

        return new Expression(firstNumber, operator, secondNumber);
    }

    private static void checkEmpty(String inputString) {
        if (isEmptyInputString(inputString)) {
            throw new IllegalArgumentException(ERROR_EMPTY_INPUT_STRING);
        }
    }

    private static boolean isEmptyInputString(String inputString) {
        return inputString == null || inputString.isBlank();
    }

    private static String[] splitString(String inputString) {
        return inputString.strip().split(SPLIT_REGEX);
    }

    private static void checkLengthPartsOfInputString(String[] stringParts) {
        if (isNotCorrectInputParts(stringParts)) {
            throw new IllegalArgumentException(ERROR_INCORRECT_INPUT_FORMAT);
        }
    }

    private static boolean isNotCorrectInputParts(String[] parts) {
        return parts.length != 3;
    }

    private static BigInteger getBigIntegerFromString(String numberStr) {
        try {
            return new BigInteger(numberStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ERROR_INCORRECT_INPUT_FORMAT);
        }
    }

    private static int getNumberFromBigInteger(BigInteger bigInteger) {
        try {
            return bigInteger.intValueExact();
        } catch (ArithmeticException e) {
            throw new IllegalArgumentException(ERROR_NUMBER_OVERFLOW);
        }
    }
}
