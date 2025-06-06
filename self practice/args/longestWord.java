package args;

public class longestWord {

    public static String findLongestWord(String[] words) {
       if (words == null || words.length == 0) {
           return "";
       }

        String longest = "";
        for (String word : words) {

            if (word.length() > longest.length()) {
                longest = word;
            }
        }
        return longest;
    }
}
