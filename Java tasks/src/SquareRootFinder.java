import java.util.Scanner;

public class SquareRootFinder {
    public static double findSquareRoot(double number) {
        if (number < 0) {
            throw new IllegalArgumentException("Невозможно найти корень из отрицательного числа.");
        }
        return Math.sqrt(number);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите число для нахождения корня:");
        double number = scanner.nextDouble();
        try {
            System.out.println("Квадратный корень: " + findSquareRoot(number));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
