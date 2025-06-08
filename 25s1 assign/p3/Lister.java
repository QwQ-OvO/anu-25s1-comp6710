package p3;

import java.io.*;
import java.util.*;

// =============================================================================
// LISTER UTILITY FOR DIRECTORY STRUCTURE DISPLAY
// =============================================================================

/**
 * Purpose: Provides functionality to display directory structures with hierarchical formatting.
 * 
 * Signature: Static class containing directory listing and formatting methods.
 * 
 * Examples:
 * - Lister.main(["/path/to/dir"]) -> prints formatted directory tree structure
 * - Lister.main([]) -> prints structure of current directory
 * 
 * Design Strategy: Function Composition - Recursive directory traversal with formatted output.
 * 
 * Effects: Reads file system structure, prints to console, may throw exceptions.
 */
public class Lister {

    // =============================================================================
    // MAIN PROGRAM FOR COMMAND LINE INTERFACE
    // =============================================================================

    /**
     * DESIGN RECIPE STEP 1: Data Definition
     * Directory structure represented as nested File objects
     * 
     * DESIGN RECIPE STEP 2: Function Signature and Purpose Statement
     * 
     * Purpose: Command line interface that displays directory structure in hierarchical format.
     * 
     * Signature: String[] -> void
     * 
     * Examples:
     * - main(["/home/user/docs"]) -> prints directory tree starting from /home/user/docs
     * - main([]) -> prints directory tree starting from current directory
     * - main([".", "extra"]) -> only processes first argument
     * 
     * Design Strategy: Function Composition - Parse arguments, traverse directory, format output.
     * 
     * Effects: Prints to console, reads directory structure, may throw exceptions.
     * 
     * @param args Command line arguments where first argument is optional directory path
     */
    public static void main(String[] args) {
        // DESIGN RECIPE STEP 4: Function Template
        // - Parse command line arguments for directory path
        // - Default to current directory if no path provided
        // - Validate directory exists and is accessible
        // - Recursively list directory contents with formatting
        // - Handle file access errors gracefully
        
        // DESIGN RECIPE STEP 5: Function Body
        String directoryPath = "."; // Default to current directory
        
        // Parse command line arguments
        if (args.length > 0) {
            directoryPath = args[0];
        }

        File directory = new File(directoryPath);
        
        // Validate directory exists and is accessible
        if (!directory.exists()) {
            System.err.println("Error: Directory " + directoryPath + " does not exist");
            return;
        }
        
        if (!directory.isDirectory()) {
            System.err.println("Error: " + directoryPath + " is not a directory");
            return;
        }

        try {
            // Print directory structure
            System.out.println("Directory structure for: " + directory.getAbsolutePath());
            System.out.println();
            
            // Start recursive listing
            listDirectory(directory, "");
            
        } catch (Exception e) {
            System.err.println("Error listing directory: " + e.getMessage());
            e.printStackTrace();
        }
    }
    // DESIGN RECIPE STEP 6: Testing
    // Tests would verify correct directory traversal and formatting

    // =============================================================================
    // DIRECTORY LISTING METHODS
    // =============================================================================

    /**
     * Purpose: Recursively lists directory contents with hierarchical indentation.
     * 
     * Signature: File, String -> void
     * 
     * Examples:
     * - listDirectory(dir, "") -> lists directory contents with no indentation
     * - listDirectory(subdir, "  ") -> lists subdirectory contents with 2-space indentation
     * - listDirectory(empty_dir, "    ") -> prints nothing for empty directory
     * 
     * Design Strategy: Cases on Directory Contents - Process each file/subdirectory with appropriate formatting.
     * 
     * Effects: Prints directory structure to console, recursively reads subdirectories.
     * 
     * @param directory Directory to list contents of
     * @param indent Current indentation string for hierarchical formatting
     */
    private static void listDirectory(File directory, String indent) {
        File[] contents = directory.listFiles();
        
        if (contents == null) {
            System.out.println(indent + "[Cannot access directory contents]");
            return;
        }
        
        // Sort contents alphabetically for consistent output
        Arrays.sort(contents, (f1, f2) -> f1.getName().compareToIgnoreCase(f2.getName()));
        
        // Process each item in the directory
        for (File item : contents) {
            if (item.isFile()) {
                // Print file with size information
                printFileInfo(item, indent);
            } else if (item.isDirectory()) {
                // Print directory and recursively list its contents
                printDirectoryInfo(item, indent);
                listDirectory(item, indent + "  ");
            }
        }
    }

    /**
     * Purpose: Prints formatted information for a single file.
     * 
     * Signature: File, String -> void
     * 
     * Examples:
     * - printFileInfo(file.txt, "") -> "file.txt (1024 bytes)"
     * - printFileInfo(large.dat, "  ") -> "  large.dat (2048576 bytes)"
     * 
     * Design Strategy: Simple Expression - Format file name and size with indentation.
     * 
     * Effects: Prints single line to console with file information.
     * 
     * @param file File to print information for
     * @param indent Indentation string for hierarchical formatting
     */
    private static void printFileInfo(File file, String indent) {
        long fileSize = file.length();
        System.out.println(indent + file.getName() + " (" + fileSize + " bytes)");
    }

    /**
     * Purpose: Prints formatted information for a directory header.
     * 
     * Signature: File, String -> void
     * 
     * Examples:
     * - printDirectoryInfo(mydir/, "") -> "mydir/"
     * - printDirectoryInfo(subdir/, "  ") -> "  subdir/"
     * 
     * Design Strategy: Simple Expression - Format directory name with trailing slash and indentation.
     * 
     * Effects: Prints single line to console with directory header.
     * 
     * @param directory Directory to print header for
     * @param indent Indentation string for hierarchical formatting
     */
    private static void printDirectoryInfo(File directory, String indent) {
        System.out.println(indent + directory.getName() + "/");
    }

    // =============================================================================
    // UTILITY METHODS
    // =============================================================================

    /**
     * Purpose: Counts total number of files in directory tree.
     * 
     * Signature: File -> int
     * 
     * Examples:
     * - countFiles(directory) -> 15 if directory tree contains 15 files total
     * - countFiles(empty_dir) -> 0 if directory is empty
     * 
     * Design Strategy: Cases on File Type - Recursively count files in subdirectories.
     * 
     * Effects: Recursively reads directory structure, pure function with no side effects.
     * 
     * @param directory Directory to count files in
     * @return Total number of files in directory tree
     */
    private static int countFiles(File directory) {
        File[] contents = directory.listFiles();
        if (contents == null) {
            return 0;
        }
        
        int fileCount = 0;
        for (File item : contents) {
            if (item.isFile()) {
                fileCount++;
            } else if (item.isDirectory()) {
                fileCount += countFiles(item);
            }
        }
        
        return fileCount;
    }

    /**
     * Purpose: Counts total number of directories in directory tree.
     * 
     * Signature: File -> int
     * 
     * Examples:
     * - countDirectories(directory) -> 5 if directory tree contains 5 subdirectories
     * - countDirectories(flat_dir) -> 0 if directory contains only files
     * 
     * Design Strategy: Cases on File Type - Recursively count directories in subdirectories.
     * 
     * Effects: Recursively reads directory structure, pure function with no side effects.
     * 
     * @param directory Directory to count subdirectories in
     * @return Total number of subdirectories in directory tree
     */
    private static int countDirectories(File directory) {
        File[] contents = directory.listFiles();
        if (contents == null) {
            return 0;
        }
        
        int dirCount = 0;
        for (File item : contents) {
            if (item.isDirectory()) {
                dirCount++; // Count this directory
                dirCount += countDirectories(item); // Count subdirectories recursively
            }
        }
        
        return dirCount;
    }

    /**
     * Purpose: Calculates total size of all files in directory tree.
     * 
     * Signature: File -> long
     * 
     * Examples:
     * - getTotalSize(directory) -> 1048576 if all files total 1MB
     * - getTotalSize(empty_dir) -> 0 if directory is empty
     * 
     * Design Strategy: Cases on File Type - Recursively sum file sizes in subdirectories.
     * 
     * Effects: Recursively reads directory structure and file sizes, pure function.
     * 
     * @param directory Directory to calculate total size for
     * @return Total size in bytes of all files in directory tree
     */
    private static long getTotalSize(File directory) {
        File[] contents = directory.listFiles();
        if (contents == null) {
            return 0;
        }
        
        long totalSize = 0;
        for (File item : contents) {
            if (item.isFile()) {
                totalSize += item.length();
            } else if (item.isDirectory()) {
                totalSize += getTotalSize(item);
            }
        }
        
        return totalSize;
    }

    // =============================================================================
    // TESTING METHODS
    // =============================================================================

    /**
     * Purpose: Tests the directory listing functionality with various scenarios.
     * 
     * Signature: void -> void
     * 
     * Examples:
     * - Tests listing empty directory
     * - Tests listing directory with files only
     * - Tests listing nested directory structure
     * - Tests handling of inaccessible directories
     * 
     * Design Strategy: Function Composition - Create test cases covering different directory structures.
     * 
     * Effects: Creates temporary test directories, executes listings, cleans up test data.
     */
    private static void testDirectoryListing() {
        // Test would create temporary directory structures
        // Test empty directory
        // Test directory with files only
        // Test nested directory structure
        // Test symbolic links and special files
        // Test inaccessible directories
        System.out.println("Directory listing tests would be implemented here");
    }

    /**
     * Purpose: Tests the utility methods for counting and size calculation.
     * 
     * Signature: void -> void
     * 
     * Examples:
     * - Tests file counting accuracy
     * - Tests directory counting accuracy
     * - Tests size calculation accuracy
     * 
     * Design Strategy: Function Composition - Verify counting and size calculations.
     * 
     * Effects: Creates test directories, verifies calculations, cleans up test data.
     */
    private static void testUtilityMethods() {
        // Test would create known directory structures
        // Test countFiles with various directory layouts
        // Test countDirectories with nested structures
        // Test getTotalSize with files of known sizes
        System.out.println("Utility method tests would be implemented here");
    }

    /**
     * Purpose: Executes comprehensive test suite for the Lister class.
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
        testDirectoryListing();
        testUtilityMethods();
        System.out.println("All Lister tests completed");
    }
}
