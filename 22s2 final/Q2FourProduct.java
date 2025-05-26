public class Q2FourProduct {

    /**
     * Input to the method is a string.
     *
     * Return the sum of the even-valued digits if the string represents a
     * FourProduct number.
     * Return the sum of the odd-valued digits if the string represents a
     * number that is not a FourProduct number.
     *
     * In any other case (for example, if the input string empty or does
     * not represent a number) return -1.
     *
     * A FourProduct number is defined recursively by the following rules:
     * a) is a non-negative integer with an even number of digits (strings
     * with leading zeros are equivalent to their integer value, e.g. 00044
     * equivalent to 44);
     * b) the product of its first and last digits are divisible by four;
     * c) after removing the first and last digits, the remaining digits are
     * still a FourProduct number, or an empty string
     *
     * Examples of FourProduct numbers: "1414", "2222", "44", "8221", "88",
     * "84", "1540". As these are FourProduct numbers this method should
     * return 8, 8, 8, 12, 16, 12, 4, respectively.
     *
     * Examples that are NOT FourProduct numbers: "444", "1", "3453",
     * "1234". As these aren't FourProduct numbers this method should return
     * 0, 1, 11, 4, respectively.
     *
     * @param number A string
     * @return The sum of the even-valued digits if the string represents
     * a FourProduct number, the sum of the odd-valued digits if the string
     * represents a number that is not a FourProduct number, and -1 in any
     * other case.
     */

    public static int fourProductChecker(String number) {
        // Check if input is null or empty
        if (number == null || number.isEmpty()) {
            return -1;
        }
        
        // Check if string contains only digits
        for (int i = 0; i < number.length(); i++) {
            char c = number.charAt(i);
            if (c < '0' || c > '9') {
                return -1;
            }
        }
        
        // Remove leading zeros
        String trimmed = removeLeadingZeros(number);
        
        // If after removing leading zeros we get empty string, it means the input was "000..."
        // which represents 0, but 0 has odd number of digits (1), so it's not a FourProduct number
        if (trimmed.isEmpty()) {
            trimmed = "0";
        }
        
        // Check if it's a FourProduct number
        boolean isFourProduct = isFourProduct(trimmed);
        
        // Calculate sum of even or odd digits based on whether it's a FourProduct number
        // Note: we use the original trimmed string for digit sum calculation
        int sum = 0;
        for (int i = 0; i < trimmed.length(); i++) {
            int digit = trimmed.charAt(i) - '0';
            if (isFourProduct) {
                // Sum even-valued digits
                if (digit % 2 == 0) {
                    sum += digit;
                }
            } else {
                // Sum odd-valued digits
                if (digit % 2 == 1) {
                    sum += digit;
                }
            }
        }
        
        return sum;
    }
    
    private static String removeLeadingZeros(String number) {
        int i = 0;
        while (i < number.length() && number.charAt(i) == '0') {
            i++;
        }
        return number.substring(i);
    }
    
    private static boolean isFourProduct(String number) {
        // Remove leading zeros at each recursive call
        String trimmed = removeLeadingZeros(number);
        
        // Base case: empty string is considered a FourProduct number
        if (trimmed.isEmpty()) {
            return true;
        }
        
        // Rule a: must have even number of digits
        if (trimmed.length() % 2 != 0) {
            return false;
        }
        
        // Rule b: product of first and last digits must be divisible by 4
        int firstDigit = trimmed.charAt(0) - '0';
        int lastDigit = trimmed.charAt(trimmed.length() - 1) - '0';
        int product = firstDigit * lastDigit;
        
        if (product % 4 != 0) {
            return false;
        }
        
        // Rule c: after removing first and last digits, remaining must be FourProduct or empty
        if (trimmed.length() == 2) {
            // If only 2 digits, removing first and last gives empty string, which is valid
            return true;
        } else {
            // Recursively check the middle part
            String middle = trimmed.substring(1, trimmed.length() - 1);
            return isFourProduct(middle);
        }
    }

}