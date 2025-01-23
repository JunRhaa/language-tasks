import java.util.Scanner;

public class StringConcatenator {
    public static String concatenate(String str1, String str2) {
        if (str1 == null || str2 == null) {
            throw new NullPointerException("Одна из строк равна null.");
        }
        return str1 + str2;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите первую строку:");
        String str1 = scanner.nextLine();
        System.out.println("Введите вторую строку:");
        String str2 = scanner.nextLine();
        try {
            System.out.println("Результат конкатенации: " + concatenate(str1, str2));
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }
}
