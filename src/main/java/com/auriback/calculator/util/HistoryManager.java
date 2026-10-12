package com.auriback.calculator.util;

import java.util.ArrayList;
import java.util.List;

public class HistoryManager {
    private static final int MAX_HISTORY_SIZE = 10;

    private final List<String> historyOperations = new ArrayList<>(MAX_HISTORY_SIZE);

    public void add(String operation) {
        if (historyOperations.size() == MAX_HISTORY_SIZE) {
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
