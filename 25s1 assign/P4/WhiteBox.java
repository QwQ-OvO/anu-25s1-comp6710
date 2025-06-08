package P4;

/**
 * Purpose: Represents a white box with frame border and empty (space-filled) interior.
 * 
 * Signature: Class implementing Box interface with hollow interior behavior.
 * 
 * Examples:
 * - WhiteBox(6, 4, '#') when printed shows:
 *   ######
 *   #    #
 *   #    #
 *   ######
 * 
 * Design Strategy: Cases on Position - Frame characters on border, spaces in interior.
 * 
 * Effects: Prints bordered rectangle with hollow interior, responds to size and frame changes.
 */
public class WhiteBox implements Box {
    
    /** Width of the box (3 <= width <= 40) */
    private int width;
    /** Height of the box (3 <= height <= 10) */
    private int height;
    /** Character used for the frame border */
    private char frameChar;
    
    /**
     * Purpose: Constructs a WhiteBox with specified dimensions and frame character.
     * 
     * Signature: int, int, char -> WhiteBox
     * 
     * Examples:
     * - new WhiteBox(5, 3, '*') -> 5x3 box with '*' border and space interior
     * - new WhiteBox(8, 6, '#') -> 8x6 box with '#' border and space interior
     * 
     * Design Strategy: Simple Expression - Store parameters for later use.
     * 
     * Effects: Creates WhiteBox object with specified properties.
     * 
     * @param width The width of the box (3 <= width <= 40)
     * @param height The height of the box (3 <= height <= 10)
     * @param frame The character to use for the border
     */
    public WhiteBox(int width, int height, char frame) {
        this.width = width;
        this.height = height;
        this.frameChar = frame;
    }
    
    /**
     * Purpose: Prints the white box with frame border and space-filled interior.
     * 
     * Signature: void -> void
     * 
     * Examples:
     * - 5x4 box with '#' prints:
     *   #####
     *   #   #
     *   #   #
     *   #####
     * 
     * Design Strategy: Cases on Position - Check if at border or interior.
     * 
     * Effects: Outputs bordered rectangle with hollow interior to standard output.
     */
    @Override
    public void print() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                // Check if we're on the border
                if (row == 0 || row == height - 1 || col == 0 || col == width - 1) {
                    System.out.print(frameChar);
                } else {
                    System.out.print(' ');
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
} 