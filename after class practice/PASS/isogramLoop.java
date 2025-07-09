package PASS;

public class isogramLoop {
    public static boolean isIsogram(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        char[] chars = str.toCharArray();

        for (int i = 0; i < chars.length; i++) {
            for (int s = i + 1; s < chars.length; s++) {
                if (chars[i] == chars[s]) {
                    return false;
                }
            }
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
