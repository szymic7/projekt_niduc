package encoding;

public class DecoderParityBit {

    public DecoderParityBit() {}

    /**
     * Dekodowanie tablicy bajtów przy użyciu bitu parzystości.
     * @param input Tablica bajtów wejściowych.
     * @return true, jeśli dekodowanie się powiodło, false w przeciwnym razie.
     */
    public boolean decode(byte[] input) {
        int ones = 0;
        for (int i = 0; i < input.length - 1; i++) {
            // Zliczanie jedynek
            if (input[i] == '1') ones++;
        }
        char receivedParityBit = (char) input[input.length - 1];
        char calculatedParityBit = (ones % 2 == 0) ? '0' : '1';
        return receivedParityBit == calculatedParityBit;
    }
}