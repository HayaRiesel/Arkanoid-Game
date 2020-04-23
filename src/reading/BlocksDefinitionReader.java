package reading;

import basic.Block;
import basic.Filling;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The type Blocks definition reader.
 */
public class BlocksDefinitionReader {

    private static int defaultHeight;
    private static int defaultWidth;
    private static int defaultHitPoint;
    private static Filling defaultFill;
    private static Map<Integer, Filling> defaultFillMap;
    private static Color defaultStroke;


    /**
     * From reader blocks from symbols factory.
     *
     * @param reader the reader
     * @return the blocks from symbols factory
     * @throws Exception the exception
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) throws Exception {
        BlocksFromSymbolsFactory blocksFromSymbolsFactory = new BlocksFromSymbolsFactory();

        BufferedReader is = null;
        List<String> lines = new ArrayList<String>();
        //read the file nd remove all the line to list
        try {
            is = new BufferedReader(reader);
            String line;
            while ((line = is.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //close the file
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //analysis each line
        for (int i = 0; i < lines.size(); i++) {
            lineAnalysis(lines.get(i), blocksFromSymbolsFactory);
        }
        return blocksFromSymbolsFactory;
    }

    /**
     * Line analysis.
     *
     * @param line                     the line that need to be analysis
     * @param blocksFromSymbolsFactory the blocks from symbols factory
     * @throws Exception the exception
     */
    private static void lineAnalysis(String line, BlocksFromSymbolsFactory blocksFromSymbolsFactory) throws Exception {
        if (line.startsWith("default")) {
            defaultAnalysis(line);
        }
        if (line.startsWith("bdef")) {
            createBlockCreater(line, blocksFromSymbolsFactory);
        }
        if (line.startsWith("sdef")) {
            createSpacer(line, blocksFromSymbolsFactory);
        }
    }

    /**
     * Default analysis.
     *
     * @param s the string
     */
    private static void defaultAnalysis(String s) {
        String[] splitLine = s.split(" ");
        for (int i = 0; i < splitLine.length; i++) {
            if (splitLine[i].contains("height:")) {
                defaultHeight = Integer.valueOf(splitLine[i].replace("height:", ""));
            }
            if (splitLine[i].contains("width:")) {
                defaultWidth = Integer.valueOf(splitLine[i].replace("width:", ""));
            }
            if (splitLine[i].contains("hit_points:")) {
                defaultHitPoint = Integer.valueOf(splitLine[i].replace("hit_points:", ""));
            }
            if (splitLine[i].contains("fill:")) {
                defaultFill = ColorsParser.fillingFromString(splitLine[i].replace("fill:", ""));
            }
            if (splitLine[i].contains("fill-")) {
                String newS = splitLine[i].replace("fill-", "");
                String[] strings = splitLine[i].split(":");
                defaultFillMap.put(Integer.valueOf(strings[0]), ColorsParser.fillingFromString(strings[1]));
            }
            if (splitLine[i].contains("stroke:")) {
                defaultStroke = ColorsParser.colorFromString(splitLine[i].replace("stroke:", ""));
            }
        }
    }


    /**
     * Create block creater.
     *
     * @param line                     the line
     * @param blocksFromSymbolsFactory the blocks from symbols factory
     * @throws Exception the exception
     */
    private static void createBlockCreater(
            String line,
            BlocksFromSymbolsFactory blocksFromSymbolsFactory) throws Exception {
        String symbol = null;
        Integer height = defaultHeight;
        Integer width = defaultWidth;
        Integer hitPoint = defaultHitPoint;
        Filling fill = defaultFill;
        Map<Integer, Filling> fillsMAp = new HashMap();
        Color stroke = defaultStroke;


        String[] splitLine = line.split(" ");
        for (int i = 0; i < splitLine.length; i++) {
            if (splitLine[i].contains("symbol:")) {
                symbol = splitLine[i].replace("symbol:", "");
            }
            if (splitLine[i].contains("height:")) {
                height = Integer.valueOf(splitLine[i].replace("height:", ""));
            }
            if (splitLine[i].contains("width:")) {
                width = Integer.valueOf(splitLine[i].replace("width:", ""));
            }
            if (splitLine[i].contains("hit_points:")) {
                hitPoint = Integer.valueOf(splitLine[i].replace("hit_points:", ""));
            }
            if (splitLine[i].contains("fill:")) {
                fill = ColorsParser.fillingFromString(splitLine[i].replace("fill:", ""));
            }
            if (splitLine[i].contains("fill-")) {
                String s = splitLine[i].replace("fill-", "");
                String[] strings = s.split(":");
                fillsMAp.put(Integer.valueOf(strings[0]), ColorsParser.fillingFromString(strings[1]));
            }
            if (splitLine[i].contains("stroke:")) {
                stroke = ColorsParser.colorFromString(splitLine[i].replace("stroke:", ""));
            }
        }

        if (fillsMAp.isEmpty()) {
            fillsMAp = defaultFillMap;
        }
        if (fill == null) {
            fill = fillsMAp.get(1);
        }

        //add block creator t the factory
        if (symbol != null && height != null && width != null && hitPoint != null && fill != null) {
            BlockCreator blockCreator = createInnerBlockCreator(height, width, hitPoint, fill, fillsMAp, stroke);
            blocksFromSymbolsFactory.addBlockCreators(symbol, blockCreator);
        } else {
            throw new Exception("the arg in the bdef not good");
        }

    }

    /**
     * Create inner block creator block creator.
     *
     * @param height1   the height
     * @param width1    the width
     * @param hitPoint1 the hit point
     * @param fill1     the fill
     * @param fillsMAp1 the fills map
     * @param stroke1   the stroke
     * @return the block creator
     */
    private static BlockCreator createInnerBlockCreator(
            int height1,
            int width1,
            int hitPoint1,
            Filling fill1,
            Map<Integer, Filling> fillsMAp1,
            Color stroke1) {
        //create anonymous class of block creator
        BlockCreator blockCreator = new BlockCreator() {

            private int height = height1;
            private int width = width1;
            private int hitPoint = hitPoint1;
            private Filling fill = fill1;
            private Map<Integer, Filling> fillsMAp = fillsMAp1;
            private Color stroke = stroke1;


            public Block create(int xpos, int ypos) {
                Block b = new Block(xpos, ypos, width, height, hitPoint);
                b.setFilling(fill);
                b.setFillByHitPoint(fillsMAp);
                if (stroke != null) {
                    b.setStroke(stroke);
                }
                return b;
            }
        };
        return blockCreator;
    }

    /**
     * Create spacer.
     *
     * @param line                     the line
     * @param blocksFromSymbolsFactory the blocks from symbols factory
     * @throws Exception the exception
     */
    private static void createSpacer(String line, BlocksFromSymbolsFactory blocksFromSymbolsFactory) throws Exception {
        String symbol = null;
        Integer width = null;
        String[] splitLine = line.split(" ");
        //read the line
        for (int i = 0; i < splitLine.length; i++) {
            if (splitLine[i].contains("symbol")) {
                symbol = splitLine[i].replace("symbol:", "");
            }
            if (splitLine[i].contains("width")) {
                width = Integer.valueOf(splitLine[i].replace("width:", ""));
            }
        }
        //create the spacer creator
        if (symbol != null && width != null) {
            blocksFromSymbolsFactory.addSpacerWidths(symbol, width);
        } else {
            throw new Exception("the arg in the spacer not good");
        }


    }


}
