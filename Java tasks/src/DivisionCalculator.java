import java.util.Scanner;

public class DivisionCalculator {
    public static double divide(int numerator, int denominator) {
        if (denominator == 0) {
            throw new ArithmeticException("Деление на ноль недопустимо.");
        }
        return (double) numerator / denominator;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите числитель и знаменатель:");
        int num = scanner.nextInt();
        int denom = scanner.nextInt();
        try {
            System.out.println("Результат деления: " + divide(num, denom));
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }
}
