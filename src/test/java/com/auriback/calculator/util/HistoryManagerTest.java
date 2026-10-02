package com.auriback.calculator.util;

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
}
