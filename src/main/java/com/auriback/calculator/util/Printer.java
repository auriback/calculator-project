package com.auriback.calculator.util;

import com.auriback.calculator.models.Expression;
import com.auriback.calculator.models.Operation;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public final class Printer {

    private static final String COMMAND_INPUT_SYMBOL = "> ";
    private static final String MSG_HISTORY_EMPTY = "История пуста";
    private static final String MSG_HISTORY_CLEAR = "История очищена";
    private static final String TEMPLATE_NEGATIVE_OPERAND = "(%s)";
    private static final String TEMPLATE_CALC_RESULT = "Результат: %s%n";
    private static final String TEMPLATE_CALC_HISTORY = "%d ) %s%n";
    private static final String TEMPLATE_EXPRESSION = "%s %s %s = %s";

    private static final int DISPLAY_SCALE = 1;
    private static final RoundingMode DISPLAY_ROUNDING = RoundingMode.HALF_EVEN;

    private Printer() {

    }

    public static void printError(String errorMessage) {
        System.out.println(errorMessage);
    }

    public static void printErrorFormat(String format, String errorMessage) {
        System.out.printf(format, errorMessage);
    }

    public static void printInputPrompt() {
        System.out.print(COMMAND_INPUT_SYMBOL);
    }

    public static void printHistory(List<Operation> history) {
        if (history.isEmpty()) {
            printMsgHistoryEmpty();
            return;
        }

        for (int i = 1; i <= history.size(); i++) {
            Operation operation = history.get(i - 1);
            System.out.printf(TEMPLATE_CALC_HISTORY, i, formatOperation(operation));
        }
    }

    private static String formatOperation(Operation operation) {
        Expression expression = operation.expression();
        String firstNumber = expression.firstNumber().toPlainString();
        String operator = expression.operator().getSymbol();
        String secondNumber = getOperandForDisplay(expression.secondNumber());
        String result = getNumberWithScale(operation.result()).toPlainString();

        return String.format(TEMPLATE_EXPRESSION, firstNumber, operator, secondNumber, result);
    }

    private static BigDecimal getNumberWithScale(BigDecimal number) {
        return number.setScale(DISPLAY_SCALE, DISPLAY_ROUNDING);
    }

    private static String getOperandForDisplay(BigDecimal number) {
        return number.compareTo(BigDecimal.ZERO) < 0
                ? String.format(TEMPLATE_NEGATIVE_OPERAND, number.toPlainString())
                : number.toPlainString();
    }

    public static void printMsgClearHistory() {
        System.out.println(MSG_HISTORY_CLEAR);
    }

    public static void printLastOperation(Operation operation) {
        printResult(operation);
    }

    public static void printMsgLastOperation() {
        printMsgHistoryEmpty();
    }

    private static void printMsgHistoryEmpty() {
        System.out.println(MSG_HISTORY_EMPTY);
    }

    public static void printResult(Operation operation) {
        System.out.printf(TEMPLATE_CALC_RESULT, getNumberWithScale(operation.result()));
    }

    public static void printHelp() {
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
