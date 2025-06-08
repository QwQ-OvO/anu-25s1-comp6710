package comp1110.exam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class Q4BadLength {
    /**
     * Outputs sections of text that were marked with the wrong length.
     *
     * A mark consists of a pair of characters, a ':' followed by a single digit that indicates
     * (sometimes incorrectly) the number of characters that follow it until the next mark or the
     * end of the file. This method identifies any incorrect marks and outputs the associated
     * incorrectly marked sections of text.
     *
     * The `increment` argument is a zero or positive integer which increases the effective value of
     * every marker value.
     *
     * E.g., for an input file with contents ":2blue:5green:2red", there are three sections of text
     * "blue", "green" and "red" which are marked with character lengths 2, 5, and 2 respectively.
     * If `increment` is 0, these are also the marked values after applying the increment.
     * The actual character lengths of these text sections are 4, 5 and 3 respectively. Therefore
     * only "green" is correctly marked, and so "bluered" will be output to the output file.
     *
     * For the above example but with an `increment` of 1, the marked character lengths are
     * increased by 1 and become 3, 6 and 3 respectively. For this increment only "red" has the
     * correct length, and so "bluegreen" is output.
     *
     * Some further examples:
     *
     * input ":2blue:5green:2red" and increment 2
     * output "greenred"
     *
     * input ":3why:4this:3bed:3is:9my:3bed" and increment 0
     * output "ismy"
     *
     * @param inFile input filename
     * @param outFile output filename
     * @param increment value to increase marker values by, can be zero or positive
     */
    public static void badLengths(String inFile, String outFile, int increment) {
        try (var reader = new BufferedReader(new FileReader(inFile));
        var writer = new BufferedWriter(new FileWriter(outFile))) {

            StringBuilder allContent = new StringBuilder();
            StringBuilder badLengthWord = new StringBuilder();
            for (String line = reader.readLine(); line != null ; line = reader.readLine()) {
                allContent.append(line);
            }

            String content = allContent.toString();
            String[] contents = content.split(":");

            for (String s : contents) {
                if (s.isEmpty()) {
                    continue;
                }
                int count = Character.getNumericValue(s.charAt(0));
                int len = s.length() - 1;
                if (len != count + increment){
                    String test = s.substring(1);
                    badLengthWord.append(test);
                }
            }
            writer.write(badLengthWord.toString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

