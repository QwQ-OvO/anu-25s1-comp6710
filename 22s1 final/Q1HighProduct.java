package comp1110.exam;

public class Q1HighProduct {
    /**
     * Find the largest element in the array that has `factor` as a factor.
     *
     * In other words, find the largest array element e such that there exists some integer
     * multiplier m where:  e = m ✕ `factor`.
     *
     * If `factor` is not a factor for any array elements, return -1;
     *
     * E.g., for the array [15, 4, 10, 2, 24], the largest element that has 5 as a factor is 15.
     * For the array [15, 4, 10, 2, 24], no element has 7 as a factor, so -1 is returned.
     *
     * @param array non-zero positive integers
     * @param factor non-zero positive integer
     * @return the largest array element that has `factor` as a factor
     */
    public static int findProduct(int[] array, int factor) {
        int num = -1;
        for (int i = 0; i < array.length; i++) {
            if (array[i] % factor == 0 && array[i] > num) {
                num = array[i];
            }
        }
        return num;
    }
}


