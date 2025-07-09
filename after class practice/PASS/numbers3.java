package PASS;

import java.util.ArrayList;
import java.util.List;

/**
 * numbers是多个lists的list，
 * 需要单独计算每个list中数字的和sum并找到最大的数字max，
 * 最后输出一个包含每个list的数字sum和max的list
 * (比较每组的max和sum，大的放在后面。)
 * 假设没有空List
 */
public class numbers3 {
    public static List<Integer> analyzeNumbers(List<List<Integer>> numbers) {
        List<Integer> numList = new ArrayList<>();

        for (List<Integer> list : numbers) {
            int sum = 0;
            int max = Integer.MIN_VALUE;

            for (Integer i : list) {
                sum += i;
                if (i > max) {
                    max = i;
                }
            }
            if (sum <= max) {
                numList.add(sum);
                numList.add(max);
            } else {
                numList.add(max);
                numList.add(sum);
            }
        }
        return numList;
    }
}
