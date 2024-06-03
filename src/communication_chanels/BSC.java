package communication_chanels;

import java.util.Random;

public class BSC {

    private Random random;

    public BSC() {
        random = new Random();
    }

    /**
     * Symulacja kanału BSC (Binary Symmetric Channel) z określonym prawdopodobieństwem błędu.
     * @param input Tablica bajtów wejściowych.
     * @param probability Prawdopodobieństwo błędu bitowego.
     * @return Tablica bajtów po przejściu przez kanał BSC.
     */
    public byte[] BSCcoding(byte[] input, float probability) {
        byte[] output = new byte[input.length];
        for (int i = 0; i < input.length; i++) {
            // Symulacja błędu bitowego
            if (random.nextFloat() < probability) {
                output[i] = (byte) (input[i] == '0' ? '1' : '0');
            } else {
                output[i] = input[i];
            }
        }
        return output;
    }
}