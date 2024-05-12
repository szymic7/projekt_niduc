package communication_chanels;

import java.util.Random;
import java.util.*;
public class BSC {

    public BSC() {}

    public String BSCcoding(String tekst, float pError) { // dla Stringa - pojedynczego pakietu

        // Deklaracja Random za kazdym wywolaniem metody, aby uzywac jednego obiektu klasy BSC do przesylania pakietow
        Random random = new Random();
        String zakodowany = ""; // String do zwrocenia

        for(int i = 0; i < tekst.length(); i++) {

            if(random.nextFloat() < pError) {                   // Bledne przeslanie bitu
                if(tekst.charAt(i) == '0') zakodowany += '1';   // Jesli bit = 0 - przesylamy 1
                else zakodowany += '0';                         // A jesli bit = 1 - przesylamy 0
            } else {                                            // Poprawne przeslanie bitu
                zakodowany += tekst.charAt(i);                  // Przesylamy niezmieniony bit
            }

        }

        return zakodowany;  // Zwrocenie zakodowanego pakietu
    }

    public String[] BSCtabcoding(String[] tab, float pError) { // dla tablicy Stringow - ciagu pakietow

        String[] result = new String[tab.length];

        for(int i = 0;i < tab.length; i++) {
            result[i] = BSCcoding(tab[i], pError); // Kodowanie dla kazdego kolejnego pakietu w tablicy
        }

        return result;
    }

}
