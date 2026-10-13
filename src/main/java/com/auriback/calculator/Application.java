package com.auriback.calculator;

import com.auriback.calculator.models.Expression;
import com.auriback.calculator.models.Operation;
import com.auriback.calculator.util.Calculator;
import com.auriback.calculator.util.HistoryManager;
import com.auriback.calculator.util.InputParser;
import com.auriback.calculator.util.Printer;

import java.math.BigDecimal;
import java.util.Scanner;

public class Application {
    private static final String COMMAND_EXIT = "exit";
    private static final String COMMAND_HISTORY = "history";
    private static final String COMMAND_CLEAR = "clear";
    private static final String COMMAND_LAST_OPERATION = "last";
    private static final String COMMAND_HELP = "help";
    private static final String ERROR_UNEXPECTED = "Произошла непредвиденная ошибка приложения: %s%n";

    private final Scanner scanner;
    private final HistoryManager historyManager;
    private boolean isRunning = true;

    public Application(Scanner scanner, HistoryManager historyManager) {
        this.scanner = scanner;
        this.historyManager = historyManager;
    }

    public void run() {
        while (isRunning) {
            try {
                processing();
            } catch (ArithmeticException | IllegalArgumentException e) {
                Printer.printError(e.getMessage());
            } catch (Exception e) {
                Printer.printErrorFormat(ERROR_UNEXPECTED, e.getMessage());
            }
        }
    }

    private void processing() {
        String input = readInput();

        if (input.isBlank()) {
            return;
        }

        Runnable command = switch (input.toLowerCase()) {
            case COMMAND_EXIT -> this::stopInput;
            case COMMAND_HISTORY -> this::printHistory;
            case COMMAND_CLEAR -> this::clearHistoryWithPrompt;
            case COMMAND_LAST_OPERATION -> this::processLastOperation;
            case COMMAND_HELP -> this::printHelp;
            default -> () -> calculateAndRecord(input);
        };

        command.run();
    }

    private String readInput() {
        Printer.printInputPrompt();

        if (!scanner.hasNextLine()) {
            stopInput();
            return "";
        }

        return scanner.nextLine().strip();
    }

    private void stopInput() {
        isRunning = false;
    }

    private void printHistory() {
        Printer.printHistory(historyManager.getHistoryOperations());
    }

    private void clearHistoryWithPrompt() {
        historyManager.clear();
        Printer.printMsgClearHistory();
    }

    private void processLastOperation() {
        historyManager.getLast().ifPresentOrElse(
                Printer::printLastOperation,
                Printer::printMsgLastOperation);
    }

    private void calculateAndRecord(String input) {
        Expression expression = parseInput(input);
        BigDecimal result = calculate(expression);
        Operation operation = new Operation(expression, result);
        printResult(operation);
        addOperationToHistory(operation);
    }

    private Expression parseInput(String input) {
        return InputParser.parseInput(input);
    }

    private BigDecimal calculate(Expression expression) {
        return Calculator.calculate(expression);
    }

    private void printResult(Operation operation) {
        Printer.printResult(operation);
    }

    private void addOperationToHistory(Operation operation) {
        historyManager.add(operation);
    }

    private void printHelp() {
        Printer.printHelp();
    }
}
