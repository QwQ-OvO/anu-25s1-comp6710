package ws9a;

import java.util.Arrays;

public class MinDiff {


    private record Num(int value, int[] origin) {
    }

    public static void main(String[] args) {
        int[] myArray = new int[]{1,10,6,2003};
        minDifference(myArray, myArray);
    }


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
