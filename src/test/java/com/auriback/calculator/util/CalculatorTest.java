package com.auriback.calculator.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {

    @ParameterizedTest(name = "Тест {index}: {0} {1} {2} = {3}")
    @CsvSource({
            "5, +, 3, 8, ADD",
            "10, -, 3, 7, SUBTRACT",
            "4, *, 6, 24, MULTIPLY",
            "-4, *, -6, 24, MULTIPLY",
            "5, /, 2, 2, DIVIDE",
            "-10, /, -3, 3, DIVIDE"
    })
    public void shouldCalculateCorrectly(int a, String operatorStr, int b, int expectedResult, Operator operator) {
        assertEquals(expectedResult, Calculator.calculate(a, b, operator));

    }

    @ParameterizedTest(name = "Тест {index}: Переполнение результата при операции [{0} {1} {2}]")
    @CsvSource({
            "2147483647, +, 1, ADD",
            "-2147483648, -, 1, SUBTRACT",
            "100000, *, 100000, MULTIPLY",
            "-2147483648, /, -1, DIVIDE"
    })
    public void whenOperationOverflowsThenThrowsException(int a, String operatorStr, int b, Operator operator) {
        ArithmeticException exception = assertThrows(ArithmeticException.class,
                () -> Calculator.calculate(a, b, operator));

        assertEquals(Calculator.ERROR_ARITHMETIC_OVERFLOW, exception.getMessage());
    }


    @Test
    public void whenDivisionByZeroThenThrowsException() {
        ArithmeticException exception = assertThrows(ArithmeticException.class,
                () -> Calculator.calculate(10, 0, Operator.DIVIDE));

        assertEquals(Calculator.ERROR_DIVISION_BY_ZERO, exception.getMessage());
    }
}
