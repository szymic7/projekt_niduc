package encoding;

public class CoderCRC16 {

    public byte[] addCRC16(byte[] input) {
        String crc = calculateCRC16(new String(input));
        byte[] result = new byte[input.length + crc.length()];
        System.arraycopy(input, 0, result, 0, input.length);
        System.arraycopy(crc.getBytes(), 0, result, input.length, crc.length());
        return result;
    }

    public String calculateCRC16(String input) {

        // Wielomian CRC: x^16+x^12+x^5+1
        String divisior = "10001000000100001";

        // Dopisanie 16 zer z prawej strony ciągu bitów - miejsce na sumę kontrolną
        StringBuilder inputBuilder = new StringBuilder(input);
        inputBuilder.append("0000000000000000");

        int notZeroBytes = 0;

        // result - reszta z dzielenia
        StringBuilder result = new StringBuilder(inputBuilder);

        // Cykliczne dzielenie przez wielomian
        for (int i = 0; i < inputBuilder.length(); i++) {

            // Sprawdzamy czy bit wyniku (ciagu bitow), na wysokosci ktorego jest najstarszy bit wielomianu, jest rowny 1
            if (result.charAt(i) == '1') {
                // Jesli tak - wykonujemy ciag operacji XOR
                for (int j = 0; j < divisior.length(); j++) {
                    // operacja XOR na bicie wyniku i odpowiadającym mu bicie wielomianu
                    result.setCharAt(i + j, (result.charAt(i + j) == divisior.charAt(j)) ? '0' : '1');
                }
            }

            // Sprawdzenie, czy wszystkie bity oprocz 16 najmlodszych zostaly wyzerowane
            for (int k = 0; k < inputBuilder.length() - 16; k++) {
                if (result.charAt(k) == '1') notZeroBytes++;
            }

            if (notZeroBytes == 0) {        // jesli tak - nie dzielimy dalej
                break;
            } else {
                notZeroBytes = 0;           // jesli nie - kontunuujemy dzielenie, z przesunietym w prawo wielomianem
            }
        }

        // Zwracamy najmlodsze 16 bitow - obliczaona sume kontrolna CRC16
        return result.substring(result.length() - 16);
    }
}