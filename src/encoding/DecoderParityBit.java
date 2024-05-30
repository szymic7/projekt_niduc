package encoding;

public class DecoderParityBit {

    public DecoderParityBit() {}

    // Dekodowanie dla pojedynczego pakietu
    public boolean decode(String bits) {

        char receivedParityBit = bits.charAt(bits.length()-1); // odebrany bit parzystosci
        int ones = 0;
        char calculatedParityBit; // obliczony bit parzystosci

        for(int i = 0; i < bits.length() - 1; i++) { // liczymy bit parzystosci dla pakietu, nie uwzgledniajac jego bitu parzystosci
            if(bits.charAt(i) == '1') ones++;
        }

        if(ones % 2 == 1) {             // nieparzysta liczba jedynek
            calculatedParityBit = '1';  // bit parzystosci rowny 1
        } else {                        // parzysta liczba jedynek
            calculatedParityBit = '0';  // bit parzystosci rowny 0
        }

        // Sprawdzenie zgodnosci z odebranym bitem parzystosci
        if(calculatedParityBit == receivedParityBit) {
            // pakiet zostal prawidlowo przeslany
            return true;
        } else {
            // pakiet zostal przeslany blednie
            return false;
        }

    }

    // Dekodowanie dla tablicy - ciagu pakietow
    public void decodePackets(String[] bitsArray) {
        for(String bits: bitsArray) {
            this.decode(bits);
        }
    }

}
