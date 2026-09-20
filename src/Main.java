import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("--- Тесты ---");
        System.out.println("5 + 3 = " + calculate(5, 3, "+"));   // ожидается: 8
        System.out.println("5 / 2 = " + calculate(5, 2, "/"));   // ожидается: 2
        System.out.println("10 - 3 = " + calculate(10, 3, "-")); // ожидается: 7
        System.out.println("4 * 6 = " + calculate(4, 6, "*"));   // ожидается: 24
        System.out.println("--- Конец тестов ---\n");

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

                int result = calculate(a, b, operator);
                System.out.println(a + " " + operator + " " + b + " = " + result);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: неверный формат. Используйте: число оператор число");
            }
        }
    }

    private static int calculate(int a, int b, String operator) {
        return switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> 0;
        };
    }
}
