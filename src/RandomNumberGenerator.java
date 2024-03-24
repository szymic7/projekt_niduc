import java.util.Random;

public class RandomNumberGenerator {

    public RandomNumberGenerator() {}

    public int[] generateNumber(int zakres, int ileLiczb) {
        int[] liczby = new int[ileLiczb];
        Random random = new Random();
        for(int i = 0; i < ileLiczb; i++) {
            liczby[i] = random.nextInt(zakres + 1);
        }
        return liczby;
    }

}
