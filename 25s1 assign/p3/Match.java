package p3;

import java.io.*;
import java.util.*;

// =============================================================================
// PART 4: SEARCH MATCH DATA DEFINITION
// =============================================================================

/**
 * Purpose: Represents a search match containing file location, line information, and matching content.
 * 
 * Signature: Class containing File, int, and String[] fields for match details.
 * 
 * Examples:
 * - new Match(file, 5, ["line content"]) -> match starting at line 5 with one line of content
 * - new Match(file, 3, ["first line", "second line"]) -> multi-line match starting at line 3
 * 
 * Design Strategy: Simple Expression - Immutable data holder for search match information.
 * 
 * Effects: Creates immutable match object with validation, may throw exceptions for invalid input.
 */
public class Match {

    // =============================================================================
    // DATA FIELDS
    // =============================================================================

    /** The file in which the match was found */
    private File file;
    /** The 0-based index of the first line contained in the match */
    private int startLine;
    /** The full lines that contain the match */
    private String[] matchingLines;

    // =============================================================================
    // CONSTRUCTOR
    // =============================================================================

    /**
     * DESIGN RECIPE STEP 1: Data Definition
     * Match represents a search result with file location and content
     * 
     * DESIGN RECIPE STEP 2: Function Signature and Purpose Statement
     * 
     * Purpose: Constructs a Match object with specified file, starting line, and matching content.
     * 
     * Signature: File, int, String[] -> Match
     * 
     * Examples:
     * - new Match(file, 0, ["first line"]) -> match at file start with one line
     * - new Match(file, 10, ["line 10", "line 11"]) -> multi-line match starting at line 10
     * 
     * Design Strategy: Simple Expression - Direct assignment with input validation.
     * 
     * Effects: Creates new Match object, may throw IllegalArgumentException for invalid input.
     * 
     * @param file The file containing the match (must not be null)
     * @param startLine The 0-based line index where match starts (must be non-negative)
     * @param matchingLines Array of lines containing the match (must not be null or empty)
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Match(File file, int startLine, String[] matchingLines) {
        // DESIGN RECIPE STEP 4: Function Template
        // - Validate file parameter (not null)
        // - Validate startLine parameter (non-negative)
        // - Validate matchingLines parameter (not null, not empty)
        // - Assign validated values to fields
        
        // DESIGN RECIPE STEP 5: Function Body
        // Parameter validation
        if (file == null) {
            throw new IllegalArgumentException("File cannot be null");
        }
        if (startLine < 0) {
            throw new IllegalArgumentException("Start line must be non-negative");
        }
        if (matchingLines == null || matchingLines.length == 0) {
            throw new IllegalArgumentException("Matching lines cannot be null or empty");
        }

        this.file = file;
        this.startLine = startLine;
        // Create defensive copy to ensure immutability
        this.matchingLines = Arrays.copyOf(matchingLines, matchingLines.length);
    }
    // DESIGN RECIPE STEP 6: Testing
    // Tests would verify correct construction and validation behavior

    // =============================================================================
    // ACCESSOR METHODS
    // =============================================================================

    /**
     * Purpose: Returns the file in which the match was found.
     * 
     * Signature: void -> File
     * 
     * Examples:
     * - match.getFile() -> File object representing the file containing the match
     * 
     * Design Strategy: Simple Expression - Direct field access.
     * 
     * Effects: Pure function with no side effects, returns File reference.
     * 
     * @return The file containing this match
     */
    File getFile() {
        return file;
    }

    /**
     * Purpose: Returns the 0-based index of the first line contained in the match.
     * 
     * Signature: void -> int
     * 
     * Examples:
     * - match.getStartLine() -> 5 if match starts at line 5 (0-indexed)
     * - match.getStartLine() -> 0 if match starts at first line of file
     * 
     * Design Strategy: Simple Expression - Direct field access.
     * 
     * Effects: Pure function with no side effects, returns primitive int value.
     * 
     * @return The 0-based starting line index of this match
     */
    int getStartLine() {
        return startLine;
    }

    /**
     * Purpose: Returns the full lines that contain the match content.
     * 
     * Signature: void -> String[]
     * 
     * Examples:
     * - match.getMatchingLines() -> ["single line content"] for single-line match
     * - match.getMatchingLines() -> ["first line", "second line"] for multi-line match
     * 
     * Design Strategy: Simple Expression - Return defensive copy to preserve immutability.
     * 
     * Effects: Pure function with no side effects, returns new String array.
     * 
     * @return Array of lines containing the match (defensive copy)
     */
    String[] getMatchingLines() {
        // Return defensive copy to maintain immutability
        return Arrays.copyOf(matchingLines, matchingLines.length);
    }

    // =============================================================================
    // UTILITY METHODS
    // =============================================================================

    /**
     * Purpose: Returns string representation of this match for debugging and display.
     * 
     * Signature: void -> String
     * 
     * Examples:
     * - match.toString() -> "Match{file=test.txt, startLine=5, lines=1}"
     * 
     * Design Strategy: Simple Expression - String concatenation with field values.
     * 
     * Effects: Pure function with no side effects, returns new String object.
     * 
     * @return String representation of this Match object
     */
    @Override
    public String toString() {
        return "Match{file=" + file.getName() + 
               ", startLine=" + startLine + 
               ", lines=" + matchingLines.length + "}";
    }

    /**
     * Purpose: Checks equality with another object based on file, startLine, and matchingLines.
     * 
     * Signature: Object -> boolean
     * 
     * Examples:
     * - match1.equals(match2) -> true if all fields are equal
     * - match1.equals(different_match) -> false if any field differs
     * - match.equals("string") -> false (different type)
     * 
     * Design Strategy: Cases on Object Type - Check type, then compare all fields.
     * 
     * Effects: Pure function with no side effects, returns boolean value.
     * 
     * @param obj Object to compare with this Match
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Match match = (Match) obj;
        return startLine == match.startLine &&
               Objects.equals(file, match.file) &&
               Arrays.equals(matchingLines, match.matchingLines);
    }

    /**
     * Purpose: Returns hash code for this Match object based on all fields.
     * 
     * Signature: void -> int
     * 
     * Examples:
     * - match.hashCode() -> consistent integer for this match configuration
     * 
     * Design Strategy: Simple Expression - Combine field hash codes.
     * 
     * Effects: Pure function with no side effects, returns integer hash code.
     * 
     * @return Hash code for this Match object
     */
    @Override
    public int hashCode() {
        return Objects.hash(file, startLine, Arrays.hashCode(matchingLines));
    }

    /**
     * Purpose: Returns the number of lines this match spans.
     * 
     * Signature: void -> int
     * 
     * Examples:
     * - match.getLineCount() -> 1 for single-line match
     * - match.getLineCount() -> 3 for match spanning 3 lines
     * 
     * Design Strategy: Simple Expression - Return array length.
     * 
     * Effects: Pure function with no side effects, returns integer count.
     * 
     * @return Number of lines in this match
     */
    public int getLineCount() {
        return matchingLines.length;
    }

    /**
     * Purpose: Returns the ending line index (0-based) of this match.
     * 
     * Signature: void -> int
     * 
     * Examples:
     * - match.getEndLine() -> 5 for single-line match starting at line 5
     * - match.getEndLine() -> 7 for match starting at line 5 and spanning 3 lines
     * 
     * Design Strategy: Simple Expression - Calculate from start line and line count.
     * 
     * Effects: Pure function with no side effects, returns integer line index.
     * 
     * @return The 0-based ending line index of this match
     */
    public int getEndLine() {
        return startLine + matchingLines.length - 1;
    }
} 