public class Main {

    public static void main(String[] args) {

        RandomNumberND generator = new RandomNumberND();
        int[] tablica = new int[200];
        tablica = generator.generateArray(0, 100, 200);
        for(int i = 0; i < tablica.length; i++) {
            System.out.print(tablica[i] + ", ");
            if((i+1) % 10 == 0) System.out.println();
        }

    }

}
