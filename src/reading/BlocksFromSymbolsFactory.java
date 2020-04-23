package reading;


import basic.Block;

import java.util.HashMap;
import java.util.Map;


/**
 * The type Blocks from symbols factory.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths = new HashMap();
    private Map<String, BlockCreator> blockCreators = new HashMap();


    /**
     * Add spacer widths.
     *
     * @param s     the symbol
     * @param width the width
     */
    public void addSpacerWidths(String s, Integer width) {
        this.spacerWidths.put(s, width);
    }

    /**
     * Add block creators.
     *
     * @param s            the symbol
     * @param blockCreator the block creator
     */
    public void addBlockCreators(String s, BlockCreator blockCreator) {
        this.blockCreators.put(s, blockCreator);
    }


    /**
     * Is space symbol boolean.
     *
     * @param s the symbol
     * @return the boolean
     */
// returns true if 's' is a valid space symbol.
    public boolean isSpaceSymbol(String s) {
        if (spacerWidths.containsKey(s)) {
            return true;
        }
        return false;
    }

    /**
     * Is block symbol boolean.
     *
     * @param s the symbol
     * @return the boolean
     */
// returns true if 's' is a valid block symbol.
    public boolean isBlockSymbol(String s) {
        if (blockCreators.containsKey(s)) {
            return true;
        }
        return false;

    }


    /**
     * Gets space width.
     *
     * @param s the s
     * @return the space width
     */
// Returns the width in pixels associated with the given spacer-symbol.
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }

    /**
     * Gets block.
     *
     * @param s    the s
     * @param xpos the xpos
     * @param ypos the ypos
     * @return the block
     */
// Return a block according to the definitions associated
    // with symbol s. The block will be located at position (xpos, ypos).
    public Block getBlock(String s, int xpos, int ypos) {
        Block b = this.blockCreators.get(s).create(xpos, ypos);
        return b;
    }

}

