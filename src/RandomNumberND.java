import java.util.Random;

public class RandomNumberND {
    private Random random;

    public RandomNumberND() {
        this.random = new Random();
    }

    public int[] generateArray(int min, int max, int size) {
        int[] array = new int[size];
        double mean = (min + max) / 2.0;
        double stdDev = (max - min) / 6.0; // Assuming 99.7% of data falls within 3 standard deviations

        for (int i = 0; i < size; i += 2) {
            double u1 = random.nextDouble();
            double u2 = random.nextDouble();
            double z1 = Math.sqrt(-2 * Math.log(u1)) * Math.cos(2 * Math.PI * u2);
            double z2 = Math.sqrt(-2 * Math.log(u1)) * Math.sin(2 * Math.PI * u2);

            double x1 = z1 * stdDev + mean;
            double x2 = z2 * stdDev + mean;

            // Check if the generated numbers fall within the specified range
            if (x1 >= min && x1 <= max) {
                array[i] = (int) Math.round(x1);
            } else {
                i--; // Retry generation for this index
            }

            if (i + 1 < size && x2 >= min && x2 <= max) {
                array[i + 1] = (int) Math.round(x2);
            } else if (i + 1 < size) {
                i--; // Retry generation for next index
            }
        }
        return array;
    }

}
