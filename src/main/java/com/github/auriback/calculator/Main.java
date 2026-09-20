package com.github.auriback.calculator;

import com.github.auriback.calculator.calculator.Calculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String inputString = scanner.nextLine();

            if (inputString.equals("exit")) {
                System.exit(0);
            }

            String[] vals = inputString.split(" ");
            try {
                int a = Integer.parseInt(vals[0]);
                String operator = vals[1];
                int b = Integer.parseInt(vals[2]);

                if (operator.equals("/") && b == 0) {
                    System.out.println("Ошибка: деление на ноль");
                }

                int result = Calculator.calculate(a, b, operator);
                System.out.println(a + " " + operator + " " + b + " = " + result);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: неверный формат. Используйте: число оператор число");
            }
        }
    }


}
