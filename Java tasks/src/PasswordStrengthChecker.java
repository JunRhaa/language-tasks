import java.util.Scanner;

public class PasswordStrengthChecker {
    public static void checkPasswordStrength(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Пароль должен содержать не менее 8 символов.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите пароль для проверки:");
        String password = scanner.nextLine();
        try {
            checkPasswordStrength(password);
            System.out.println("Пароль соответствует требованиям.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
