package com.auriback.calculator;

import com.auriback.calculator.util.Calculator;
import com.auriback.calculator.util.Operator;

import java.util.Scanner;

public class Application {
    private static final String ERROR_INCORRECT_INPUT_FORMAT = "Ошибка: неверный формат. Используйте: число оператор число";

    public void run() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String inputString = scanner.nextLine().strip();

            if ("exit".equalsIgnoreCase(inputString)) {
                break;
            }

            String[] vals = inputString.split("\\s+");
            if (vals.length != 3) {
                System.out.println(ERROR_INCORRECT_INPUT_FORMAT);
                continue;
            }
            try {
                int a = Integer.parseInt(vals[0]);
                int b = Integer.parseInt(vals[2]);
                String operator = vals[1];

                int result = Calculator.calculate(a, b, Operator.fromString(operator));
                System.out.println(a + " " + operator + " " + b + " = " + result);
            } catch (NumberFormatException e) {
                System.out.println(ERROR_INCORRECT_INPUT_FORMAT);
            } catch (ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
