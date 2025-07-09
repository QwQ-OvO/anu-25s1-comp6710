package PASS;

import java.util.HashSet;
import java.util.Set;

/**
 * 判断一串字符串 str 是否是忽略大小写的 isogram。
 * 即字符串中不能有重复的字母（大小写视为相同字母）
 * 例如输入 "Aa" 则为false（A和a视为同一字母），输入 "AbC" 则为true
 */
public class CaseInsensitiveIsogram {
    public static boolean isCaseInsensitiveIsogram(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        Set<Character> seenChar = new HashSet<>();
        for (char c : str.toCharArray()) {
            char lowerChar = Character.toLowerCase(c);
            if (seenChar.contains(lowerChar)) {
                return false;
            }
            seenChar.add(lowerChar);
        }
        return true;
    }

    public static void main(String[] args) {
        // 测试用例
        System.out.println(isCaseInsensitiveIsogram("abc"));     // true
        System.out.println(isCaseInsensitiveIsogram("Abc"));     // true
        System.out.println(isCaseInsensitiveIsogram("Aa"));      // false
        System.out.println(isCaseInsensitiveIsogram("AbC"));     // true
        System.out.println(isCaseInsensitiveIsogram("Hello"));   // false (l重复)
        System.out.println(isCaseInsensitiveIsogram("World"));   // true
        System.out.println(isCaseInsensitiveIsogram(""));        // true
        System.out.println(isCaseInsensitiveIsogram(null));      // true
    }
}
