package reading;

import basic.Block;

/**
 * The interface Block creator.
 */
public interface BlockCreator {
    /**
     * Create block.
     *
     * @param xpos the xpos of the upper lefr point
     * @param ypos the ypos of the upper lefr point
     * @return the block
     */
// Create a block at the specified location.
    Block create(int xpos, int ypos);
}
