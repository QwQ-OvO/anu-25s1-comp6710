package args;

public class findClosestNum {

    /**
     * 找出数组中与给定值target最接近的数
     */
    public static int findClosest(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            throw new IllegalArgumentException("Array cannot be empty");
        }

        int closest = nums[0];
        for (int i = 0; i < nums.length; i++) {
            if (Math.abs(nums[i] - target) < Math.abs(closest - target)) {
                closest = nums[i];
            }
        }
        return closest;
    }
}
