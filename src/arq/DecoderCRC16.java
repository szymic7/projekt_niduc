package arq;

public class DecoderCRC16 {

    public DecoderCRC16() {

    }

    public boolean decode(String input) {

        // Wielomian CRC: x^16+x^12+x^5+1
        String divisior = "10001000000100001";

        // Zmienna kontrolna, sledzaca kiedy dzielnie powinno sie zakonczyc
        int notZeroBytes = 0;

        // result - reszta z dzielenia
        StringBuilder result = new StringBuilder(input);

        // Cykliczne dzielenie przez wielomian
        for (int i = 0; i < input.length(); i++) {

            // Sprawdzamy czy bit wyniku (ciagu bitow), na wysokosci ktorego jest najstarszy bit wielomianu, jest rowny 1
            if (result.charAt(i) == '1') {
                // Jesli tak - wykonujemy ciag operacji XOR
                for (int j = 0; j < divisior.length(); j++) {
                    // operacja XOR na bicie wyniku i odpowiadającym mu bicie wielomianu
                    result.setCharAt(i + j, (result.charAt(i + j) == divisior.charAt(j)) ? '0' : '1');
                }
            }

            // Sprawdzenie, czy wszystkie bity oprocz 16 najmlodszych zostaly wyzerowane
            for(int k = 0; k < input.length() - 16; k++) {
                if(result.charAt(k) == '1') notZeroBytes += 1;
            }

            if(notZeroBytes == 0) {         // jesli tak - nie dzielimy dalej
                break;
            } else {
                notZeroBytes = 0;           // jesli nie - kontunuujemy dzielenie, z przesunietym w prawo wielomianem
            }

        }

        // Zwracamy najmlodsze 16 bitow - obliczaona sume kontrolna CRC16
        // return result.substring(result.length() - 16);

        String calculatedCRC = result.substring(result.length() - 16);
        for(char bit: calculatedCRC.toCharArray()) {
            if(bit == '1') return false; // Jesli CRC nie jest ciagiem zer, to pakiet zostal blednie przeslany
        }

        return true;

    }

}
