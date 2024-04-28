public class Main {

    public static void main(String[] args) {

        RandomNumberND generator = new RandomNumberND();
        String ciag;
        ciag = generator.generateArray(0, 100, 200);
        System.out.println(ciag);

        //aktywuj do sprawdzenia BSC (Kanał binarnie symetryczny) - Nicklas Lipińskas

        //test
//        String[] tab = new String[5];
//        BSC bsc = new BSC();

//        tab[0] = "01010101";
//        tab[1] = "01111111";
//        tab[2] = "10100000";
//        tab[3] = "01010000";
//        tab[4] = "01100001";
//        for(int i = 0;i< tab.length; i++){
//            System.out.println(tab[i]);
//        }

//        System.out.println();
//
//        tab = bsc.BSCtabcoding(tab,0.1F);
//        for(int i = 0;i< tab.length; i++){
//            System.out.println(tab[i]);
//        }
//        ciag = bsc.BSCcoding(ciag,0.1F);
//        System.out.println(ciag);
    }
    //witam z tej strony Mikołaj, porobimy razem projekt?
    //Hej tutaj Piotrus, jestem za
}
