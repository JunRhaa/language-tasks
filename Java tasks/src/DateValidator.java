import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class DateValidator {
    public static void validateDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        try {
            LocalDate.parse(date, formatter);
        } catch (DateTimeParseException e) {
            throw new DateTimeParseException("Неверный формат даты. Используйте dd.MM.yyyy.", date, 0);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите дату в формате dd.MM.yyyy:");
        String date = scanner.nextLine();
        try {
            validateDate(date);
            System.out.println("Дата корректна.");
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
        }
    }
}
