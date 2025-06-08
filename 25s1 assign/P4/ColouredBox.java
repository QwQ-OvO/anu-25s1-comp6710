package P4;

/**
 * Purpose: Represents a coloured box with frame border and mutable fill character interior.
 * 
 * Signature: Class implementing Box interface with mutable fill character behavior.
 * 
 * Examples:
 * - ColouredBox(6, 4, '#', '@') when printed shows:
 *   ######
 *   #@@@@#
 *   #@@@@#
 *   ######
 * - After setFillChar('.'), prints:
 *   ######
 *   #....#
 *   #....#
 *   ######
 * 
 * Design Strategy: Cases on Position - Frame characters on border, mutable fill character in interior.
 * 
 * Effects: Prints bordered rectangle with filled interior, fill character can be changed after construction.
 */
public class ColouredBox implements Box {
    
    /** Width of the box (3 <= width <= 40) */
    private int width;
    /** Height of the box (3 <= height <= 10) */
    private int height;
    /** Character used for the frame border */
    private char frameChar;
    /** Character used to fill the interior (mutable after construction) */
    private char fillChar;
    
    /**
     * Purpose: Constructs a ColouredBox with specified dimensions, frame character, and fill character.
     * 
     * Signature: int, int, char, char -> ColouredBox
     * 
     * Examples:
     * - new ColouredBox(5, 3, '*', '.') -> 5x3 box with '*' border and '.' interior
     * - new ColouredBox(8, 6, '#', '@') -> 8x6 box with '#' border and '@' interior
     * 
     * Design Strategy: Simple Expression - Store parameters for later use.
     * 
     * Effects: Creates ColouredBox object with specified properties.
     * 
     * @param width The width of the box (3 <= width <= 40)
     * @param height The height of the box (3 <= height <= 10)
     * @param frame The character to use for the border
     * @param fill The character to use for the interior (can be changed later)
     */
    public ColouredBox(int width, int height, char frame, char fill) {
        this.width = width;
        this.height = height;
        this.frameChar = frame;
        this.fillChar = fill;
    }
    
    /**
     * Purpose: Prints the coloured box with frame border and current fill character interior.
     * 
     * Signature: void -> void
     * 
     * Examples:
     * - 5x4 box with '#' frame and '@' fill prints:
     *   #####
     *   #@@@#
     *   #@@@#
     *   #####
     * 
     * Design Strategy: Cases on Position - Check if at border or interior, using current fill character.
     * 
     * Effects: Outputs bordered rectangle with filled interior to standard output.
     */
    @Override
    public void print() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // Check if we're on the border
                if (row == 0 || row == height - 1 || col == 0 || col == width - 1) {
                    System.out.print(frameChar);
                } else {
                    System.out.print(fillChar);
                }
            }
            System.out.println();
        }
    }
    
    /**
     * Purpose: Updates the dimensions of the box.
     * 
     * Signature: int, int -> void
     * 
     * Examples:
     * - setSize(7, 4) -> changes box to 7x4 dimensions
     * 
     * Design Strategy: Simple Expression - Update dimension fields.
     * 
     * Effects: Modifies box dimensions for future print operations.
     * 
     * @param width The new width (3 <= width <= 40)
     * @param height The new height (3 <= height <= 10)
     */
    @Override
    public void setSize(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    /**
     * Purpose: Updates the character used for the frame border.
     * 
     * Signature: char -> void
     * 
     * Examples:
     * - setFrameChar('@') -> future prints will use '@' for border
     * 
     * Design Strategy: Simple Expression - Update frame character field.
     * 
     * Effects: Changes border character for future print operations.
     * 
     * @param frame The new frame character
     */
    @Override
    public void setFrameChar(char frame) {
        this.frameChar = frame;
    }
    
    /**
     * Purpose: Updates the character used to fill the interior of the box.
     * 
     * Signature: char -> void
     * 
     * Examples:
     * - setFillChar('.') -> future prints will use '.' for interior
     * - setFillChar('*') -> future prints will use '*' for interior
     * 
     * Design Strategy: Simple Expression - Update fill character field.
     * 
     * Effects: Changes interior fill character for future print operations.
     * 
     * @param fill The new fill character
     */
    public void setFillChar(char fill) {
        this.fillChar = fill;
    }
} 