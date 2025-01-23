import java.util.Scanner;

public class BinaryConverter {
    public static String convertToBinary(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("Отрицательные числа не могут быть преобразованы в двоичную систему.");
        }
        return Integer.toBinaryString(number);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите целое число для конвертации в двоичную систему:");
        int number = scanner.nextInt();
        try {
            System.out.println("Двоичное представление: " + convertToBinary(number));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
