import java.util.Scanner;

public class TemperatureConverter {
    public static double celsiusToFahrenheit(double celsius) {
        if (celsius < -273.15) {
            throw new IllegalArgumentException("Температура не может быть ниже абсолютного нуля.");
        }
        return (celsius * 9/5) + 32;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введите температуру в Цельсиях:");
        double celsius = scanner.nextDouble();
        try {
            System.out.println("Температура в Фаренгейтах: " + celsiusToFahrenheit(celsius));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
