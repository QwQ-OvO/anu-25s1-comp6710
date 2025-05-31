package comp1110.exam;

public class Q2ThreeFour {

    /**
     * Given a string, return -1 if it is an empty string or contains any non-digit characters,
     * return the sum of the odd-valued digits if the string represents a ThreeFour number or return
     * the sum of the even-valued digits if the string does not represent a ThreeFour number.
     *
     * A ThreeFour number is defined recursively by the following rules:
     *
     * a) is a non-negative integer (may have one or more leading zeros, e.g., 0033 is equivalent to 33),
     * b) is divisible by three or four,
     * c) after removing the first and last digits, the remaining digits are still a ThreeFour
     *    number, or an empty string,
     *
     * Examples of ThreeFour numbers: "3333", "2931", "1240", "4", "630", "1060", "0"
     *
     * As these are ThreeFour numbers this method would return 12, 13, 1, 0, 3, 1, 0 respectively
     *
     * Examples that are NOT ThreeFour numbers: "2401", "11", "1", "724"
     *
     * As these aren't ThreeFour numbers this method would return 6, 0, 0, 6 respectively
     *
     * @param number A number
     * @return -1 if it is an empty string or contains any non-digit characters, the sum of
     * the odd-valued digits if this is a ThreeFour number, the sum of the even-valued digits if it
     * is not a ThreeFour number.
     */
    public static int threeFour(String number) {
        if (number.isEmpty() || number.matches(".*\\D.*")) {
            return -1;
        }

//        // 替代正则判断contains any non-digit characters的方法
//        for (char c : number.toCharArray()) {
//            if (!Character.isDigit(c)){
//                return -1;
//            }
//        }

        boolean isThreeFourNumber = isThreeFourNumber(number);

        int sumDigit = 0;

        if (isThreeFourNumber) {
            sumDigit = sumOddDigit(number);
        }
        if (!isThreeFourNumber) {
            sumDigit = sumEvenDigit(number);
        }
        return sumDigit;
    }

    public static boolean isThreeFourNumber(String number) {
        if (number.isEmpty() ) {
            return true;
        }
        int num = Integer.parseInt(number);

        if (num % 3 != 0 && num % 4 != 0) {
        return false;
        }

        // 如果只有一位数字，并且可以被3或4整除，那么是ThreeFour数字
        if (number.length() == 1) {
            return true;
        }

        // 直接使用 number.substring(1, number.length() - 1) 来获取子字符串，
        // 这会保留中间可能的前导零
        String innerNum = number.substring(1, number.length() - 1);
        return isThreeFourNumber(innerNum);
    }

    public static int sumOddDigit(String number) {

        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);
            int digit = Character.getNumericValue(c);
            if (digit % 2 != 0) {
                sum += digit;
            }
        }
        return sum;
    }

    public static int sumEvenDigit(String number) {
        int sum = 0;
        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);
            int digit = Character.getNumericValue(c);
            if (digit % 2 == 0) {
                sum += digit;
            }
        }
        return sum;
    }
}



