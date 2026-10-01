package com.auriback.calculator;

import com.auriback.calculator.models.Expression;
import com.auriback.calculator.util.Calculator;
import com.auriback.calculator.util.InputParser;

import java.util.Scanner;

public class Application {

    public void run() {
        try(Scanner scanner = new Scanner(System.in)) {
            boolean isRunning = true;

            while (isRunning) {
                try {
                    System.out.print("> ");
                    String input = scanner.nextLine().strip();
                    if (checkStopInput(input)) {
                        isRunning = false;
                        continue;
                    }

                    if (input.isBlank()) {
                        continue;
                    }

                    processInputAndPrintResult(input);
                } catch(ArithmeticException | IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void processInputAndPrintResult(String input) {
        Expression expression = InputParser.parseInput(input);
        int result = Calculator.calculate(expression);
        printResult(expression, result);
    }

    private boolean checkStopInput(String input) {
        return "exit".equalsIgnoreCase(input);
    }

    private void printResult(Expression expression, int result) {
        System.out.println(expression + " = " + result);
    }
}
