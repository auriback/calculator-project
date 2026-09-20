package com.auriback.calculator;

import com.auriback.calculator.calculator.Calculator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorTest {
    @Test
    public void when5Add3Then8() {
        assertEquals(8, Calculator.calculate(5, 3, "+"));
    }

    @Test
    public void when5Divide2Then2() {
        assertEquals(2, Calculator.calculate(5, 2, "/"));
    }

    @Test
    public void when10Minus3Then7() {
        assertEquals(7, Calculator.calculate(10, 3, "-"));
    }

    @Test
    public void when4Multiply6Then24() {
        assertEquals(24, Calculator.calculate(4, 6, "*"));
    }

    @Test
    public void divisionByZeroThrowsException() {
        assertThrows(ArithmeticException.class,
                () -> Calculator.calculate(10, 0, "/"));
    }
}
