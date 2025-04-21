package p3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TextFile {

    private File file;
    private ArrayList<String> lines;
    private ArrayList<ArrayList<String>> wordInLines;
    private Map<String, Integer> wordCounts;
    private ArrayList<String> allWords;

    public TextFile(File file) {
        this.file = file;
        this.lines = new ArrayList<>();
        this.wordInLines = new ArrayList<>();
        this.wordCounts = new HashMap<>();
        this.allWords = new ArrayList<>();

        try {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    lines.add(line);

                    String[] wordsArray = line.split(" +");
                    ArrayList<String> lineWords = new ArrayList<>();

                    for (String word : wordsArray) {
                        if (!word.isEmpty()) {
                            lineWords.add(word);
                            allWords.add(word);
                            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                        }
                    }

                    wordInLines.add(lineWords);
                }
            }
        }
        catch (IOException e) {
            throw new RuntimeException("Error reading file: " + file.getPath(), e);
        }
    }

}
