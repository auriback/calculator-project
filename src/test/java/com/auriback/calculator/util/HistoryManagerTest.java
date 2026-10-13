package com.auriback.calculator.util;

import com.auriback.calculator.models.Expression;
import com.auriback.calculator.models.Operation;
import com.auriback.calculator.models.Operator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HistoryManagerTest {

    private HistoryManager historyManager;

    private final Operation operation1 = createOperation("5.2", Operator.ADD, "3", "8.2");
    private final Operation operation2 = createOperation("10.3", Operator.SUBTRACT, "3.3", "7");
    private final Operation operation3 = createOperation("4.1", Operator.MULTIPLY, "6", "24.6");
    private final Operation operation4 = createOperation("1", Operator.DIVIDE, "3", "0.33");

    @BeforeEach
    void setUp() {
        historyManager = new HistoryManager();
    }

    @Test
    void whenAddOperationsThenHistorySizeIncreases() {
        addTestOperationsToList();
        assertEquals(4, historyManager.size());
    }

    @Test
    void whenClearHistoryThenHistorySizeEqualsZero() {
        addTestOperationsToList();
        historyManager.clear();
        assertEquals(0, historyManager.size());
    }

    @Test
    void whenRequestLastOperationThenGetLastAdded() {
        addTestOperationsToList();
        assertEquals(operation4, historyManager.getLast().orElseThrow());
    }

    @Test
    void whenAddMoreThanMaxOperationsThenOldestAreRemoved() {
        historyManager.add(operation3);
        for (int i = 0; i < 9; i++) {
            historyManager.add(operation1);
        }
        historyManager.add(operation2);

        assertAll(
                () -> assertEquals(10, historyManager.size()),
                () -> assertEquals(operation2, historyManager.getLast().orElseThrow()),
                () -> assertEquals(operation1, historyManager.getHistoryOperations().getFirst()));
    }

    private void addTestOperationsToList() {
        historyManager.add(operation1);
        historyManager.add(operation2);
        historyManager.add(operation3);
        historyManager.add(operation4);
    }

    private static Operation createOperation(String firstNumber, Operator operator, String secondNumber, String expectedResult) {
        Expression expression = new Expression(new BigDecimal(firstNumber), operator, new BigDecimal(secondNumber));
        return new Operation(expression, new BigDecimal(expectedResult));
    }
}
