import java.util.Scanner;

public class StringToIntConverter {
    public static int convertStringToInt(String str) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Невозможно конвертировать строку в целое число.");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите строку для конвертации:");
        String input = scanner.nextLine();
        try {
            System.out.println("Конвертированное число: " + convertStringToInt(input));
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
    }
}
