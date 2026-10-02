package com.auriback.calculator.util;

import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    private final List<String> historyOperations = new ArrayList<>(10);

    public void add(String operation) {
        historyOperations.add(operation);
    }

    public List<String> getOperations() {
        return historyOperations;
    }

    public void clear() {
        historyOperations.clear();
    }

    public String getLast() {
        return historyOperations.getLast();
    }
}
