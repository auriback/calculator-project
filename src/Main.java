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

                calculate(a, b, operator);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка: неверный формат. Используйте: число оператор число");
            }
        }
    }

    private static void calculate(int a, int b, String operator) {
        if (operator.equals("/") && b == 0) {
            System.out.println("Ошибка: деление на ноль");
            return;
        }

        int result = switch (operator) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            default -> 0;
        };
        System.out.println(a + " " + operator + " " + b + " = " + result);
    }
}
