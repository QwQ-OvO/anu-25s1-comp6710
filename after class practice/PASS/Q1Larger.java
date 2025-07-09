package PASS;

public class Q1Larger {
    /**
     * Given an array of integers and a start and end value, return the largest value in the array
     * that is outside the range from start to end inclusive.
     *
     * If the range is invalid, i.e. start is greater than end, return start.
     *
     * If there is no value outside the range, return end.
     *
     * For example:
     *
     * If I have the array of ints: [10,3,2,20], start value 4 and end value 20, this will return 3
     * because 3 is the largest value that is outside the range 4 to 20.
     *
     * Note: start and end are values **not** indices.
     * @param in    an array of integers.
     * @param start the start value of the range (inclusive).
     * @param end   the end value of the range (inclusive).
     * @return the largest value outside the range from start to end, or start if the start is
     * greater than end, or end if there is no such value outside the range.
     */
    public static int findLarger(int[] in, int start, int end) {
        if (start > end) {
            return start;
        }
        int max = Integer.MIN_VALUE;
        for (int i : in) {
            if (i < start || i > end) {
                if ( i > max) {
                    max = i;
                }
            }
        }
        if (max == Integer.MIN_VALUE) {
            return end;
        }
        return max;
    }

    public static void main(String[] args) {
        // 测试1：基本情况 - 示例中的测试
        int[] arr1 = {10, 3, 2, 20};
        int result1 = Q1Larger.findLarger(arr1, 4, 20);
        System.out.println("测试1: " + result1 + " (期望: 3)");

        // 测试2：范围无效，start > end
        int[] arr2 = {1, 2, 3, 4, 5};
        int result2 = Q1Larger.findLarger(arr2, 10, 5);
        System.out.println("测试2: " + result2 + " (期望: 10)");

        // 测试3：没有范围外的值
        int[] arr3 = {5, 6, 7, 8, 9};
        int result3 = Q1Larger.findLarger(arr3, 4, 10);
        System.out.println("测试3: " + result3 + " (期望: 10)");

        // 测试4：空数组
        int[] arr4 = {};
        int result4 = Q1Larger.findLarger(arr4, 1, 5);
        System.out.println("测试4: " + result4 + " (期望: 5)");

        // 测试5：包含负数
        int[] arr5 = {-10, -5, 0, 5, 10};
        int result5 = Q1Larger.findLarger(arr5, -3, 3);
        System.out.println("测试5: " + result5 + " (期望: 10)");

        // 测试6：所有值都小于范围
        int[] arr6 = {1, 2, 3};
        int result6 = Q1Larger.findLarger(arr6, 5, 10);
        System.out.println("测试6: " + result6 + " (期望: 3)");

        // 测试7：所有值都大于范围
        int[] arr7 = {15, 20, 25};
        int result7 = Q1Larger.findLarger(arr7, 1, 10);
        System.out.println("测试7: " + result7 + " (期望: 25)");

        // 测试8：混合情况
        int[] arr8 = {1, 15, 8, 25, 5};
        int result8 = Q1Larger.findLarger(arr8, 6, 20);
        System.out.println("测试8: " + result8 + " (期望: 25)");

        // 测试9：边界值测试
        int[] arr9 = {1, 5, 10, 15};
        int result9 = Q1Larger.findLarger(arr9, 5, 10);
        System.out.println("测试9: " + result9 + " (期望: 15)");

        // 测试10：单个元素数组，在范围外
        int[] arr10 = {100};
        int result10 = Q1Larger.findLarger(arr10, 1, 10);
        System.out.println("测试10: " + result10 + " (期望: 100)");
    }
}

