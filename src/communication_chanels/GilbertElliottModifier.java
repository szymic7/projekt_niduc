package communication_chanels;

import java.util.Random;

public class GilbertElliottModifier {

    public GilbertElliottModifier() {}


    // Funkcja modyfikująca tablicę stringów zgodnie z modelem Gilberta-Elliotta, z wykorzystaniem BSC w stanie "zlym"
    public String[] modifyStringByElliotGilbert(String[] packets, float pGood, float pBad, float pErrorInBad) {

        String[] modifiedPackets = new String[packets.length];

        for (int i = 0; i < packets.length; i++) {
            modifiedPackets[i] = modifyString(packets[i], pGood, pBad, pErrorInBad);
        }

        return modifiedPackets;
    }

    // Funkcja modyfikująca stringa zgodnie z modelem Gilberta-Elliotta, z wykorzystaniem BSC w stanie "złym"
    public String modifyString(String input, float pGoodToBad, float pBadToGood, float pErrorInBad) {

        // Deklaracja Random za kazdym wywolaniem metody, aby uzywac jednego obiektu do przesylania pakietow
        Random random = new Random();
        BSC bsc = new BSC();
        StringBuilder output = new StringBuilder();
        boolean channelGood = true; // Początkowo zakładamy, że kanał jest w stanie "dobrym"


        for (char bit : input.toCharArray()) {

            // Sprawdzenie, czy kanał zmienia stan
            if (random.nextFloat() < (channelGood ? pGoodToBad : pBadToGood)) {
                channelGood = !channelGood; // Zmiana stanu kanału
            }

            // W przypadku stanu "złego", wywołujemy funkcję BSCcoding z prawdopodobieństwem błędu pErrorInBad
            if (!channelGood) {
                output.append(bsc.BSCcoding(String.valueOf(bit), pErrorInBad));
            } else { // dla stanu "dobrego" - pError = 0, wiec przesylamy prawidlowo bit
                output.append(bit);
            }

        }

        return output.toString();
    }

}