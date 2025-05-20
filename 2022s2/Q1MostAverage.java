
public class Q1MostAverage {

    /**
     * Find the number in an array of integers that is closest to
     * the average of all numbers in the array ("closest" means having
     * the smallest absolute difference to the average).
     *
     * For example, if the array is {6, 11, 7}, the average is 8.0,
     * so the most average number in the array is 7.
     *
     * If the array is empty, the method should return 0.
     */
    public static int mostAverage(int[] numbers) {
        // FIXME: implement this method
        if (numbers == null || numbers.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
        }
        double average = (double) sum / numbers.length;

        int closestNum = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (Math.abs(average - numbers[i]) < Math.abs(average - closestNum)) {
                closestNum = numbers[i];
            }
        }
        return closestNum;
    }
}
