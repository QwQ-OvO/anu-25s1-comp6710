package string;

import java.util.HashSet;
import java.util.Set;

public class LongestSubstring {

    /**
     * Given a string s, find the length of the longest substring without duplicate characters.
     * 必须是连续的字符（子串）
     * 不能有重复字符
     * 找最长的这样的子串
     */
    public static int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }

        int maxLength = 0;
        for (int i = 0; i < s.length(); i++) {
            Set<Character> seen = new HashSet<>(); // ← 每次外循环都创建新的Set

            for (int j = i; j < s.length(); j++) {
                char currentChar = s.charAt(j);
                if (seen.contains(currentChar)) {
                    break; // ← 遇到重复就停止，不会继续添加
                }
                seen.add(currentChar);
            }
            // 此时seen里只包含从位置i开始的最长无重复子串
            maxLength = Math.max(seen.size(), maxLength);
        }
        return  maxLength;
    }
}
