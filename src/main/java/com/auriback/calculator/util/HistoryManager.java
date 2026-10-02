package com.auriback.calculator.util;

import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    private final List<String> historyOperations = new ArrayList<>(10);

    public void add(String operation) {
        if (historyOperations.size() == 10) {
            historyOperations.removeFirst();
        }
        historyOperations.add(operation);
    }

    public int size() {
        return historyOperations.size();
    }

    public void clear() {
        historyOperations.clear();
    }

    public String getLast() {
        return historyOperations.getLast();
    }
}
