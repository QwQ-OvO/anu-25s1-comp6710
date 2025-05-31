package comp1110.exam;

public class Q1Multiplier {
    /**
     * Find the smallest element in the array that has `factor` as a factor.
     *
     * In other words, find the smallest array element e such that there exists some integer
     * multiplier m where:  e = m ✕ `factor`.
     *
     * If `factor` is not a factor for any array elements, return -1;
     *
     * E.g., for the array [15, 4, 10, 2, 24], the smallest element that has 5 as a factor is 10.
     * For the array [15, 4, 10, 2, 24], no element has 7 as a factor, so -1 is returned.
     *
     * @param array non-zero positive integers
     * @param factor non-zero positive integer
     * @return the smallest array element that has `factor` as a factor
     */
    public static int smallProduct(int[] array, int factor) {
        int num = Integer.MAX_VALUE;
        for (int i = 0; i < array.length; i++) {
            if (array[i] % factor == 0 && array[i] < num) {
                num = array[i];
            }
        }
        if (num == Integer.MAX_VALUE) {
            num = -1;
        }
        return num;
    }
}

