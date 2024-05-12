package generating_data;

import java.util.Random;
import java.util.*;

public class RandomNumberND {
    private Random random;

    public RandomNumberND() {
        this.random = new Random();
    }

    public String generateArray(int min, int max, int size) {
        int[] array = new int[size]; //inicjalizacja tablicy zastępczej
        String[] arrayString = new String[size]; //inicjalizacja tablicy zastępczej String
        double mean = (min + max) / 2.0; //średnia arytmetyczna
        double stdDev = (max - min) / 6.0; // odchylenie standardowe
        String pakiet = "";

        for (int i = 0; i < size; i += 2) {
            double u1 = random.nextDouble(); //losowa liczba z przedziału [0,1)
            double u2 = random.nextDouble(); //losowa liczba z przedziału [0,1)
            double z1 = Math.sqrt(-2 * Math.log(u1)) * Math.cos(2 * Math.PI * u2); //transformacja Boxa-Mullera
            double z2 = Math.sqrt(-2 * Math.log(u1)) * Math.sin(2 * Math.PI * u2); //transformacja Boxa-Mullera

            double x1 = z1 * stdDev + mean; // faktyczna wartość = transformata * odchylenie + średnia
            double x2 = z2 * stdDev + mean; // faktyczna wartość = transformata * odchylenie + średnia

            // Sprawdzenie, czy x1 mieści się w zakresie [min, max]
            if (x1 >= min && x1 <= max) {
                array[i] = (int) Math.round(x1); //jeśli tak zapis do tablicy
                arrayString[i] = Integer.toBinaryString(array[i]);
            } else {
                i--; //jeśli nie ponowne wygenerowanie liczby
            }

            // Sprawdzenie, czy x2 mieści się w zakresie [min, max]
            if (i + 1 < size && x2 >= min && x2 <= max) {
                array[i + 1] = (int) Math.round(x2); //jeśli tak zapis do tablicy
                arrayString[i+1] = Integer.toBinaryString(array[i+1]);
            } else if (i + 1 < size) {
                i--; //jeśli nie ponowne wygenerowanie liczby
            }
        }
        for(int i = 0; i<size; i++){
            pakiet += arrayString[i];
        }
        return pakiet; //zwrócenie tablicy
    }

}
