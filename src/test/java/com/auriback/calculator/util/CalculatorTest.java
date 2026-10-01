package com.auriback.calculator.util;

import com.auriback.calculator.models.Expression;
import com.auriback.calculator.models.Operator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {

    @ParameterizedTest(name = "Тест {index}: [{0}] = {1}")
    @MethodSource("provideCorrectTestData")
    void shouldCalculateCorrectly(Expression expression, int expectedResult) {
        assertEquals(expectedResult, Calculator.calculate(expression));
    }

    @ParameterizedTest(name = "Тест {index}: Переполнение результата при операции [{0}]")
    @MethodSource("provideOverflowTestData")
    void whenOperationOverflowsThenThrowsException(Expression expression) {
        ArithmeticException exception = assertThrows(ArithmeticException.class,
                () -> Calculator.calculate(expression));
        assertEquals(Calculator.ERROR_ARITHMETIC_OVERFLOW, exception.getMessage());
    }

    @ParameterizedTest(name = "Тест {index}: Деление на ноль [{0}]")
    @MethodSource("provideDivideByZeroTestData")
    void whenDivisionByZeroThenThrowsException(Expression expression) {
        ArithmeticException exception = assertThrows(ArithmeticException.class,
                () -> Calculator.calculate(expression));
        assertEquals(Calculator.ERROR_DIVISION_BY_ZERO, exception.getMessage());
    }

    private static Stream<Arguments> provideCorrectTestData() {
        return Stream.of(
                Arguments.of(new Expression(5, Operator.ADD, 3), 8),
                Arguments.of(new Expression(10, Operator.SUBTRACT, 3), 7),
                Arguments.of(new Expression(4, Operator.MULTIPLY, 6), 24),
                Arguments.of(new Expression(-4, Operator.MULTIPLY, -6), 24),
                Arguments.of(new Expression(5, Operator.DIVIDE, 2), 2),
                Arguments.of(new Expression(-10, Operator.DIVIDE, -3), 3)

        );
    }

    private static Stream<Expression> provideOverflowTestData() {
        return Stream.of(
                new Expression(Integer.MAX_VALUE, Operator.ADD, 1),
                new Expression(Integer.MIN_VALUE, Operator.SUBTRACT, 1),
                new Expression(Integer.MAX_VALUE / 2, Operator.MULTIPLY, 3),
                new Expression(Integer.MIN_VALUE / 2, Operator.MULTIPLY, 3),
                new Expression(Integer.MIN_VALUE, Operator.DIVIDE, -1)
        );
    }

    private static Stream<Expression> provideDivideByZeroTestData() {
        return Stream.of(
                new Expression(10, Operator.DIVIDE, 0)
        );
    }
}
