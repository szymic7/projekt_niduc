import java.util.Random;
import java.util.*;
public class BSC {
    private Random random;          //zadeklarowanie zmiennej random
    public BSC(){
        this.random = new Random();
    }
    public String BSCcoding(String tekst, float p){
        String zakodowany = "";     //przetrzymywanie tekstu zakodowanego
        int randomNumber;           //liczba wylosowana z generatora
        int chance = (int)(p*100);  //szansa w %
        char znak;                  //znak kolejno odczytany z podanego tekstu
        for(int i = 0; i < tekst.length(); i++){
            randomNumber = random.nextInt(100)+1;       //przypisywanie do zmiennej random nowo wylosowane liczby
            if(chance>=randomNumber){                         //warunek jeżeli losowa liczba zawiera się w szansie
                znak = tekst.charAt(i);
                if(znak == '0'){                              //zamiana znaku jako efekt "dobrego" losu
                    zakodowany += '1';
                }
                else{
                    zakodowany += '0';
                }
            }
            else{
                zakodowany += tekst.charAt(i);                //przepisanie znaku
            }
        }
        return zakodowany;           //zwrócenie tekstu zakodowanego
    }
}
