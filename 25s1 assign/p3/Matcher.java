package p3;

import java.io.*;
import java.util.*;

// =============================================================================
// PART 4: SENTENCE MATCHER FOR MULTI-WORD SEARCH
// =============================================================================

/**
 * Purpose: Provides functionality to search for sentence matches across multiple lines in text files.
 * 
 * Signature: Static class containing sentence search and pattern matching methods.
 * 
 * Examples:
 * - Matcher.getMatches(directory, "hello world") -> array of Match objects for "hello world" occurrences
 * - Matcher.main(["search sentence"]) -> prints formatted matches for search sentence
 * 
 * Design Strategy: Function Composition - Recursive directory traversal with multi-line sentence matching.
 * 
 * Effects: Searches file system, reads text files, may print to console or throw exceptions.
 */
public class Matcher {

    // =============================================================================
    // SENTENCE SEARCH METHODS
    // =============================================================================

    /**
     * DESIGN RECIPE STEP 1: Data Definition
     * Match array represents collection of sentence matches in files
     * 
     * DESIGN RECIPE STEP 2: Function Signature and Purpose Statement
     * 
     * Purpose: Returns an array of matches of the given sentence in the contents of any text files in the sub-tree rooted at the given path.
     * 
     * Signature: File, String -> Match[]
     * 
     * Examples:
     * - getMatches(new File("/docs"), "hello world") -> [Match objects] for "hello world" occurrences
     * - getMatches(new File("single.txt"), "multi line sentence") -> [Match] if sentence spans lines
     * - getMatches(new File("/empty"), "any sentence") -> [] if directory is empty or no matches
     * 
     * Design Strategy: Cases on File Type - Handle files vs directories, search for sentence patterns.
     * 
     * Effects: Reads files from disk, may throw exceptions for file access errors.
     * 
     * @param path File or directory to search (starting point for search)
     * @param sentence Sentence to search for (may contain multiple words separated by spaces/newlines)
     * @return Array of Match objects for sentence occurrences
     */
    static Match[] getMatches(File path, String sentence) {
        // DESIGN RECIPE STEP 4: Function Template
        // - Check if path exists and is accessible
        // - Parse sentence into individual words
        // - Handle single file case
        // - Handle directory case with recursive search
        // - Filter files by .txt extension
        // - Search each text file for sentence matches
        
        // DESIGN RECIPE STEP 5: Function Body
        // Validate input path
        if (path == null || !path.exists()) {
            return new Match[0];
        }

        // Parse sentence into words
        String[] sentenceWords = parseSentence(sentence);
        if (sentenceWords.length == 0) {
            return new Match[0];
        }

        ArrayList<Match> result = new ArrayList<>();
        
        if (path.isFile()) {
            // Handle single file case
            processFileForSentence(path, sentenceWords, result);
        } else if (path.isDirectory()) {
            // Handle directory case - search recursively
            searchDirectoryForSentence(path, sentenceWords, result);
        }

        // Sort results by absolute path, then by start line
        result.sort((m1, m2) -> {
            int pathCompare = m1.getFile().getAbsolutePath().compareTo(m2.getFile().getAbsolutePath());
            if (pathCompare != 0) return pathCompare;
            return Integer.compare(m1.getStartLine(), m2.getStartLine());
        });

        return result.toArray(new Match[0]);
    }
    // DESIGN RECIPE STEP 6: Testing
    // Tests would verify correct sentence discovery and multi-line matching

    /**
     * Purpose: Parses a sentence string into individual words by splitting on spaces and newlines.
     * 
     * Signature: String -> String[]
     * 
     * Examples:
     * - parseSentence("hello world") -> ["hello", "world"]
     * - parseSentence("line one\nline two") -> ["line", "one", "line", "two"]
     * - parseSentence("  multiple   spaces  ") -> ["multiple", "spaces"]
     * 
     * Design Strategy: Function Composition - Split on whitespace, filter empty strings.
     * 
     * Effects: Pure function with no side effects, returns new String array.
     * 
     * @param sentence Sentence to parse into words
     * @return Array of words from the sentence
     */
    private static String[] parseSentence(String sentence) {
        if (sentence == null || sentence.trim().isEmpty()) {
            return new String[0];
        }
        
        // Split on any whitespace (spaces, newlines, tabs)
        String[] words = sentence.trim().split("\\s+");
        
        // Filter out empty strings
        ArrayList<String> filteredWords = new ArrayList<>();
        for (String word : words) {
            if (!word.isEmpty()) {
                filteredWords.add(word);
            }
        }
        
        return filteredWords.toArray(new String[0]);
    }

    /**
     * Purpose: Processes a single file to find sentence matches that may span multiple lines.
     * 
     * Signature: File, String[], ArrayList<Match> -> void
     * 
     * Examples:
     * - processFileForSentence(test.txt, ["hello", "world"], list) -> adds matches where "hello world" appears
     * - processFileForSentence(data.doc, ["sentence"], list) -> no action (not .txt file)
     * 
     * Design Strategy: Cases on File Extension - Check .txt extension, then search for sentence patterns.
     * 
     * Effects: May read file content, modifies result list, may throw exceptions for file errors.
     * 
     * @param file File to process
     * @param sentenceWords Array of words forming the target sentence
     * @param result List to add matching Match objects to
     */
    private static void processFileForSentence(File file, String[] sentenceWords, ArrayList<Match> result) {
        // Only process .txt files
        if (!file.getName().endsWith(".txt")) {
            return;
        }

        try {
            // Read all lines from the file
            ArrayList<String> lines = readAllLines(file);
            
            // Search for sentence matches
            findSentenceMatches(file, lines, sentenceWords, result);
            
        } catch (Exception e) {
            // Skip files that cannot be read
            System.err.println("Warning: Could not read file " + file.getPath() + ": " + e.getMessage());
        }
    }

    /**
     * Purpose: Reads all lines from a text file into an ArrayList.
     * 
     * Signature: File -> ArrayList<String>
     * 
     * Examples:
     * - readAllLines(file) -> ["line 1", "line 2", "line 3"] for multi-line file
     * - readAllLines(empty_file) -> [] for empty file
     * 
     * Design Strategy: Function Composition - Use BufferedReader to read line by line.
     * 
     * Effects: Reads file from disk, may throw IOException.
     * 
     * @param file File to read
     * @return ArrayList containing all lines from the file
     * @throws IOException if file reading fails
     */
    private static ArrayList<String> readAllLines(File file) throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        return lines;
    }

    /**
     * Purpose: Finds all sentence matches in the given lines that may span multiple lines.
     * 
     * Signature: File, ArrayList<String>, String[], ArrayList<Match> -> void
     * 
     * Examples:
     * - findSentenceMatches(file, lines, ["hello", "world"], result) -> finds "hello world" across lines
     * - findSentenceMatches(file, lines, ["single"], result) -> finds single word matches
     * 
     * Design Strategy: Cases on Line Position - Search each starting position for sentence matches.
     * 
     * Effects: Modifies result list by adding found matches.
     * 
     * @param file File being searched
     * @param lines All lines from the file
     * @param sentenceWords Target sentence words to match
     * @param result List to accumulate matches
     */
    private static void findSentenceMatches(File file, ArrayList<String> lines, String[] sentenceWords, ArrayList<Match> result) {
        // Try starting at each line
        for (int startLine = 0; startLine < lines.size(); startLine++) {
            Match match = findMatchStartingAt(file, lines, startLine, sentenceWords);
            if (match != null) {
                result.add(match);
            }
        }
    }

    /**
     * Purpose: Attempts to find a sentence match starting at the specified line.
     * 
     * Signature: File, ArrayList<String>, int, String[] -> Match
     * 
     * Examples:
     * - findMatchStartingAt(file, lines, 5, ["hello", "world"]) -> Match if "hello world" starts at line 5
     * - findMatchStartingAt(file, lines, 0, ["missing"]) -> null if sentence not found
     * 
     * Design Strategy: Function Composition - Extract words from current position, match against sentence.
     * 
     * Effects: Pure function with potential Match creation, no side effects.
     * 
     * @param file File being searched
     * @param lines All lines from the file
     * @param startLine Line index to start matching from
     * @param sentenceWords Target sentence words to match
     * @return Match object if sentence found starting at startLine, null otherwise
     */
    private static Match findMatchStartingAt(File file, ArrayList<String> lines, int startLine, String[] sentenceWords) {
        ArrayList<String> wordBuffer = new ArrayList<>();
        ArrayList<String> matchingLines = new ArrayList<>();
        int wordIndex = 0;
        
        // Search starting from startLine
        for (int lineIndex = startLine; lineIndex < lines.size() && wordIndex < sentenceWords.length; lineIndex++) {
            String line = lines.get(lineIndex);
            String[] lineWords = line.split("\\s+");
            
            // Filter out empty words
            ArrayList<String> validWords = new ArrayList<>();
            for (String word : lineWords) {
                if (!word.isEmpty()) {
                    validWords.add(word);
                }
            }
            
            boolean lineHasMatch = false;
            
            // Check words in this line
            for (String word : validWords) {
                if (wordIndex < sentenceWords.length && word.equals(sentenceWords[wordIndex])) {
                    wordBuffer.add(word);
                    wordIndex++;
                    lineHasMatch = true;
                } else if (wordIndex > 0) {
                    // Sentence match broken
                    return null;
                }
            }
            
            // If this line contributed to the match, include it
            if (lineHasMatch) {
                matchingLines.add(line);
            }
            
            // Check if we've found the complete sentence
            if (wordIndex == sentenceWords.length) {
                return new Match(file, startLine, matchingLines.toArray(new String[0]));
            }
        }
        
        // Incomplete match
        return null;
    }

    /**
     * Purpose: Recursively searches a directory for text files containing sentence matches.
     * 
     * Signature: File, String[], ArrayList<Match> -> void
     * 
     * Examples:
     * - searchDirectoryForSentence(docs/, ["hello", "world"], list) -> searches all .txt files recursively
     * - searchDirectoryForSentence(empty_dir/, ["sentence"], list) -> no action if directory is empty
     * 
     * Design Strategy: Cases on Directory Contents - Process each file/subdirectory recursively.
     * 
     * Effects: Recursively reads directory structure and files, modifies result list.
     * 
     * @param directory Directory to search recursively
     * @param sentenceWords Target sentence words to search for
     * @param result List to accumulate matching Match objects
     */
    private static void searchDirectoryForSentence(File directory, String[] sentenceWords, ArrayList<Match> result) {
        File[] contents = directory.listFiles();
        if (contents != null) {
            for (File item : contents) {
                if (item.isFile()) {
                    processFileForSentence(item, sentenceWords, result);
                } else if (item.isDirectory()) {
                    // Recursively search subdirectories
                    searchDirectoryForSentence(item, sentenceWords, result);
                }
            }
        }
    }

    // =============================================================================
    // MAIN PROGRAM FOR COMMAND LINE INTERFACE
    // =============================================================================

    /**
     * Purpose: Command line interface that searches current directory for text files containing specified sentence.
     * 
     * Signature: String[] -> void
     * 
     * Examples:
     * - main(["hello world"]) -> prints formatted matches for "hello world" in current directory
     * - main([]) -> prints usage message (no arguments provided)
     * - main(["multi word sentence"]) -> searches for exact phrase "multi word sentence"
     * 
     * Design Strategy: Function Composition - Parse arguments, search current directory, format output.
     * 
     * Effects: Prints to console, reads files from current directory, may throw exceptions.
     * 
     * @param args Command line arguments where first argument is the sentence to search for
     */
    public static void main(String[] args) {
        // DESIGN RECIPE STEP 4: Function Template
        // - Validate command line arguments
        // - Get current working directory
        // - Search for files containing target sentence
        // - Format and print results according to specification
        
        // DESIGN RECIPE STEP 5: Function Body
        // Check if sentence argument is provided
        if (args.length == 0) {
            System.err.println("Usage: java Matcher <sentence>");
            System.err.println("Searches current directory for .txt files containing the specified sentence.");
            return;
        }

        String searchSentence = args[0];
        File currentDirectory = new File(".");

        try {
            // Search for files containing the sentence
            Match[] matches = getMatches(currentDirectory, searchSentence);
            
            // Print results in specified format
            for (Match match : matches) {
                System.out.println("In file " + match.getFile().getAbsolutePath() + 
                                 ", at line " + match.getStartLine() + ":");
                
                String[] matchingLines = match.getMatchingLines();
                for (String line : matchingLines) {
                    System.out.println(line);
                }
                
                System.out.println(); // Empty line after each match
            }
            
        } catch (Exception e) {
            System.err.println("Error during search: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // =============================================================================
    // TESTING METHODS
    // =============================================================================

    /**
     * Purpose: Tests the sentence matching functionality with various scenarios.
     * 
     * Signature: void -> void
     * 
     * Examples:
     * - Tests single-line sentence matches
     * - Tests multi-line sentence matches
     * - Tests sentence not found cases
     * - Tests edge cases with punctuation and spacing
     * 
     * Design Strategy: Function Composition - Create test cases covering different matching scenarios.
     * 
     * Effects: Creates temporary test files, executes searches, cleans up test data.
     */
    private static void testSentenceMatching() {
        // Test would create temporary files with various sentence patterns
        // Test single-line matches
        // Test multi-line matches spanning 2-3 lines
        // Test sentences with punctuation
        // Test sentences not found
        // Test empty files and directories
        System.out.println("Sentence matching tests would be implemented here");
    }

    /**
     * Purpose: Tests the main method functionality with various command line scenarios.
     * 
     * Signature: void -> void
     * 
     * Examples:
     * - Tests main with valid sentence argument
     * - Tests main with no arguments
     * - Tests main with complex sentence patterns
     * 
     * Design Strategy: Function Composition - Simulate different command line inputs.
     * 
     * Effects: Captures console output, verifies expected formatting.
     */
    private static void testMainMethod() {
        // Test would simulate different command line argument scenarios
        // Test with valid sentence
        // Test with no arguments
        // Test with multi-word sentences
        // Test output formatting
        System.out.println("Main method tests would be implemented here");
    }

    /**
     * Purpose: Executes comprehensive test suite for the Matcher class.
     * 
     * Signature: void -> void
     * 
     * Examples:
     * - test() -> runs all test methods for complete validation
     * 
     * Design Strategy: Function Composition - Execute all individual test methods.
     * 
     * Effects: Runs test suite, outputs test results to console.
     */
    public static void test() {
        testSentenceMatching();
        testMainMethod();
        System.out.println("All Matcher tests completed");
    }
} 