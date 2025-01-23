import java.util.Scanner;

public class ZeroArrayChecker {
    public static void checkArrayForZeros(int[] array) {
        for (int num : array) {
            if (num == 0) {
                throw new IllegalArgumentException("Массив содержит нули.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите размер массива:");
        int size = scanner.nextInt();
        int[] array = new int[size];
        System.out.println("Введите элементы массива:");
        for (int i = 0; i < size; i++) {
            array[i] = scanner.nextInt();
        }
        try {
            checkArrayForZeros(array);
            System.out.println("Массив не содержит нулей.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
