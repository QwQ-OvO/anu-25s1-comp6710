package ws9a;

import java.util.Arrays;

/**
 * Utility class that provides a method to calculate
 * the minimum difference between elements from two arrays
 */
public class MinDiff {

    /**
     * Represents an integer that tracks its origin
     * to some array
     * Examples:
     * - Num(4,[1,2,3,4])
     * - Num(-13,[-13])
     * @param value an integer value
     * @param origin the array that the integer came from
     */
    private record Num(int value, int[] origin) {
    }

    public static void main(String[] args) {
        int[] myArray = new int[]{1,10,6,2003};
        minDifference(myArray, myArray);
    }

    /**
     * Returns the minimum difference between two numbers
     * taken one each from two given arrays
     * Examples:
     * - Given: [6,1,10], [4, 16]
     *   Expect: 2
     * - Given: [5, 10], [100, 70]
     *   Expect: 60
     * Strategy: Iteration
     * @param a1 an array of numbers
     * @param a2 an array of numbers
     * @return the minimum difference between some number
     *         from a1 and some number from a2, or
     *         Integer.MAX_VALUE if at least one of thw
     *         two arrays was empty
     */
    public static int minDifference(int[] a1, int[] a2) {
        a1=Arrays.copyOf(a1, a1.length);
        a2=Arrays.copyOf(a2, a2.length);
        Arrays.sort(a1);
        Arrays.sort(a2);
        Num[] array=createMerge(a1, a2);
        int minDiff=Integer.MAX_VALUE;
        // Termination: array is of finite size, and index always increases
        //     and neither is otherwise changed in the loop body
        for(int index=0;index<array.length-1;index++) {
            if(array[index].origin!=array[index+1].origin) {
                minDiff=Math.min(minDiff, array[index+1].value-array[index].value);
            }
        }
        return minDiff;
    }

    /**
     * Given two sorted arrays of integers, creates a merged
     * array that is still sorted, and whose values track
     * which array they came from
     * Examples:
     * - Given [1,3], [2,4]
     *   Expect: [Num(1,[1,3]), Num(2,[2,4]), Num(3,[1,3]), Num(4,[2,4])]
     * - Given [], [1,2]
     *   Expect: [Num(1,[1,2]), Num(2,[1,2])]
     * Strategy: Iteration
     * @param a1 the first array
     * @param a2 the second array
     * @return the merged array of origin-tracked integers
     */
    private static Num[] createMerge(int[] a1, int[] a2) {
        Num[] array=new Num[a1.length+a2.length];
        int i1=0;
        int i2=0;
        //Termination argument: array length is the sum of the lenghts of a1 and a2,
        // and either i1 or i2 increases in every iteration, each up
        // to the length of a1 and a2, respectively
        while(i1+i2<array.length) {
            if(i1<a1.length) {
                if(i2<a2.length && a2[i2]<a1[i1]) {
                    array[i1+i2]=new Num(a2[i2], a2);
                    i2++;
                } else {
                    array[i1+i2]=new Num(a1[i1], a1);
                    i1++;
                }
            } else {
                array[i1+i2]=new Num(a2[i2], a2);
                i2++;
            }
        }
        return array;
    }
}
