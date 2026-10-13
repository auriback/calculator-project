package com.auriback.calculator.util;

import com.auriback.calculator.models.Expression;
import com.auriback.calculator.models.Operator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculatorTest {

    @ParameterizedTest(name = "Тест {index}: [{0}] = {1}")
    @MethodSource("provideCorrectTestData")
    void shouldCalculateCorrectly(Expression expression, BigDecimal expectedResult) {
        BigDecimal actualResult = Calculator.calculate(expression);
        assertEquals(0, expectedResult.compareTo(actualResult),
                () -> String.format("Ожидалось: %s, но получено %s", expectedResult, actualResult));
    }

    @ParameterizedTest(name = "Тест {index}: Деление на ноль [{0}]")
    @MethodSource("provideDivideByZeroTestData")
    void whenDivisionByZeroThenThrowsException(Expression expression) {
        ArithmeticException exception = assertThrows(ArithmeticException.class,
                () -> Calculator.calculate(expression));
        assertEquals(Calculator.ERROR_DIVISION_BY_ZERO, exception.getMessage());
    }

    @ParameterizedTest(name = "Тест {index}: Валидация на NULL")
    @MethodSource("provideNullTestData")
    void whenProvideNullThenThrowsException(Runnable action, String expectedMessage) {
        NullPointerException exception = assertThrows(NullPointerException.class, action::run);
        assertEquals(expectedMessage, exception.getMessage());
    }

    private static Stream<Arguments> provideCorrectTestData() {
        return Stream.of(
                Arguments.of(getExpressionFromTestData("5.2", Operator.ADD, "3"), new BigDecimal("8.2")),
                Arguments.of(getExpressionFromTestData("10.3", Operator.SUBTRACT, "3.3"), new BigDecimal("7")),
                Arguments.of(getExpressionFromTestData("4.1", Operator.MULTIPLY, "6"), new BigDecimal("24.6")),
                Arguments.of(getExpressionFromTestData("-4", Operator.MULTIPLY, "-6"), new BigDecimal("24")),
                Arguments.of(getExpressionFromTestData("5.2", Operator.DIVIDE, "2"), new BigDecimal("2.6")),
                Arguments.of(getExpressionFromTestData("-10.4", Operator.DIVIDE, "-3.2"), new BigDecimal("3.2")),
                Arguments.of(getExpressionFromTestData("1", Operator.DIVIDE, "3"), new BigDecimal("0.3"))
        );
    }

    private static Stream<Expression> provideDivideByZeroTestData() {
        return Stream.of(
                getExpressionFromTestData("10", Operator.DIVIDE, "0"),
                getExpressionFromTestData("2", Operator.DIVIDE, "0.0000")
        );
    }

    private static Stream<Arguments> provideNullTestData() {
        return Stream.of(
                Arguments.of((Runnable) () -> Calculator.calculate(null), Calculator.ERROR_INCORRECT_VALUE),
                Arguments.of((Runnable) () -> new Expression(null, Operator.ADD, BigDecimal.ONE), Expression.ERROR_INCORRECT_VALUE),
                Arguments.of((Runnable) () -> new Expression(BigDecimal.ONE, null, BigDecimal.ONE), Expression.ERROR_INCORRECT_VALUE),
                Arguments.of((Runnable) () -> new Expression(BigDecimal.ONE, Operator.ADD, null), Expression.ERROR_INCORRECT_VALUE)
        );
    }

    private static Expression getExpressionFromTestData(String firstNumber, Operator operator, String secondNumber) {
        return new Expression(new BigDecimal(firstNumber), operator, new BigDecimal(secondNumber));
    }
}
