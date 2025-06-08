package P4;

/**
 * Purpose: Represents a black box that fills the entire rectangle with frame characters.
 * 
 * Signature: Class implementing Box interface with complete fill behavior.
 * 
 * Examples:
 * - BlackBox(6, 4, '#') when printed shows:
 *   ######
 *   ######
 *   ######
 *   ######
 * 
 * Design Strategy: Simple Expression - Fill entire rectangle with frame character.
 * 
 * Effects: Prints complete filled rectangle, responds to size and frame changes.
 */
public class BlackBox implements Box {
    
    /** Width of the box (3 <= width <= 40) */
    private int width;
    /** Height of the box (3 <= height <= 10) */
    private int height;
    /** Character used to fill the entire box */
    private char frameChar;
    
    /**
     * Purpose: Constructs a BlackBox with specified dimensions and frame character.
     * 
     * Signature: int, int, char -> BlackBox
     * 
     * Examples:
     * - new BlackBox(5, 3, '*') -> 5x3 box filled with '*'
     * - new BlackBox(8, 6, '#') -> 8x6 box filled with '#'
     * 
     * Design Strategy: Simple Expression - Store parameters for later use.
     * 
     * Effects: Creates BlackBox object with specified properties.
     * 
     * @param width The width of the box (3 <= width <= 40)
     * @param height The height of the box (3 <= height <= 10)
     * @param frame The character to fill the box with
     */
    public BlackBox(int width, int height, char frame) {
        this.width = width;
        this.height = height;
        this.frameChar = frame;
    }
    
    /**
     * Purpose: Prints the black box by filling every position with the frame character.
     * 
     * Signature: void -> void
     * 
     * Examples:
     * - 4x3 box with '#' prints:
     *   ####
     *   ####
     *   ####
     * 
     * Design Strategy: Nested Iteration - Print frame character for every position.
     * 
     * Effects: Outputs filled rectangle to standard output.
     */
    @Override
    public void print() {
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                System.out.print(frameChar);
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
     * Purpose: Updates the character used to fill the box.
     * 
     * Signature: char -> void
     * 
     * Examples:
     * - setFrameChar('@') -> future prints will use '@' character
     * 
     * Design Strategy: Simple Expression - Update frame character field.
     * 
     * Effects: Changes fill character for future print operations.
     * 
     * @param frame The new frame character
     */
    @Override
    public void setFrameChar(char frame) {
        this.frameChar = frame;
    }
} 