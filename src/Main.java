public class Main {

    public static void main(String[] args) {

        RandomNumberGenerator generator = new RandomNumberGenerator();
        int[] tablica = new int[10];
        tablica = generator.generateNumber(100, 10);
        for(int i = 0; i < tablica.length; i++) {
            System.out.print(tablica[i] + ", ");
        }

        System.out.println();

        int[] tablica2 = new int[10];
        tablica2 = generator.generateNumber(100, 10);
        for(int i = 0; i < tablica2.length; i++) {
            System.out.print(tablica2[i] + ", ");
        }

    }

}
