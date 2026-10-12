package com.auriback.calculator;

import com.auriback.calculator.util.HistoryManager;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Application app = new Application(scanner, new HistoryManager());
            app.run();
        }
    }
}
