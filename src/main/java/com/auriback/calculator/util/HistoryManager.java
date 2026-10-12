package com.auriback.calculator.util;

import com.auriback.calculator.models.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HistoryManager {
    private static final int MAX_HISTORY_SIZE = 10;

    private final List<Operation> historyOperations = new ArrayList<>(MAX_HISTORY_SIZE);

    public void add(Operation operation) {
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

    public Optional<Operation> getLast() {
        if (historyOperations.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(historyOperations.getLast());
    }

    public List<Operation> getHistoryOperations() {
        return List.copyOf(historyOperations);
    }
}
