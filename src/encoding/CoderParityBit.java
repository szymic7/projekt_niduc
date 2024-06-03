package encoding;

public class CoderParityBit {

    /**
     * Dodanie bitu parzystości do tablicy bajtów.
     * @param input Tablica bajtów wejściowych.
     * @return Tablica bajtów z dodanym bitem parzystości.
     */
    public byte[] addParityBit(byte[] input) {
        int onesCount = 0;
        for (byte b : input) {
            // Zliczanie jedynek
            if (b == '1') onesCount++;
        }
        // Obliczenie bitu parzystości
        byte parityBit = (byte) ((onesCount % 2 == 0) ? '0' : '1');
        byte[] result = new byte[input.length + 1];
        System.arraycopy(input, 0, result, 0, input.length);
        result[input.length] = parityBit;
        return result;
    }
}