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

    private final Operation operation1 = new Operation(new Expression(new BigDecimal("5.2"), Operator.ADD, new BigDecimal("3")), new BigDecimal("8.2"));
    private final Operation operation2 = new Operation(new Expression(new BigDecimal("10.3"), Operator.SUBTRACT, new BigDecimal("3.3")), new BigDecimal("7"));
    private  final Operation operation3 = new Operation(new Expression(new BigDecimal("4.1"), Operator.MULTIPLY, new BigDecimal("6")), new BigDecimal("24.6"));
    private final Operation operation4 = new Operation(new Expression(BigDecimal.ONE, Operator.DIVIDE, new BigDecimal("3")), new BigDecimal("0.33"));

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
        assertEquals(operation4, historyManager.getLast());
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
                () -> assertEquals(operation2, historyManager.getLast()),
                () -> assertEquals(operation1, historyManager.getHistoryOperations().getFirst()));
    }

    private void addTestOperationsToList() {
        historyManager.add(operation1);
        historyManager.add(operation2);
        historyManager.add(operation3);
        historyManager.add(operation4);
    }
}
