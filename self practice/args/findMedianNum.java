package args;

import java.util.Arrays;

public class findMedianNum {
    /**
     * 找出数组中的中位数（需要先排序）
     */
    public static double findMedian(int[] nums) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("The given array is empty");
        }

        // 数组排序
        Arrays.sort(nums);

        /**
         * 对于偶数长度的数组，中位数是中间两个元素的平均值。
         * 这两个中间元素的索引应该是：
         * 左中间元素：nums.length / 2 - 1
         * 右中间元素：nums.length / 2
         */
        if (nums.length % 2 == 0) {
            return (nums[nums.length / 2 - 1] + nums[nums.length / 2]) / 2.0;
        } else {
            return (nums[nums.length / 2]);
        }
    }
}
