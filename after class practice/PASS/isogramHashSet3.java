package PASS;

import java.util.HashSet;
import java.util.Set;

public class isogramHashSet3 {
    public static boolean isIsogram(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        Set<Character> seenChar = new HashSet<>();
        for (char c : str.toCharArray()) {
            if (seenChar.contains(c)) {
                return false;
            }
            seenChar.add(c);
        }
        return true;
    }

    public static void main(String[] args) {
        // 测试用例
        System.out.println(isIsogram("aaa"));      // false
        System.out.println(isIsogram("abc"));      // true
        System.out.println(isIsogram("Abc"));      // true (大小写不同)
        System.out.println(isIsogram("ABBA"));     // false
        System.out.println(isIsogram(""));         // true
        System.out.println(isIsogram("abcdefg"));  // true
    }
}
