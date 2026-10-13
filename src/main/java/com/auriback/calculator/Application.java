package com.auriback.calculator;

import com.auriback.calculator.models.Expression;
import com.auriback.calculator.models.Operation;
import com.auriback.calculator.util.Calculator;
import com.auriback.calculator.util.HistoryManager;
import com.auriback.calculator.util.InputParser;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Application {
    private static final String COMMAND_EXIT = "exit";
    private static final String COMMAND_HISTORY = "history";
    private static final String COMMAND_CLEAR = "clear";
    private static final String COMMAND_LAST_OPERATION = "last";
    private static final String COMMAND_HELP = "help";
    private static final String COMMAND_INPUT_SYMBOL = "> ";
    private static final String ERROR_UNEXPECTED = "Произошла непредвиденная ошибка приложения: %s%n";
    private static final String MSG_HISTORY_EMPTY = "История пуста";
    private static final String MSG_HISTORY_CLEAR = "История очищена";
    private static final String TEMPLATE_CALC_RESULT = "Результат: %s%n";

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
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.printf(ERROR_UNEXPECTED, e.getMessage());
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
        System.out.print(COMMAND_INPUT_SYMBOL);

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
        List<Operation> history = historyManager.getHistoryOperations();

        if (history.isEmpty()) {
            System.out.println(MSG_HISTORY_EMPTY);
            return;
        }

        for (int i = 1; i <= history.size(); i++) {
            System.out.println(i + ") " + history.get(i - 1));
        }
    }

    private void clearHistoryWithPrompt() {
        historyManager.clear();
        System.out.println(MSG_HISTORY_CLEAR);
    }

    private void processLastOperation() {
        historyManager.getLast().ifPresentOrElse(
                operation -> System.out.printf(TEMPLATE_CALC_RESULT, operation.result()),
                () -> System.out.println(MSG_HISTORY_EMPTY));
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
        System.out.printf(TEMPLATE_CALC_RESULT, operation.result().toPlainString());
    }

    private void addOperationToHistory(Operation operation) {
        historyManager.add(operation);
    }

    private void printHelp() {
        System.out.println("""
                Доступные команды:
                
                <число> <оператор> <число>
                
                Операторы:
                +  сложение
                -  вычитание
                *  умножение
                /  деление
                
                Дополнительные команды:
                history  — показать историю
                last     — повторить последнюю операцию
                clear    — очистить историю
                exit     — выход""");
    }
}
