package p3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// =============================================================================
// PART 1: TEXT FILE DATA DEFINITION AND PROCESSING
// =============================================================================

/**
 * Purpose: Represents a text file with word analysis capabilities including word counting and frequency analysis.
 * 
 * Signature: Class containing File, ArrayList<String>, ArrayList<ArrayList<String>>, Map<String, Integer>, ArrayList<String> fields.
 * 
 * Examples:
 * - new TextFile(file) -> processes file and builds word analysis data structures
 * - TextFile with "hello world hello" -> word counts: {hello:2, world:1}
 * 
 * Design Strategy: Simple Expression - Immutable analysis of file content with structured data storage.
 * 
 * Effects: Reads file content, builds word frequency maps and maintains file structure.
 */
public class TextFile {

    // =============================================================================
    // DATA FIELDS
    // =============================================================================
    
    /** Stores original file reference for identification purposes */
    private File file;
    /** Stores all lines from the file preserving original structure */
    private ArrayList<String> lines;
    /** Stores words for each line maintaining two-dimensional file structure */
    private ArrayList<ArrayList<String>> wordInLines;
    /** Stores word frequency mapping for efficient lookup */
    private Map<String, Integer> wordCounts;
    /** Stores all words in file order for sequential processing */
    private ArrayList<String> allWords;

    // =============================================================================
    // CONSTRUCTOR AND FILE PROCESSING
    // =============================================================================

    /**
     * DESIGN RECIPE STEP 1: Data Definition
     * TextFile represents a processed text file with word analysis capabilities
     * 
     * DESIGN RECIPE STEP 2: Function Signature and Purpose Statement
     * 
     * Purpose: Constructs a TextFile object by reading and processing specified file content.
     * 
     * Signature: File -> TextFile
     * 
     * Examples:
     * - new TextFile(new File("test.txt")) -> processes test.txt and builds analysis structures
     * - new TextFile(empty_file) -> creates TextFile with empty data structures
     * 
     * Design Strategy: Function Composition - Read file, split words, build frequency maps.
     * 
     * Effects: Reads file from disk, may throw RuntimeException if file access fails.
     * 
     * @param file Text file to process and analyze
     * @throws RuntimeException if file reading fails, wrapping IOException
     */
    public TextFile(File file) {
        // DESIGN RECIPE STEP 4: Function Template
        // - Initialize all data structures
        // - Open file for reading with proper resource management
        // - Process each line: split into words, count frequencies
        // - Handle file I/O exceptions appropriately
        
        // DESIGN RECIPE STEP 5: Function Body
        // Initialize instance variables
        this.file = file;
        this.lines = new ArrayList<>();
        this.wordInLines = new ArrayList<>();
        this.wordCounts = new HashMap<>();
        this.allWords = new ArrayList<>();

        try {
            // Use try-with-resources for automatic resource management
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    // Store original line
                    lines.add(line);

                    // Split line into words using space delimiter
                    String[] wordsArray = line.split(" +");
                    ArrayList<String> lineWords = new ArrayList<>();

                    // Process each word in the line
                    for (String word : wordsArray) {
                        if (!word.isEmpty()) {
                            lineWords.add(word);
                            allWords.add(word);
                            // Update word frequency count
                            wordCounts.put(word, wordCounts.getOrDefault(word, 0) + 1);
                        }
                    }
                    wordInLines.add(lineWords);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + file.getPath(), e);
        }
    }
    // DESIGN RECIPE STEP 6: Testing
    // Tests would verify correct file reading and word processing

    // =============================================================================
    // WORD FREQUENCY ANALYSIS METHODS
    // =============================================================================

    /**
     * Purpose: Returns the k most common words in the file sorted by frequency (descending).
     * 
     * Signature: int -> Word[]
     * 
     * Examples:
     * - getMostCommonWords(3) -> [Word("the", 15), Word("and", 12), Word("of", 10)]
     * - getMostCommonWords(0) -> [] (empty array)
     * - getMostCommonWords(100) when file has 50 unique words -> array of 50 words
     * 
     * Design Strategy: Function Composition - Sort word entries by frequency, take first k elements.
     * 
     * Effects: Pure function with no side effects, returns new Word array.
     * 
     * @param k Number of most frequent words to return (non-negative)
     * @return Array of k most common words, sorted by frequency descending
     */
    public Word[] getMostCommonWords(int k) {
        // DESIGN RECIPE STEP 4: Function Template
        // - Convert word frequency map to list of Word objects
        // - Sort list by frequency in descending order
        // - Take first k elements respecting array bounds
        // - Return as Word array
        
        // DESIGN RECIPE STEP 5: Function Body
        // Handle edge case: k = 0
        if (k <= 0) {
            return new Word[0];
        }

        // Convert map entries to Word objects
        ArrayList<Word> wordList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordCounts.entrySet()) {
            wordList.add(new Word(entry.getKey(), entry.getValue()));
        }

        // Sort by frequency (descending order)
        wordList.sort((w1, w2) -> Integer.compare(w2.getCount(), w1.getCount()));

        // Take first k elements
        int actualK = Math.min(k, wordList.size());
        Word[] result = new Word[actualK];
        for (int i = 0; i < actualK; i++) {
            result[i] = wordList.get(i);
        }

        return result;
    }

    /**
     * Purpose: Returns the k most common words if file has k or more unique words, otherwise all words.
     * 
     * Signature: int -> Word[]
     * 
     * Examples:
     * - mostKthCommonWords(5) with 10 unique words -> array of 5 most common words
     * - mostKthCommonWords(5) with 3 unique words -> array of all 3 words
     * - mostKthCommonWords(0) -> [] (empty array)
     * 
     * Design Strategy: Function Composition - Delegate to getMostCommonWords with appropriate k value.
     * 
     * Effects: Pure function with no side effects, returns new Word array.
     * 
     * @param k Target number of words to return
     * @return Array of most common words, length min(k, total_unique_words)
     */
    public Word[] mostKthCommonWords(int k) {
        // DESIGN RECIPE STEP 4: Function Template
        // - Handle case where k <= 0
        // - Determine actual k based on available unique words
        // - Delegate to getMostCommonWords
        
        // DESIGN RECIPE STEP 5: Function Body
        if (k <= 0) {
            return new Word[0];
        }

        // Return k words if we have at least k unique words, otherwise all words
        int uniqueWordCount = wordCounts.size();
        int actualK = Math.min(k, uniqueWordCount);
        
        return getMostCommonWords(actualK);
    }

    // =============================================================================
    // FILE ACCESS AND UTILITY METHODS
    // =============================================================================

    /**
     * Purpose: Returns the original File object for identification and path access.
     * 
     * Signature: void -> File
     * 
     * Examples:
     * - getFile() -> File object representing the processed file
     * 
     * Design Strategy: Simple Expression - Direct field access.
     * 
     * Effects: Pure function with no side effects, returns File reference.
     * 
     * @return The original File object
     */
    public File getFile() {
        return file;
    }

    /**
     * Purpose: Checks if the file contains the specified word.
     * 
     * Signature: String -> boolean
     * 
     * Examples:
     * - containsWord("hello") -> true if "hello" appears in file
     * - containsWord("nonexistent") -> false if word not found
     * 
     * Design Strategy: Simple Expression - Check map containment.
     * 
     * Effects: Pure function with no side effects, returns boolean.
     * 
     * @param word Word to search for in the file
     * @return true if word exists in file, false otherwise
     */
    public boolean containsWord(String word) {
        return wordCounts.containsKey(word);
    }

    /**
     * Purpose: Returns the frequency count of a specific word in the file.
     * 
     * Signature: String -> int
     * 
     * Examples:
     * - getWordCount("the") -> 15 if "the" appears 15 times
     * - getWordCount("nonexistent") -> 0 if word not found
     * 
     * Design Strategy: Simple Expression - Map lookup with default value.
     * 
     * Effects: Pure function with no side effects, returns integer count.
     * 
     * @param word Word to get count for
     * @return Frequency count of the word (0 if not found)
     */
    public int getWordCount(String word) {
        return wordCounts.getOrDefault(word, 0);
    }

    /**
     * Purpose: Returns the total number of unique words in the file.
     * 
     * Signature: void -> int
     * 
     * Examples:
     * - getUniqueWordCount() -> 150 if file contains 150 distinct words
     * 
     * Design Strategy: Simple Expression - Return map size.
     * 
     * Effects: Pure function with no side effects, returns integer count.
     * 
     * @return Number of unique words in the file
     */
    public int getUniqueWordCount() {
        return wordCounts.size();
    }

    /**
     * Purpose: Returns the total number of words in the file (including duplicates).
     * 
     * Signature: void -> int
     * 
     * Examples:
     * - getTotalWordCount() -> 500 if file contains 500 total words
     * 
     * Design Strategy: Simple Expression - Return list size.
     * 
     * Effects: Pure function with no side effects, returns integer count.
     * 
     * @return Total number of words in the file
     */
    public int getTotalWordCount() {
        return allWords.size();
    }
}
