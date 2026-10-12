package com.auriback.calculator.util;

import com.auriback.calculator.models.Expression;
import com.auriback.calculator.models.Operator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputParserTest {

    @ParameterizedTest(name = "Тест {index}: [{0}] результат: {1}, {2}, {3}")
    @CsvSource({
            "5.5 - 3.25, 5.5, -, 3.25",
            "5 / 2, 5, /, 2",
            "10.000 - 3, 10, -, 3",
            "0.0001 * 6.0, 0.0001, *, 6",
            "4 * 6, 4, *, 6",
            "-10 / -3, -10, /, -3",
            "-10.4 / -3.2, -10.4, /, -3.2",
            "999999999999.99 + 1, 999999999999.99, +, 1"
    })
    void whenValidFormatStringThenCorrectValues(String inputString,
                                                BigDecimal expectedFirstNumber, String expectedOperator, BigDecimal expectedSecondNumber) {
        Expression calc = InputParser.parseInput(inputString);

        assertAll("Проверка полей структуры Calculation",
                () -> assertEquals(0, expectedFirstNumber.compareTo(calc.firstNumber())),
                () -> assertEquals(0, expectedSecondNumber.compareTo(calc.secondNumber())),
                () -> assertEquals(expectedOperator, calc.operator().getSymbol())
        );
    }

    @ParameterizedTest(name = "Тест {index} (пустая строка или null): [{0}]")
    @NullSource
    @ValueSource(strings = {
            "     ",
            ""
    })
    void whenNullOrEmptyStringThenThrowsException(String inputString) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> InputParser.parseInput(inputString));

        assertEquals(InputParser.ERROR_EMPTY_INPUT_STRING, exception.getMessage());
    }

    @ParameterizedTest(name = "Тест {index} ({1}): [{0}] ")
    @CsvSource(value = {
            "'2  35'; нет оператора",
            "'2    '; нет оператора и операнда",
            "'() + )))'; мусорные символы",
            "'abc + 3'; буквы вместо первого числа",
            "'5 + xyz'; буквы вместо второго числа",
            "'abc + 999999999999'; буквы на месте первого числа",
            "'999999999999 + abc'; буквы на месте второго числа"
    }, delimiter = ';')
    void whenInvalidFormatStringThenThrowsException(String inputString, String caseDescription) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> InputParser.parseInput(inputString));

        assertEquals(InputParser.ERROR_INCORRECT_INPUT_FORMAT, exception.getMessage());
    }

    @ParameterizedTest(name = "Тест {index} ({1} {2}): [{0}] ")
    @CsvSource(value = {
            "'2 4 5'; невалидный оператор; 4",
            "'5 — 3'; длинное тире (em-dash); —",
            "'5 – 3'; среднее тире (en-dash); –",
            "'999999999999 $ abc'; большое число, невалидный оператор и буквы; $",
            "'abc $ 555'; буквы и невалидный оператор; $",
            "'100000000000000 $ 2000000000000'; большие числа и невалидный оператор; $"

    }, delimiter = ';')
    void whenInvalidOperationStringThenThrowsException(String inputString, String caseDescription, String expectedBadOperator) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> InputParser.parseInput(inputString));

        assertEquals(Operator.ERROR_UNKNOWN_OPERATOR.formatted(expectedBadOperator), exception.getMessage());
    }
}
