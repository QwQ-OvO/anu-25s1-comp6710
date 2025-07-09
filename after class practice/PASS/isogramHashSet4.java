package PASS;

import java.util.HashSet;
import java.util.Set;

/**
 * 判断一串字符串 str 是否是 isogram。
 * 即字符串中不能有重复的字母(大小写也有区别）
 * 例如输入 aaa 则为false，输入abc，则为true
 */
public class isogramHashSet4 {
    public static boolean isIsogram(String str) {
        if (str == null || str.isEmpty()) {
            return  true;
        }
        Set<Character> seenChar = new HashSet<>();
        for (char s : str.toCharArray()) {
            if (seenChar.contains(s)) {
                return false;
            }
            seenChar.add(s);
        }
        return true;
    }
}
