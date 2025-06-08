package p3;

// =============================================================================
// PART 2: WORD-COUNT ENTRY DATA DEFINITION
// =============================================================================

/**
 * Purpose: Represents a word-count entry containing a word and its frequency count in a text file.
 * 
 * Signature: Class containing String and int fields for word and count respectively.
 * 
 * Examples:
 * - new Word("hello", 5) -> word-count entry for "hello" appearing 5 times
 * - new Word("the", 20) -> word-count entry for "the" appearing 20 times
 * 
 * Design Strategy: Simple Expression - Immutable data holder for word frequency information.
 * 
 * Effects: Creates immutable word-count object with validation, may throw exceptions for invalid input.
 */
public class Word {

    // =============================================================================
    // DATA FIELDS
    // =============================================================================

    /** The word string that this entry represents */
    private String word;
    /** The frequency count of this word in the source text */
    private int count;

    // =============================================================================
    // CONSTRUCTOR
    // =============================================================================

    /**
     * DESIGN RECIPE STEP 1: Data Definition
     * Word represents a word-count pair for frequency analysis
     * 
     * DESIGN RECIPE STEP 2: Function Signature and Purpose Statement
     * 
     * Purpose: Constructs a Word object with specified word string and frequency count.
     * 
     * Signature: String, int -> Word
     * 
     * Examples:
     * - new Word("test", 3) -> creates word-count entry for "test" with count 3
     * - new Word("", 0) -> creates entry for empty string with zero count
     * 
     * Design Strategy: Simple Expression - Direct assignment with input validation.
     * 
     * Effects: Creates new Word object, may throw IllegalArgumentException for invalid input.
     * 
     * @param word The word string (must not be null)
     * @param count The frequency count (must be non-negative)
     * @throws IllegalArgumentException if word is null or count is negative
     */
    public Word(String word, int count) {
        // DESIGN RECIPE STEP 4: Function Template
        // - Validate word parameter (not null)
        // - Validate count parameter (non-negative)
        // - Assign validated values to fields
        
        // DESIGN RECIPE STEP 5: Function Body
        // Parameter validation
        if (word == null) {
            throw new IllegalArgumentException("Word cannot be null");
        }
        if (count < 0) {
            throw new IllegalArgumentException("Count must be non-negative");
        }

        this.word = word;
        this.count = count;
    }
    // DESIGN RECIPE STEP 6: Testing
    // Tests would verify correct construction and validation behavior

    // =============================================================================
    // ACCESSOR METHODS
    // =============================================================================

    /**
     * Purpose: Returns the word string that this word-count entry represents.
     * 
     * Signature: void -> String
     * 
     * Examples:
     * - word.getWord() -> "hello" if Word was created with "hello"
     * - word.getWord() -> "" if Word was created with empty string
     * 
     * Design Strategy: Simple Expression - Direct field access.
     * 
     * Effects: Pure function with no side effects, returns String reference.
     * 
     * @return The word string for this entry
     */
    String getWord() {
        return word;
    }

    /**
     * Purpose: Returns the frequency count of this word in the source text.
     * 
     * Signature: void -> int
     * 
     * Examples:
     * - word.getCount() -> 5 if Word was created with count 5
     * - word.getCount() -> 0 if Word was created with count 0
     * 
     * Design Strategy: Simple Expression - Direct field access.
     * 
     * Effects: Pure function with no side effects, returns primitive int value.
     * 
     * @return The frequency count for this word
     */
    int getCount() {
        return count;
    }

    // =============================================================================
    // UTILITY METHODS
    // =============================================================================

    /**
     * Purpose: Returns string representation of this word-count entry for debugging and display.
     * 
     * Signature: void -> String
     * 
     * Examples:
     * - new Word("hello", 5).toString() -> "Word{word='hello', count=5}"
     * - new Word("test", 1).toString() -> "Word{word='test', count=1}"
     * 
     * Design Strategy: Simple Expression - String concatenation with field values.
     * 
     * Effects: Pure function with no side effects, returns new String object.
     * 
     * @return String representation of this Word object
     */
    @Override
    public String toString() {
        return "Word{word='" + word + "', count=" + count + "}";
    }

    /**
     * Purpose: Checks equality with another object based on word string and count values.
     * 
     * Signature: Object -> boolean
     * 
     * Examples:
     * - new Word("test", 3).equals(new Word("test", 3)) -> true
     * - new Word("test", 3).equals(new Word("test", 4)) -> false
     * - new Word("test", 3).equals("test") -> false (different type)
     * 
     * Design Strategy: Cases on Object Type - Check type, then compare fields.
     * 
     * Effects: Pure function with no side effects, returns boolean value.
     * 
     * @param obj Object to compare with this Word
     * @return true if objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Word word1 = (Word) obj;
        return count == word1.count && 
               java.util.Objects.equals(word, word1.word);
    }

    /**
     * Purpose: Returns hash code for this Word object based on word and count fields.
     * 
     * Signature: void -> int
     * 
     * Examples:
     * - new Word("test", 3).hashCode() -> consistent integer for this word-count pair
     * 
     * Design Strategy: Simple Expression - Combine field hash codes.
     * 
     * Effects: Pure function with no side effects, returns integer hash code.
     * 
     * @return Hash code for this Word object
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(word, count);
    }
}
