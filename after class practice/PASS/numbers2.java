package PASS;

import java.util.ArrayList;
import java.util.List;

/**
 * numbers是多个lists的list，
 * 需要单独计算每个list中数字的和sum并找到最大的数字max，
 * 最后输出一个包含每个list的数字sum和max的list
 * (比较每组的max和sum，大的放在后面。)
 * 不考虑null
 */
public class numbers2 {
    public static List<Integer> orderNumbers(List<List<Integer>> numbers) {
        List<Integer> orderNum = new ArrayList<>();

        for (List<Integer> list  : numbers) {
            int sum = 0;
            int max = Integer.MIN_VALUE;

            for (int num : list) {
                sum += num;
                if (num > max) {
                    max = num;
                }

            }
            if (sum >= max) {
                orderNum.add(max);
                orderNum.add(sum);
            } else {
                orderNum.add(sum);
                orderNum.add(max);
            }
        }
        return orderNum;
    }
}
