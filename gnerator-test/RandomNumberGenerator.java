import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class RandomNumberGenerator {

    private static final Random random = new Random();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Podaj minimalną wartość: ");
        int min = scanner.nextInt();

        System.out.print("Podaj maksymalną wartość: ");
        int max = scanner.nextInt();

        System.out.print("Podaj ilość liczb do wygenerowania: ");
        int size = scanner.nextInt();

        int[] array = generateArray(min, max, size);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("wynik.txt"))) {
            for (int number : array) {
                writer.write(Integer.toString(number));
                writer.newLine();
            }
            System.out.println("Wygenerowane liczby zostały zapisane do pliku wynik.txt.");
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu do pliku: " + e.getMessage());
        }

        scanner.close();
    }

    public static int[] generateArray(int min, int max, int size) {
        int[] array = new int[size];
        double mean = (min + max) / 2.0;
        double stdDev = (max - min) / 6.0;

        for (int i = 0; i < size; i += 2) {
            double u1 = random.nextDouble();
            double u2 = random.nextDouble();
            double z1 = Math.sqrt(-2 * Math.log(u1)) * Math.cos(2 * Math.PI * u2);
            double z2 = Math.sqrt(-2 * Math.log(u1)) * Math.sin(2 * Math.PI * u2);

            double x1 = z1 * stdDev + mean;
            double x2 = z2 * stdDev + mean;

            if (x1 >= min && x1 <= max) {
                array[i] = (int) Math.round(x1);
            } else {
                i--;
            }

            if (i + 1 < size && x2 >= min && x2 <= max) {
                array[i + 1] = (int) Math.round(x2);
            } else if (i + 1 < size) {
                i--;
            }
        }
        return array;
    }
}
