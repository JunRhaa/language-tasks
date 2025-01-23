import java.util.Scanner;

public class DivisibilityChecker {
    public static boolean isDivisible(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Деление на ноль недопустимо.");
        }
        return a % b == 0;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите два числа:");
        int num1 = scanner.nextInt();
        int num2 = scanner.nextInt();
        try {
            System.out.println(num1 + " делится на " + num2 + ": " + isDivisible(num1, num2));
        } catch (ArithmeticException e) {
            System.out.println(e.getMessage());
        }
    }
}
