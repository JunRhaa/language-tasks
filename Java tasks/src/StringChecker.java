import java.util.Scanner;

public class StringChecker {
    public static void checkString(String str) {
        if (str == null || str.isEmpty()) {
            throw new IllegalArgumentException("Строка пустая или равна null.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строку для проверки:");
        String str = scanner.nextLine();
        try {
            checkString(str);
            System.out.println("Строка корректна.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
