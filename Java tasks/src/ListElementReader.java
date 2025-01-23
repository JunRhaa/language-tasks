import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ListElementReader {
    public static <T> T getElementFromList(List<T> list, int index) {
        if (index < 0 || index >= list.size()) {
            throw new IndexOutOfBoundsException("Индекс выходит за пределы списка.");
        }
        return list.get(index);
    }

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("Первый элемент");
        list.add("Второй элемент");
        list.add("Третий элемент");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите индекс элемента для чтения (0, 1 или 2):");
        int index = scanner.nextInt();
        try {
            System.out.println("Элемент: " + getElementFromList(list, index));
        } catch (IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }
}
