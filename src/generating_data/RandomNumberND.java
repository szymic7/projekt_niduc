package generating_data;

import java.util.Random;

public class RandomNumberND {

    public RandomNumberND() {}

    public byte[] generateArray(int min, int max, int size) {
        Random random = new Random();
        byte[] array = new byte[size]; //inicjalizacja tablicy zastępczej
        double mean = (min + max) / 2.0; //średnia arytmetyczna
        double stdDev = (max - min) / 6.0; // odchylenie standardowe

        for (int i = 0; i < size; i += 2) {
            double u1 = random.nextDouble(); //losowa liczba z przedziału [0,1)
            double u2 = random.nextDouble(); //losowa liczba z przedziału [0,1)
            double z1 = Math.sqrt(-2 * Math.log(u1)) * Math.cos(2 * Math.PI * u2); //transformacja Boxa-Mullera
            double z2 = Math.sqrt(-2 * Math.log(u1)) * Math.sin(2 * Math.PI * u2); //transformacja Boxa-Mullera

            double x1 = z1 * stdDev + mean; // faktyczna wartość = transformata * odchylenie + średnia
            double x2 = z2 * stdDev + mean; // faktyczna wartość = transformata * odchylenie + średnia

            // Sprawdzenie, czy x1 mieści się w zakresie [min, max]
            if (x1 >= min && x1 <= max) {
                array[i] = (byte) Math.round(x1); //jeśli tak zapis do tablicy
            } else {
                i--; //jeśli nie ponowne wygenerowanie liczby
            }

            // Sprawdzenie, czy x2 mieści się w zakresie [min, max]
            if (i + 1 < size && x2 >= min && x2 <= max) {
                array[i + 1] = (byte) Math.round(x2); //jeśli tak zapis do tablicy
            } else if (i + 1 < size) {
                i--; //jeśli nie ponowne wygenerowanie liczby
            }
        }
        return array;
    }
}