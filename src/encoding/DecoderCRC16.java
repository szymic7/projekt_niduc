package encoding;

public class DecoderCRC16 {

    public DecoderCRC16() {}

    public boolean decode(byte[] input) {
        String inputStr = new String(input);
        String data = inputStr.substring(0, inputStr.length() - 16);
        String receivedCRC = inputStr.substring(inputStr.length() - 16);
        String calculatedCRC = calculateCRC16(data);
        return receivedCRC.equals(calculatedCRC);
    }

    public String calculateCRC16(String input) {

        // Wielomian CRC: x^16+x^12+x^5+1
        String divisor = "10001000000100001";

        // result - reszta z dzielenia
        StringBuilder inputBuilder = new StringBuilder(input);
        inputBuilder.append("0000000000000000");

        // Zmienna kontrolna, sledzaca kiedy dzielnie powinno sie zakonczyc
        int notZeroBytes = 0;
        StringBuilder result = new StringBuilder(inputBuilder);

        // Cykliczne dzielenie przez wielomian
        for (int i = 0; i < inputBuilder.length(); i++) {

            // Sprawdzamy czy bit wyniku (ciagu bitow), na wysokosci ktorego jest najstarszy bit wielomianu, jest rowny 1
            if (result.charAt(i) == '1') {
                // Jesli tak - wykonujemy ciag operacji XOR
                for (int j = 0; j < divisor.length(); j++) {
                    // operacja XOR na bicie wyniku i odpowiadającym mu bicie wielomianu
                    result.setCharAt(i + j, (result.charAt(i + j) == divisor.charAt(j)) ? '0' : '1');
                }
            }

            // Sprawdzenie, czy wszystkie bity oprocz 16 najmlodszych zostaly wyzerowane
            for (int k = 0; k < inputBuilder.length() - 16; k++) {
                if (result.charAt(k) == '1') notZeroBytes++;
            }

            if (notZeroBytes == 0) {            // jesli tak - nie dzielimy dalej
                break;
            } else {
                notZeroBytes = 0;               // jesli nie - kontunuujemy dzielenie, z przesunietym w prawo wielomianem
            }
        }

        return result.substring(result.length() - 16);
    }
}