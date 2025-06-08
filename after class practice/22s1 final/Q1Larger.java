package comp1110.exam;

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

        boolean haveNumOutside = haveNumOutside(in, start, end);
        int largestOutsideNum;
        if (!haveNumOutside) {
            return end;
        } else {
            largestOutsideNum = largestOutsideNum(in, start, end);
        }
        return largestOutsideNum;
    }

    public static boolean haveNumOutside(int[] in, int start, int end) {
        boolean haveNumOutside = false;
        for (int i = 0; i < in.length; i++) {
            if (in[i] < start || in[i] > end) {
                haveNumOutside = true;
            }
        }
        return haveNumOutside;
    }

    public static int largestOutsideNum(int[] in, int start, int end) {
        int largestNum = Integer.MIN_VALUE;
        for (int i = 0; i < in.length; i++) {
            if (in[i] > end || in[i] < start) {
                if (largestNum < in[i]) {
                    largestNum = in[i];
                }
            }
        }
        return largestNum;
    }
}

