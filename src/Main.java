import arq.CoderCRC16;
import arq.CoderParityBit;
import arq.DecoderCRC16;
import arq.DecoderParityBit;

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


        // TEST1 - CRC16 i BSC
        System.out.println("\nTEST 1: CRC16 i kanal BSC");
        String pakiet1 = ciag.substring(0, 20);     // utowrzenie pakietu n = 20 bitow
        System.out.println("a) " + pakiet1 + " - pakiet przed dodaniem sumy kontrolnej. Rozmiar: " + pakiet1.length());


        pakiet1 = coderCRC.addCRC16(pakiet1);
        System.out.println("b) " + pakiet1 + " - pakiet po dodaniu sumy kontrolnej. Rozmiar: " + pakiet1.length());

        BSC bsc1 = new BSC();
        String wyslany1 = bsc1.BSCcoding(pakiet1, 0.05f);
        System.out.println("c) " + wyslany1 + " - pakiet po przeslaniu kanalem BSC. Rozmiar: " + wyslany1.length());

        String odebraneCRC1 = decoderCRC.calculateCRC16(wyslany1);
        System.out.println("d) " + odebraneCRC1 + " - CRC wyliczone z odebranego pakietu.");


        // TEST2 - CRC16 i Gilbert-Elliott
        System.out.println("\nTEST 2: CRC16 i kanal Gilberta-Elliotta");
        String pakiet2 = ciag.substring(20, 40);     // utowrzenie pakietu n = 20 bitow
        System.out.println("a) " + pakiet2 + " - pakiet przed dodaniem sumy kontrolnej. Rozmiar: " + pakiet2.length());

        pakiet2 = coderCRC.addCRC16(pakiet2);
        System.out.println("b) " + pakiet2 + " - pakiet po dodaniu sumy kontrolnej. Rozmiar: " + pakiet2.length());

        GilbertElliottModifier gilbertElliottModifier = new GilbertElliottModifier();
        String wyslany2 = gilbertElliottModifier.modifyString(pakiet2, 1, 0.2f, 0.05f);
        System.out.println("c) " + wyslany2 + " - pakiet po przeslaniu kanalem Gilberta-Elliotta. Rozmiar: " + wyslany2.length());

        String odebraneCRC2 = decoderCRC.calculateCRC16(wyslany2);
        System.out.println("d) " + odebraneCRC2 + " - CRC wyliczone z odebranego pakietu.");


        // TEST3 - Bit parzystosci i BSC
        System.out.println("\nTEST 3: Bit parzystosci i BSC");
        String pakiet3 = ciag.substring(40, 60);     // utowrzenie pakietu n = 20 bitow
        System.out.println("a) " + pakiet3 + " - pakiet przed dodaniem bitu parzystosci. Rozmiar: " + pakiet3.length());

        pakiet3 = coderPB.addParityBit(pakiet3);
        System.out.println("b) " + pakiet3 + " - pakiet po dodaniu bitu parzystosci. Rozmiar: " + pakiet3.length());

        BSC bsc2 = new BSC();
        String wyslany3 = bsc2.BSCcoding(pakiet3, 0.05f);
        System.out.println("c) " + wyslany3 + " - pakiet po przeslaniu kanalem BSC. Rozmiar: " + wyslany3.length());

        decoderPB.decode(wyslany3);


        // TEST4 - Bit parzystosci i Gilbert-Elliott
        System.out.println("\nTEST 4: Bit parzystosci i kanal Gilberta-Elliotta");
        String pakiet4 = ciag.substring(60, 80);     // utowrzenie pakietu n = 20 bitow
        System.out.println("a) " + pakiet4 + " - pakiet przed dodaniem bitu parzystosci. Rozmiar: " + pakiet4.length());

        pakiet4 = coderPB.addParityBit(pakiet4);
        System.out.println("b) " + pakiet4 + " - pakiet po dodaniu bitu parzystosci. Rozmiar: " + pakiet4.length());

        GilbertElliottModifier gilbertElliottModifier2 = new GilbertElliottModifier();
        String wyslany4 = gilbertElliottModifier2.modifyString(pakiet4, 1, 0.2f, 0.05f);
        System.out.println("c) " + wyslany4 + " - pakiet po przeslaniu kanalem Gilberta-Elliotta. Rozmiar: " + wyslany4.length());

        decoderPB.decode(wyslany4);



        // TEST - sprawdzenie dzialania BSC

        /*String[] tab = new String[5];
        BSC bsc = new BSC();

        // 5 przykladowych pakietow o dlugosci n = 8
        tab[0] = "01010101";
        tab[1] = "01111111";
        tab[2] = "10100000";
        tab[3] = "01010000";
        tab[4] = "01100001";
        for(int i = 0;i< tab.length; i++){
            System.out.println(tab[i]);
        }

        System.out.println();

        // przeslanie 5 przykladowych pakietow przez kanal komunikacyjny
        tab = bsc.BSCtabcoding(tab,0.1F);
        for(int i = 0; i< tab.length; i++){
            System.out.println(tab[i]);
        }

        // przeslanie dlugiego ciagu bitow przez kanal komunikacyjny (bez podzialu na pakiety)
        ciag = bsc.BSCcoding(ciag,0.1F);
        System.out.println(ciag);*/



        // TEST - sprawdzenie dzialania kanalu Gilberta-Elliota

        /*GilbertElliottModifier GEM = new GilbertElliottModifier();
        String[] tab = new String[5];
        tab[0] = "01010101";
        tab[1] = "01111111";
        tab[2] = "10100000";
        tab[3] = "01010000";
        tab[4] = "01100001";
        for(int i = 0;i< tab.length; i++){
            System.out.println(tab[i]);
        }

        System.out.println();

        tab = GEM.modifyStringByElliotGilbert(tab,1,0.2F,0.6F);
        for(int i = 0;i< tab.length; i++){
            System.out.println(tab[i]);
        }*/


    }
}
