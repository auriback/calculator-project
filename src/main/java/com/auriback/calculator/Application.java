package com.auriback.calculator;

import com.auriback.calculator.models.Expression;
import com.auriback.calculator.models.Operation;
import com.auriback.calculator.util.Calculator;
import com.auriback.calculator.util.InputParser;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Scanner;

public class Application {
    public static final String COMMAND_EXIT = "exit";
    public static final String COMMAND_INPUT_SYMBOL = "> ";
    public static final String ERROR_UNEXPECTED = "Произошла непредвиденная ошибка приложения: %s%n";

    public Application(Scanner scanner) {
        this.scanner = scanner;
    }

    private final Scanner scanner;
    private boolean isRunning = true;

    public void run() {
        while (isRunning) {
            try {
                processing();
            } catch (ArithmeticException | IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.printf(ERROR_UNEXPECTED, e.getMessage());
            }
        }
    }

    private void processing() {
        processInput().ifPresent(input -> {
            Expression expression = parseInput(input);
            BigDecimal result = calculate(expression);
            printResult(new Operation(expression, result));
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

        if (!scanner.hasNextLine()) {
            stopInput();
            return "";
        }

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

    private BigDecimal calculate(Expression expression) {
        return Calculator.calculate(expression);
    }

    private void printResult(Operation operation) {
        System.out.println(operation);
    }
}
