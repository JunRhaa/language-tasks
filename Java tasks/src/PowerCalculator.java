import java.util.Scanner;

public class PowerCalculator {
    public static double power(double base, int exponent) {
        if (exponent < 0) {
            throw new IllegalArgumentException("Степень не может быть отрицательной.");
        }
        return Math.pow(base, exponent);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите основание и степень:");
        double base = scanner.nextDouble();
        int exponent = scanner.nextInt();
        try {
            System.out.println("Результат возведения в степень: " + power(base, exponent));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
