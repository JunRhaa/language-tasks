import java.util.Scanner;

public class FactorialCalculator {
    public static long factorial(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Факториал отрицательного числа не определен.");
        }
        long result = 1;
        for (int i = 2; i <= number; i++) {
            result *= i;
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите число для вычисления факториала:");
        int number = scanner.nextInt();
        try {
            System.out.println("Факториал: " + factorial(number));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
