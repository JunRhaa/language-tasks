import java.util.Scanner;

public class ElementFinder {
    public static int findElementInArray(int[] array, int element) {
        for (int num : array) {
            if (num == element) {
                return element;
            }
        }
        throw new IllegalArgumentException("Элемент не найден в массиве.");
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
        System.out.println("Введите элемент для поиска:");
        int element = scanner.nextInt();
        try {
            System.out.println("Найден элемент: " + findElementInArray(array, element));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
