package P4;

/**
 * Represents rectangular boxes consisting of printable 
 * characters. A box has a frame and an interior, and 
 * a frame character to represent the frame of the box
 * when printed.
 */
public interface Box {
    /**
     * Writes several lines representing this box
     * to the standard output. At minimum, this means
     * drawing frame characters along the box's outline.
     */
    void print();
    
    /**
     * Sets the size of the frame of the box
     * Effects: the box now has the given width and height
     * @param width The width of the box, 3 <= width <= 40
     * @param height The height of the box, 3 <= height <= 10
     */
    void setSize(int width, int height);

    /**
     * Sets the character to be used to draw the frame 
     * of the box in future calls to the print method.
     * Effects: the box now uses the given character as
     *   its frame character
     * @param frame the new frame character
     */
    void setFrameChar(char frame);
} 