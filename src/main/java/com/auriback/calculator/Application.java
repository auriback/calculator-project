package com.auriback.calculator;

import com.auriback.calculator.models.Expression;
import com.auriback.calculator.util.Calculator;
import com.auriback.calculator.util.InputParser;

import java.util.Optional;
import java.util.Scanner;

public class Application {
    public static final String COMMAND_EXIT = "exit";
    public static final String COMMAND_INPUT_SYMBOL = "> ";

    private Scanner scanner;
    private boolean isRunning = true;

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            this.scanner = scanner;

            while (isRunning) {
                try {
                    processing();
                } catch (ArithmeticException | IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    private void processing() {
        processInput().ifPresent(input -> {
            Expression expression = parseInput(input);
            int result = calculate(expression);
            printResult(expression, result);
        });
    }

    private Optional<String> processInput() {
        String input = readInput();

        if (isEmptyInput(input)) {
            return Optional.empty();
        }

        if (isInputStopCommand(input)) {
            stopInput();
            return Optional.empty();
        }

        return Optional.of(input);
    }

    private String readInput() {
        System.out.print(COMMAND_INPUT_SYMBOL);
        return scanner.nextLine().strip();
    }

    private boolean isEmptyInput(String input) {
        return input.isBlank();
    }

    private boolean isInputStopCommand(String input) {
        return COMMAND_EXIT.equalsIgnoreCase(input);
    }

    private void stopInput() {
        isRunning = false;
    }

    private Expression parseInput(String input) {
        return InputParser.parseInput(input);
    }

    private int calculate(Expression expression) {
        return Calculator.calculate(expression);
    }

    private void printResult(Expression expression, int result) {
        System.out.println(expression + " = " + result);
    }
}
