import arq.CoderCRC16;
import arq.CoderParityBit;
import arq.DecoderCRC16;
import arq.DecoderParityBit;
import communication_chanels.BSC;
import communication_chanels.GilbertElliottModifier;

public class Main {

    public static void main(String[] args) {

        // inicjalizacja tablicy liczb losowych, z konwersja na ciag bitow
        RandomNumberND generator = new RandomNumberND();
        String ciag = generator.generateArray(0, 100, 200);
        //System.out.println(ciag);

        // inicjalizacja koderow i dekoderow
        CoderCRC16 coderCRC = new CoderCRC16();
        DecoderCRC16 decoderCRC = new DecoderCRC16();
        CoderParityBit coderPB = new CoderParityBit();
        DecoderParityBit decoderPB = new DecoderParityBit();


        // SYMULACJA 1 - CRC16 i BSC
        System.out.println("\nSYMULACJA 1: CRC16 i kanal BSC");
        String pakiet1 = ciag.substring(0, 20);     // utowrzenie pakietu n = 20 bitow
        System.out.println("a) " + pakiet1 + " - pakiet przed dodaniem sumy kontrolnej. Rozmiar: " + pakiet1.length());


        pakiet1 = coderCRC.addCRC16(pakiet1);
        System.out.println("b) " + pakiet1 + " - pakiet po dodaniu sumy kontrolnej. Rozmiar: " + pakiet1.length());

        BSC bsc1 = new BSC();
        String wyslany1 = bsc1.BSCcoding(pakiet1, 0.05f);
        System.out.println("c) " + wyslany1 + " - pakiet po przeslaniu kanalem BSC. Rozmiar: " + wyslany1.length());

        String odebraneCRC1 = decoderCRC.calculateCRC16(wyslany1);
        System.out.println("d) " + odebraneCRC1 + " - CRC wyliczone z odebranego pakietu.");


        // SYMULACJA 2 - CRC16 i Gilbert-Elliott
        System.out.println("\nSYMULACJA 2: CRC16 i kanal Gilberta-Elliotta");
        String pakiet2 = ciag.substring(20, 40);     // utowrzenie pakietu n = 20 bitow
        System.out.println("a) " + pakiet2 + " - pakiet przed dodaniem sumy kontrolnej. Rozmiar: " + pakiet2.length());

        pakiet2 = coderCRC.addCRC16(pakiet2);
        System.out.println("b) " + pakiet2 + " - pakiet po dodaniu sumy kontrolnej. Rozmiar: " + pakiet2.length());

        GilbertElliottModifier gilbertElliottModifier = new GilbertElliottModifier();
        String wyslany2 = gilbertElliottModifier.modifyString(pakiet2, 0.5f, 0.5f, 0.05f);
        System.out.println("c) " + wyslany2 + " - pakiet po przeslaniu kanalem Gilberta-Elliotta. Rozmiar: " + wyslany2.length());

        String odebraneCRC2 = decoderCRC.calculateCRC16(wyslany2);
        System.out.println("d) " + odebraneCRC2 + " - CRC wyliczone z odebranego pakietu.");


        // SYMULACJA 3 - Bit parzystosci i BSC
        System.out.println("\nSYMULACJA 3: Bit parzystosci i BSC");
        String pakiet3 = ciag.substring(40, 60);     // utowrzenie pakietu n = 20 bitow
        System.out.println("a) " + pakiet3 + " - pakiet przed dodaniem bitu parzystosci. Rozmiar: " + pakiet3.length());

        pakiet3 = coderPB.addParityBit(pakiet3);
        System.out.println("b) " + pakiet3 + " - pakiet po dodaniu bitu parzystosci. Rozmiar: " + pakiet3.length());

        BSC bsc2 = new BSC();
        String wyslany3 = bsc2.BSCcoding(pakiet3, 0.05f);
        System.out.println("c) " + wyslany3 + " - pakiet po przeslaniu kanalem BSC. Rozmiar: " + wyslany3.length());

        decoderPB.decode(wyslany3);


        // SYMULACJA 4 - Bit parzystosci i Gilbert-Elliott
        System.out.println("\nSYMULACJA 4: Bit parzystosci i kanal Gilberta-Elliotta");
        String pakiet4 = ciag.substring(60, 80);     // utowrzenie pakietu n = 20 bitow
        System.out.println("a) " + pakiet4 + " - pakiet przed dodaniem bitu parzystosci. Rozmiar: " + pakiet4.length());

        pakiet4 = coderPB.addParityBit(pakiet4);
        System.out.println("b) " + pakiet4 + " - pakiet po dodaniu bitu parzystosci. Rozmiar: " + pakiet4.length());

        GilbertElliottModifier gilbertElliottModifier2 = new GilbertElliottModifier();
        String wyslany4 = gilbertElliottModifier2.modifyString(pakiet4, 0.5f, 0.5f, 0.05f);
        System.out.println("c) " + wyslany4 + " - pakiet po przeslaniu kanalem Gilberta-Elliotta. Rozmiar: " + wyslany4.length());

        decoderPB.decode(wyslany4);

    }
}
