import java.util.Scanner;

public class StringTrimmer {
    public static String trimString(String str, int length) {
        if (length > str.length()) {
            throw new IllegalArgumentException("Длина обрезки превышает длину строки.");
        }
        return str.substring(0, length);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строку:");
        String str = scanner.nextLine();
        System.out.println("Введите длину обрезки:");
        int length = scanner.nextInt();
        try {
            System.out.println("Обрезанная строка: " + trimString(str, length));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
