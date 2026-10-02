package com.auriback.calculator.util;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HistoryManagerTest {

    @ParameterizedTest(name = "Тест {index}: [{0}]")
    @ValueSource(strings = {
            "5.0 + 3.0 = 8.0"
    })
    void whenCalculateThenHistorySizeAddOneOperation(String operation) {
        HistoryManager historyManager = new HistoryManager();
        historyManager.add(operation);
        historyManager.add(operation);
        int historySize = historyManager.getOperations().size();
        assertEquals(2, historySize);
    }

    @Test
    void whenClearHistoryThenHistorySizeEquals0() {
        HistoryManager historyManager = new HistoryManager();
        historyManager.add("5.0 / 2.0 = 2.5");
        historyManager.add("3.0 + 4.0 = 7.0");
        historyManager.add("5.0 + 3.0 = 8.0");
        historyManager.clear();
        assertEquals(0, historyManager.getOperations().size());
    }

    @Test
    void whenRequestLastOperationThenGetLastOperation() {
        HistoryManager historyManager = new HistoryManager();
        String firstOperation = "5.0 / 2.0 = 2.5";
        String lastOperation = "3.0 + 4.0 = 7.0";
        historyManager.add(firstOperation);
        historyManager.add(lastOperation);
        assertEquals(lastOperation, historyManager.getLast());
    }
}
