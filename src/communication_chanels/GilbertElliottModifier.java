package communication_chanels;

import java.util.Random;

public class GilbertElliottModifier {

    public GilbertElliottModifier() {}

    /**
     * Modyfikacja tablicy bajtów przy użyciu modelu Gilberta-Elliotta.
     * @param packets Tablica bajtów wejściowych.
     * @param pGood Prawdopodobieństwo przejścia z dobrego do złego stanu.
     * @param pBad Prawdopodobieństwo przejścia ze złego do dobrego stanu.
     * @param pErrorInBad Prawdopodobieństwo błędu bitowego w złym stanie.
     * @return Zmodyfikowana tablica bajtów.
     */
    public byte[] modifyStringByElliotGilbert(byte[] packets, float pGood, float pBad, float pErrorInBad) {
        byte[] modifiedPackets = new byte[packets.length];

        for (int i = 0; i < packets.length; i++) {
            // Modyfikacja każdego bajtu przy użyciu modelu Gilberta-Elliotta
            modifiedPackets[i] = modifyByte(packets[i], pGood, pBad, pErrorInBad);
        }

        return modifiedPackets;
    }

    /**
     * Modyfikacja pojedynczego bajtu przy użyciu modelu Gilberta-Elliotta.
     * @param input Bajt wejściowy.
     * @param pGoodToBad Prawdopodobieństwo przejścia z dobrego do złego stanu.
     * @param pBadToGood Prawdopodobieństwo przejścia ze złego do dobrego stanu.
     * @param pErrorInBad Prawdopodobieństwo błędu bitowego w złym stanie.
     * @return Zmodyfikowany bajt.
     */
    public byte modifyByte(byte input, float pGoodToBad, float pBadToGood, float pErrorInBad) {
        Random random = new Random();
        BSC bsc = new BSC();
        boolean channelGood = true;
        byte output = input;

        // Przełączenie stanu kanału
        if (random.nextFloat() < (channelGood ? pGoodToBad : pBadToGood)) {
            channelGood = !channelGood;
        }

        // Symulacja błędu w złym stanie
        if (!channelGood) {
            output = bsc.BSCcoding(new byte[]{input}, pErrorInBad)[0];
        }

        return output;
    }
}