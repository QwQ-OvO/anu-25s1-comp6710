package comp1110.exam;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashSet;
import java.util.Set;

public class Q4Occurrences {
    /**
     * Outputs first occurrences of "tagged" text.
     * <p>
     * A tag consists of a pair of characters, a ':' followed by a character identifying the tag
     * (in these examples this second character is always a single digit).
     * <p>
     * A tag "tags" the text immediately following it, up until either the start of the next tag
     * or the end of the file, whichever comes first. E.g., ":2blue:3green:2red", has three tagged
     * sections of text "blue", "green" and "red" which have the tags '2', '3' and '2' respectively.
     * <p>
     * This method outputs the text that matches the provided `tag`, ensuring that each output
     * tagged section of text is unique. I.e. a tagged section of text will only be output the first
     * time it appears.
     * <p>
     * For example:
     * <p>
     * input ":2blue:3green:2red" and tag character '3'
     * output "green"
     * <p>
     * input ":2blue:3green:2red" and tag character '2'
     * output "bluered"
     * <p>
     * input ":3why:4this:3bed:3is:9my:3bed" and tag character '3'
     * output "whybedis"
     * <p>
     * Noting in this last example bed only appears once in the output, the first time it occurred
     * in the input file.
     *
     * @param inFile  input filename
     * @param outFile output filename
     * @param tag     second character of tag to consider
     */
    public static void occurrences(String inFile, String outFile, char tag) {
        try (var reader = new BufferedReader(new FileReader(inFile));
             var writer = new BufferedWriter(new FileWriter(outFile))) {

            StringBuilder allContent = new StringBuilder();
            Set<String> haveRecorded = new HashSet<>();
            StringBuilder result = new StringBuilder();

            //readLine()每次调用都会读取下一行，并且移动内部指针
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                allContent.append(line);
            }
            String content = allContent.toString();
            String[] splitWords = content.split(":");
            for (String splitWord : splitWords) {
                if (splitWord.isEmpty()) {
                    continue;
                }

                if (tag == splitWord.charAt(0)) {
                    String word = splitWord.substring(1);
                    if (!haveRecorded.contains(word)) {
                        haveRecorded.add(word);
                        result.append(word);
                    }
                }
            }
            writer.write(result.toString());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
