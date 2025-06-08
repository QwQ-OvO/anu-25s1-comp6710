package p3;

/** Import packages from course website. */
import comp1110.lib.*;
import static comp1110.lib.Functions.*;
import static comp1110.testing.Comp1110Unit.*;

import java.io.File;

// =============================================================================
// ANCESTOR FILE PATH ANALYSIS
// =============================================================================

/**
 * Purpose: Represents an ancestor file with name and distance information for file system hierarchy analysis.
 * 
 * Signature: Class containing String, int, and File fields for ancestor file information.
 * 
 * Examples:
 * - new Ancestor(file) -> creates ancestor with file name and calculated distance from root
 * - ancestor.name() -> returns file name
 * - ancestor.distance() -> returns directory depth from root
 * 
 * Design Strategy: Simple Expression - Immutable data holder for file hierarchy information.
 * 
 * Effects: Creates ancestor object with calculated distance, pure functions for data access.
 */
public class Ancestor {

    // =============================================================================
    // DATA FIELDS
    // =============================================================================

    /** The name of the ancestor file */
    private String name;
    /** The distance (depth) of the file from the root directory */
    private int distance;
    /** The original File object for reference */
    private File file;

    // =============================================================================
    // CONSTRUCTOR
    // =============================================================================

    /**
     * DESIGN RECIPE STEP 1: Data Definition
     * Ancestor represents a file with name and hierarchy distance information
     * 
     * DESIGN RECIPE STEP 2: Function Signature and Purpose Statement
     * 
     * Purpose: Constructs an Ancestor object with file name and calculated distance from root.
     * 
     * Signature: File -> Ancestor
     * 
     * Examples:
     * - new Ancestor(new File("/home/user/docs/file.txt")) -> creates ancestor with distance 3
     * - new Ancestor(new File("file.txt")) -> creates ancestor with distance 0 (current directory)
     * 
     * Design Strategy: Simple Expression - Extract file name and calculate directory depth.
     * 
     * Effects: Creates new Ancestor object, calculates distance through file system traversal.
     * 
     * @param file File to create ancestor information for
     */
    public Ancestor(File file) {
        // DESIGN RECIPE STEP 4: Function Template
        // - Store file reference
        // - Extract file name from file object
        // - Calculate distance from root using helper method
        
        // DESIGN RECIPE STEP 5: Function Body
        this.file = file;
        this.name = file.getName();
        this.distance = getDistance(file);
    }
    // DESIGN RECIPE STEP 6: Testing
    // Tests would verify correct name extraction and distance calculation

    // =============================================================================
    // ACCESSOR METHODS
    // =============================================================================

    /**
     * Purpose: Returns the name of the ancestor file.
     * 
     * Signature: void -> String
     * 
     * Examples:
     * - ancestor.name() -> "file.txt" if file is named file.txt
     * - ancestor.name() -> "document.pdf" if file is named document.pdf
     * 
     * Design Strategy: Simple Expression - Direct field access.
     * 
     * Effects: Pure function with no side effects, returns String reference.
     * 
     * @return The name of the ancestor file
     */
    public String name() {
        return this.name;
    }

    /**
     * Purpose: Returns the distance (depth) of the file from the root directory.
     * 
     * Signature: void -> int
     * 
     * Examples:
     * - ancestor.distance() -> 3 if file is 3 directories deep from root
     * - ancestor.distance() -> 0 if file is in root directory
     * 
     * Design Strategy: Simple Expression - Direct field access.
     * 
     * Effects: Pure function with no side effects, returns primitive int value.
     * 
     * @return The distance from root directory
     */
    public int distance() {
        return this.distance;
    }

    // =============================================================================
    // UTILITY METHODS
    // =============================================================================

    /**
     * Purpose: Calculates the distance (depth) of a file from the root directory by counting parent directories.
     * 
     * Signature: File -> int
     * 
     * Examples:
     * - getDistance(new File("/home/user/file.txt")) -> 2 (counting home, user)
     * - getDistance(new File("file.txt")) -> 0 (no parent directories)
     * - getDistance(new File("/root/dir1/dir2/file.txt")) -> 3 (counting root, dir1, dir2)
     * 
     * Design Strategy: Function Composition - Iteratively traverse parent directories counting levels.
     * 
     * Effects: Pure function with no side effects, returns integer distance calculation.
     * 
     * @param file File to calculate distance for
     * @return Number of parent directories from root (0 if no parents)
     */
    public static int getDistance(File file) {
        // DESIGN RECIPE STEP 4: Function Template
        // - Initialize distance counter
        // - Start with given file
        // - Loop while file has parent directory
        // - Increment counter and move to parent
        // - Return final count
        
        // DESIGN RECIPE STEP 5: Function Body
        int currentDistance = 0;
        File currentFile = file;
        
        // Count parent directories until reaching root
        while (currentFile.getParentFile() != null) {
            currentDistance++;
            currentFile = currentFile.getParentFile();
        }
        
        return currentDistance;
    }
}
