package comp1110.exam;

public class Q2Difference {
    /**
     * Given a string, return -1 if it is an empty string or contains non-digit characters, return
     * the sum of the even digits if the string represents a Difference number, or return the sum
     * of the odd digits if the string does not represent a Difference number.
     *
     * A Difference number is defined recursively by the following rules:
     *
     * a) is a non-negative integer (may have one or more leading zeros, e.g., 0002 is OK),
     * b) the difference between the first and last digits is even,
     * c) after removing the first and last digits, the remaining digits are still a Difference
     *    number, or an empty string,
     * e) a single digit number is a Difference number if it is even.
     *
     * Examples of Difference numbers: "3465", "72821", "6", "95", "1919", "0"
     *
     * As these are Difference numbers this method would return 10, 12, 6, 0, 0, 0 respectively
     *
     * Examples that are NOT Difference numbers: "3", "453", "29", "3477", "7", "96382"
     *
     * As these aren't Difference numbers this method would return 3, 8, 9, 17, 7, 12 respectively
     *
     * @param number A number
     * @return return -1 if it is an empty string or contains non-digit characters, return
     * the sum of the even digits if the string represents a Difference number, or return the sum
     * of the odd digits if the string does not represent a Difference number.
     */
    public static int difference(String number) {
        if (number.isEmpty() || number.matches(".*\\D.*")) {
            return -1;
        }

        boolean isDifferentNum = isDifferentNum(number);

        int sum = 0;
        if (isDifferentNum) {
            sum = sumEvenDigits(number);
        } else {
            sum = sumOddDigits(number);
        }
        return sum;
    }

    public static boolean isDifferentNum(String number) {
        if (number.isEmpty()) {
            return true;
        }

        if (number.length() == 1) {
            int singleNum = Character.getNumericValue(number.charAt(0));
            return singleNum % 2 == 0;
        }

        //Character.getNumericValue 是 Java 中的一个静态方法，用于将字符转换为对应的数值
        //无需先将字符转换为字符串再解析为整数，减少了中间步骤
        int firstNum = Character.getNumericValue(number.charAt(0));
        int lastNum = Character.getNumericValue(number.charAt(number.length() - 1));


        if ((lastNum - firstNum) % 2 != 0) {
            return false;
        }

        return isDifferentNum(number.substring(1, number.length() - 1));
    }

    public static int sumEvenDigits(String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            int digit = Character.getNumericValue(number.charAt(i));
            if (digit % 2 == 0) {
                sum += digit;
            }
        }
        return sum;
    }

    public static int sumOddDigits(String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            int digit = Character.getNumericValue(number.charAt(i));
            if (digit % 2 != 0) {
                sum += digit;
            }
        }
        return sum;
    }
}

