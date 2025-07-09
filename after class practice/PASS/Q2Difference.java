package PASS;

public class Q2Difference {
    /**
     * Given a string, return -1 if it is an empty string or contains non-digit characters, return
     * the sum of the even digits if the string represents a Difference number, or return the sum
     * of the odd digits if the string does not represent a Difference number.
     * <p>
     * A Difference number is defined recursively by the following rules:
     * <p>
     * a) is a non-negative integer (may have one or more leading zeros, e.g., 0002 is OK),
     * b) the difference between the first and last digits is even,
     * c) after removing the first and last digits, the remaining digits are still a Difference
     * number, or an empty string,
     * e) a single digit number is a Difference number if it is even.
     * <p>
     * Examples of Difference numbers: "3465", "72821", "6", "95", "1919", "0"
     * <p>
     * As these are Difference numbers this method would return 10, 12, 6, 0, 0, 0 respectively
     * <p>
     * Examples that are NOT Difference numbers: "3", "453", "29", "3477", "7", "96382"
     * <p>
     * As these aren't Difference numbers this method would return 3, 8, 9, 17, 7, 12 respectively
     *
     * @param number A number
     * @return return -1 if it is an empty string or contains non-digit characters, return
     * the sum of the even digits if the string represents a Difference number, or return the sum
     * of the odd digits if the string does not represent a Difference number.
     */
    public static int difference(String number) {
        if (number == null || number.isEmpty()) {
            return -1;
        }

        for (char c : number.toCharArray()) {
            if (!Character.isDigit(c)) {
                return -1;
            }
        }

        int even = sumEvenNum(number);
        int odd = sumOddNum(number);
        return (isDiff(number) ? even : odd);
    }

    public static boolean isDiff(String number) {
        // 传入单个数字情况
        if (number.length() == 1) {
            return ((number.charAt(0) - '0') % 2 == 0);
        }

        int first = number.charAt(0) - '0'; // 字符转数字
        int last = number.charAt(number.length() - 1) - '0';
        int difference = last - first;

        if (difference % 2 != 0) {
            return false;
        } else {
            //substring(start, end)的规则是：start是包含的起始索引，end是不包含的结束索引
            String innerNum = number.substring(1, number.length() - 1);
            if (innerNum.isEmpty()) {
                return true;
            }
            if (innerNum.length() == 1) {
                return ((innerNum.charAt(0) - '0') % 2 == 0);
            }
            return isDiff(innerNum);
        }
    }

        public static int sumOddNum (String number){
            int sum = 0;
            for (char c : number.toCharArray()) {
                if ((c - '0') % 2 != 0) {
                    sum += (c - '0');
                }
            }
            return sum;
        }

        public static int sumEvenNum (String number){
            int sum = 0;
            for (char c : number.toCharArray()) {
                if ((c - '0') % 2 == 0) {
                    sum += (c - '0');
                }
            }
            return sum;
        }

}


