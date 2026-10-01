package com.auriback.calculator.util;

import com.auriback.calculator.models.Expression;
import com.auriback.calculator.models.Operator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InputParserTest {

    @ParameterizedTest(name = "Тест {index}: [{0}] результат: {1}, {2}, {3}")
    @CsvSource({
            "5 - 3, 5, -, 3",
            "5 / 2, 5, /, 2",
            "10 - 3, 10, -, 3",
            "4 * 6, 4, *, 6",
            "-10 / -3, -10, /, -3"
    })
    void whenValidFormatStringThenCorrectValues(String inputString,
                                                int expectedFirstNumber, String expectedOperator, int expectedSecondNumber) {
        Expression calc = InputParser.parseInput(inputString);

        assertAll("Проверка полей структуры Calculation",
                () -> assertEquals(expectedFirstNumber, calc.firstNumber()),
                () -> assertEquals(expectedSecondNumber, calc.secondNumber()),
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
            "'abc + 999999999999'; буквы и переполнение",
            "'999999999999 + abc'; переполнение и буквы"
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
            "'999999999999 $ abc'; переполнение и невалидный оператор и буквы; $",
            "'abc $ 555'; буквы и невалидный оператор; $",
            "'100000000000000 $ 2000000000000'; переполнение обоих чисел и невалидный оператор; $"

    }, delimiter = ';')
    void whenInvalidOperationStringThenThrowsException(String inputString, String caseDescription, String expectedBadOperator) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> InputParser.parseInput(inputString));

        assertEquals(Operator.ERROR_UNKNOWN_OPERATOR.formatted(expectedBadOperator), exception.getMessage());
    }

    @ParameterizedTest(name = "Тест {index}: [{0}]")
    @ValueSource(strings = {
            "2147483648 + 5",
            "-2147483649 - 1",
            "999999999999 * 2"
    })
    void whenNumberOverflowThenThrowsException(String inputString) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> InputParser.parseInput(inputString));

        assertEquals(InputParser.ERROR_NUMBER_OVERFLOW, exception.getMessage());
    }
}
