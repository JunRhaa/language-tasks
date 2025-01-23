import java.util.Scanner;

public class AgeValidator {
    public static void validateAge(int age) {
        if (age < 0 || age > 150) {
            throw new IllegalArgumentException("Возраст должен быть между 0 и 150.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите ваш возраст:");
        int age = scanner.nextInt();
        try {
            validateAge(age);
            System.out.println("Возраст корректен.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
