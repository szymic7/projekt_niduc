package arq;

public class CoderCRC16 {

    //private static final String DIVISOR = "10001000000100001"; // CRC-16 divisor (17 bits)

    public CoderCRC16() {

    }

    public String calculateCRC16(String input) {
        String divisior = "10001000000100001";
        StringBuilder shiftedDivisor = new StringBuilder(divisior);
        // Dopisanie 16 zer z prawej strony ciągu bitów - miejsce na sumę kontrolną
        StringBuilder inputBuilder = new StringBuilder(input);
        inputBuilder.append("0000000000000000");

        int notZeroBytes = 0;

        // result - reszta z dzielenia
        StringBuilder result = new StringBuilder(inputBuilder);

        // Cykliczne dzielenie przez wielomian
        for (int i = 0; i < inputBuilder.length(); i++) {

            // If MSB of result is 1, perform XOR operation with divisor
            if (result.charAt(i) == '1') {
                for (int j = 0; j < divisior.length(); j++) {
                    // operacja XOR na bicie wyniku i odpowiadającym mu bicie wielomianu
                    result.setCharAt(i + j, (result.charAt(i + j) == divisior.charAt(j)) ? '0' : '1');
                }
            }

            // Sprawdzenie, czy wszystkie bity oprocz 16 najmlodszych zostaly wyzerowane
            for(int k = 0; k < inputBuilder.length() - 16; k++) {
                if(result.charAt(k) == '1') notZeroBytes += 1;
            }

            if(notZeroBytes == 0) {         // jesli tak - nie dzielimy dalej
                break;
            } else {
                notZeroBytes = 0;           // jesli nie - kontunuujemy dzielenie, z przesunietym w prawo wielomianem
            }

            // Shift divisor to the right by 1 - byc moze nie potrzebne, bo przechodzimy na kolejny bit ciagu input
            /*if (i < input.length() - 1) {
                shiftedDivisor.insert('0', 0);
            }*/
        }

        // Return the last 16 bits of the result as CRC value
        return result.substring(result.length() - 16);

    }

    public String addCRC16(String bits) {
        StringBuilder bitsWithCRC = new StringBuilder(bits);
        String crc16 = this.calculateCRC16(bits);
        bitsWithCRC.append(crc16);
        return bitsWithCRC.toString();
    }

}
