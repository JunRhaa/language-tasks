import java.util.Scanner;

    public class MaxFinder {
    public static int findMax(int a, int b) {
        if (a == b) {
            throw new IllegalArgumentException("Числа равны. Невозможно определить максимум.");
        }
        return Math.max(a, b);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите два числа:");
        int num1 = scanner.nextInt();
        int num2 = scanner.nextInt();
        try {
            System.out.println("Максимум: " + findMax(num1, num2));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
