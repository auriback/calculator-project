package com.auriback.calculator;

import com.auriback.calculator.util.HistoryManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            bootstrapApplication(scanner).run();
        }
    }

    private static Application bootstrapApplication(Scanner scanner) {
        HistoryManager historyManager = new HistoryManager();
        return new Application(scanner, historyManager);
    }
}
