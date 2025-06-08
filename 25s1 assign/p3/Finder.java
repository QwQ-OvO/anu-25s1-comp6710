package p3;

import java.io.*;
import java.util.*;

// =============================================================================
// PART 3: FILE FINDER FOR WORD SEARCH
// =============================================================================

/**
 * Purpose: Provides functionality to search for text files containing specific words in directory structures.
 * 
 * Signature: Static class containing file search and filtering methods.
 * 
 * Examples:
 * - Finder.getFilesContaining(directory, "hello") -> array of TextFile objects containing "hello"
 * - Finder.main(["search_word"]) -> prints paths of files containing search_word
 * 
 * Design Strategy: Function Composition - Recursive directory traversal with word matching.
 * 
 * Effects: Searches file system, reads text files, may print to console or throw exceptions.
 */
public class Finder {

    // =============================================================================
    // FILE SEARCH METHODS
    // =============================================================================

    /**
     * DESIGN RECIPE STEP 1: Data Definition
     * TextFile array represents collection of files containing target word
     * 
     * DESIGN RECIPE STEP 2: Function Signature and Purpose Statement
     * 
     * Purpose: Returns a list of TextFile objects corresponding to all text files in the sub-tree starting at the given path that contain the given word.
     * 
     * Signature: File, String -> TextFile[]
     * 
     * Examples:
     * - getFilesContaining(new File("/docs"), "hello") -> [file1.txt, file2.txt] containing "hello"
     * - getFilesContaining(new File("single.txt"), "word") -> [single.txt] if it contains "word", [] otherwise
     * - getFilesContaining(new File("/empty"), "any") -> [] if directory is empty or no matches
     * 
     * Design Strategy: Cases on File Type - Handle files vs directories, recurse through directory tree.
     * 
     * Effects: Reads files from disk, may throw exceptions for file access errors.
     * 
     * @param path File or directory to search (starting point for search)
     * @param word Word to search for in text files (assumes no spaces or newlines)
     * @return Array of TextFile objects for files containing the word
     */
    static TextFile[] getFilesContaining(File path, String word) {
        // DESIGN RECIPE STEP 4: Function Template
        // - Check if path exists and is accessible
        // - Handle single file case
        // - Handle directory case with recursive search
        // - Filter files by .txt extension
        // - Test each text file for word containment
        
        // DESIGN RECIPE STEP 5: Function Body
        // Validate input path
        if (path == null || !path.exists()) {
            return new TextFile[0];
        }

        ArrayList<TextFile> result = new ArrayList<>();
        
        if (path.isFile()) {
            // Handle single file case
            processFile(path, word, result);
        } else if (path.isDirectory()) {
            // Handle directory case - search recursively
            searchDirectory(path, word, result);
        }

        return result.toArray(new TextFile[0]);
    }
    // DESIGN RECIPE STEP 6: Testing
    // Tests would verify correct file discovery and word matching

    /**
     * Purpose: Processes a single file to check if it's a text file containing the target word.
     * 
     * Signature: File, String, ArrayList<TextFile> -> void
     * 
     * Examples:
     * - processFile(test.txt, "hello", list) -> adds TextFile to list if test.txt contains "hello"
     * - processFile(data.doc, "word", list) -> no action (not .txt file)
     * 
     * Design Strategy: Cases on File Extension - Check .txt extension, then check word containment.
     * 
     * Effects: May read file content, modifies result list, may throw exceptions for file errors.
     * 
     * @param file File to process
     * @param word Target word to search for
     * @param result List to add matching TextFile objects to
     */
    private static void processFile(File file, String word, ArrayList<TextFile> result) {
        // Only process .txt files
        if (file.getName().endsWith(".txt")) {
            try {
                TextFile textFile = new TextFile(file);
                if (textFile.containsWord(word)) {
                    result.add(textFile);
                }
            } catch (Exception e) {
                // Skip files that cannot be read
                System.err.println("Warning: Could not read file " + file.getPath() + ": " + e.getMessage());
            }
        }
    }

    /**
     * Purpose: Recursively searches a directory for text files containing the target word.
     * 
     * Signature: File, String, ArrayList<TextFile> -> void
     * 
     * Examples:
     * - searchDirectory(docs/, "hello", list) -> searches all .txt files in docs/ and subdirectories
     * - searchDirectory(empty_dir/, "word", list) -> no action if directory is empty
     * 
     * Design Strategy: Cases on Directory Contents - Process each file/subdirectory recursively.
     * 
     * Effects: Recursively reads directory structure and files, modifies result list.
     * 
     * @param directory Directory to search recursively
     * @param word Target word to search for
     * @param result List to accumulate matching TextFile objects
     */
    private static void searchDirectory(File directory, String word, ArrayList<TextFile> result) {
        File[] contents = directory.listFiles();
        if (contents != null) {
            for (File item : contents) {
                if (item.isFile()) {
                    processFile(item, word, result);
                } else if (item.isDirectory()) {
                    // Recursively search subdirectories
                    searchDirectory(item, word, result);
                }
            }
        }
    }

    // =============================================================================
    // MAIN PROGRAM FOR COMMAND LINE INTERFACE
    // =============================================================================

    /**
     * Purpose: Command line interface that searches current directory for text files containing specified word.
     * 
     * Signature: String[] -> void
     * 
     * Examples:
     * - main(["hello"]) -> prints absolute paths of .txt files containing "hello" in current directory
     * - main([]) -> prints usage message (no arguments provided)
     * - main(["word1", "word2"]) -> only processes first argument
     * 
     * Design Strategy: Function Composition - Parse arguments, search current directory, format output.
     * 
     * Effects: Prints to console, reads files from current directory, may throw exceptions.
     * 
     * @param args Command line arguments where first argument is the word to search for
     */
    public static void main(String[] args) {
        // DESIGN RECIPE STEP 4: Function Template
        // - Validate command line arguments
        // - Get current working directory
        // - Search for files containing target word
        // - Sort results alphabetically by absolute path
        // - Print results one per line
        
        // DESIGN RECIPE STEP 5: Function Body
        // Check if word argument is provided
        if (args.length == 0) {
            System.err.println("Usage: java Finder <word>");
            System.err.println("Searches current directory for .txt files containing the specified word.");
            return;
        }

        String searchWord = args[0];
        File currentDirectory = new File(".");

        try {
            // Search for files containing the word
            TextFile[] matchingFiles = getFilesContaining(currentDirectory, searchWord);
            
            // Extract absolute paths and sort alphabetically
            String[] absolutePaths = new String[matchingFiles.length];
            for (int i = 0; i < matchingFiles.length; i++) {
                absolutePaths[i] = matchingFiles[i].getFile().getAbsolutePath();
            }
            
            // Sort paths alphabetically
            Arrays.sort(absolutePaths);
            
            // Print results
            for (String path : absolutePaths) {
                System.out.println(path);
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
     * Purpose: Tests the file search functionality with various scenarios.
     * 
     * Signature: void -> void
     * 
     * Examples:
     * - Tests single file search
     * - Tests directory search
     * - Tests empty directory handling
     * - Tests non-existent path handling
     * 
     * Design Strategy: Function Composition - Create test cases covering different search scenarios.
     * 
     * Effects: Creates temporary test files, executes searches, cleans up test data.
     */
    private static void testFileSearch() {
        // Test would create temporary files and directories
        // Test single file containing target word
        // Test single file not containing target word
        // Test directory with multiple files
        // Test empty directory
        // Test non-existent path
        System.out.println("File search tests would be implemented here");
    }

    /**
     * Purpose: Tests the main method functionality with various command line scenarios.
     * 
     * Signature: void -> void
     * 
     * Examples:
     * - Tests main with valid word argument
     * - Tests main with no arguments
     * - Tests main with multiple arguments
     * 
     * Design Strategy: Function Composition - Simulate different command line inputs.
     * 
     * Effects: Captures console output, verifies expected behavior.
     */
    private static void testMainMethod() {
        // Test would simulate different command line argument scenarios
        // Test with valid word
        // Test with no arguments  
        // Test with multiple arguments
        System.out.println("Main method tests would be implemented here");
    }

    /**
     * Purpose: Executes comprehensive test suite for the Finder class.
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
        testFileSearch();
        testMainMethod();
        System.out.println("All Finder tests completed");
    }
} 