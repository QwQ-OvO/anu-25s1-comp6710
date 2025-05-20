package args;

public class findSingleNum {

    /**
     * 在一个数组中，除了一个数字只出现一次外，其他数字都出现了两次，找出这个只出现一次的数字
     */
    public static int findSingleNumber(int[] nums) {
        /**
         * 初始化为加法的恒等元
         * 任何数与0异或等于其本身，即 a ^ 0 = a
         */
        int result = 0;

        for (int num : nums) {
            result ^= num; // 相当于 result = result ^ num
        }
        return result;
    }
}
