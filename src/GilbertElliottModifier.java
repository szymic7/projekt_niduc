import java.util.Random;

public class GilbertElliottModifier {
    private Random random;
    public GilbertElliottModifier() {
        this.random = new Random();
    }


    // Funkcja modyfikująca tablicę stringów zgodnie z modelem Gilberta-Elliotta z wykorzystaniem BSC
    public String[] modifyStringByElliotGilbert(String[] packets, float pGood, float pBad, float pErrorInBad) {
        String[] modifiedPackets = new String[packets.length];
        for (int i = 0; i < packets.length; i++) {
            modifiedPackets[i] = modifyString(packets[i], pGood, pBad, pErrorInBad);
        }
        return modifiedPackets;
    }

    // Funkcja modyfikująca stringa zgodnie z modelem Gilberta-Elliotta z wykorzystaniem BSC w stanie "złym"
    private String modifyString(String input, float pGood, float pBad, float pErrorInBad) {
        BSC bsc = new BSC();
        StringBuilder output = new StringBuilder();
        boolean channelGood = true; // Początkowo zakładamy, że kanał jest w stanie "dobrym"

        for (char bit : input.toCharArray()) {
            // Sprawdzenie, czy kanał zmienia stan
            if (random.nextFloat() < (channelGood ? pGood : pBad)) {
                channelGood = !channelGood; // Zmiana stanu kanału
            }

            // W przypadku stanu "złego", wywołujemy funkcję BSCcoding z prawdopodobieństwem błędu pErrorInBad
            if (!channelGood) {
                output.append(bsc.BSCcoding(String.valueOf(bit), pErrorInBad));
            } else {
                output.append(bit);
            }
        }

        return output.toString();
    }


}