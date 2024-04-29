import arq.CoderParityBit;
import arq.DecoderParityBit;

public class Main {

    public static void main(String[] args) {

        RandomNumberND generator = new RandomNumberND();
        String ciag;
        ciag = generator.generateArray(0, 100, 200);
        System.out.println(ciag);

        // TEST - przeslanie probnego pakietu z dodanym bitem parzystosci przez kanal BSC i odebranie przez dekoder

        /*String pakiet = ciag.substring(0, 10); // utowrzenie pakietu n = 10 bitow
        System.out.println("Pakiet przed dodaniem bitu parzystosci: " + pakiet + ". Rozmiar: " + pakiet.length());

        CoderParityBit coder = new CoderParityBit();
        pakiet = coder.addParityBit(pakiet);
        System.out.println("Pakiet po dodaniu bitu parzystosci: " + pakiet + ". Rozmiar: " + pakiet.length());

        BSC bsc = new BSC();
        String wyslany = bsc.BSCcoding(pakiet, 0.5f);
        System.out.println("Pakiet po przeslaniu kanalem BSC: " + wyslany + ". Rozmiar: " + wyslany.length());

        DecoderParityBit decoder = new DecoderParityBit();
        decoder.decode(wyslany);*/



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


    /*witam z tej strony Mikołaj, porobimy razem projekt?
    Hej tutaj Piotrus, jestem za*/



        // TEST - sprawdzenie dzialania kanalu Gilberta-Elliota

        GilbertElliottModifier GEM = new GilbertElliottModifier();
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
        }


    }
}
