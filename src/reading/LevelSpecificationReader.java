package reading;

import basic.Background;
import basic.Block;
import basic.Velocity;
import inerfaces.LevelInformation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.io.InputStreamReader;

/**
 * The type Level specification reader.
 */
public class LevelSpecificationReader {
    private List<LevelInformation> levels = new ArrayList<LevelInformation>();
    private Level currentLevel = new Level();

    private int blocksStartX;
    private int blocksStartY;
    private int rowHeight;
    private BlocksFromSymbolsFactory blocksFromSymbolsFactory;


    /**
     * From reader list.
     *
     * @param reader the reader
     * @return the list
     */
    public List<LevelInformation> fromReader(Reader reader) {
        BufferedReader is = null;
        //list of the level. each level represent by list of line
        List<List<String>> levelsInString = new ArrayList<List<String>>();
        try {
            is = new BufferedReader(reader);

            String line;
            int i = 0;
            while ((line = is.readLine()) != null) {
                if (line.equals("START_LEVEL")) {
                    //add new level
                    levelsInString.add(new ArrayList<String>());
                    continue;
                }
                if (line.equals("END_LEVEL")) {
                    i++;
                    continue;
                }
                if (line.startsWith("#") || line.isEmpty()) {
                    continue;
                }
                //ad the ine to the level
                levelsInString.get(i).add(line);
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
        //pass on all the levels and analyse
        for (int i = 0; i < levelsInString.size(); i++) {
            //move the string level to the currentLevel
            levelAnalysis(levelsInString.get(i), i);
            levels.add(this.currentLevel);

            this.currentLevel = new Level();


        }
        //return the list of the levels
        return this.levels;
    }

    /**
     * Level analysis.
     *
     * @param level the level in string
     * @param i     the place of the level in the list
     */
    public void levelAnalysis(List<String> level, int i) {
        //pass on all the lines and analyse
        int j;
        for (j = 0; j < level.size(); j++) {
            if (level.get(j).startsWith("START_BLOCKS")) {
                //because j++ in the start
                int k = 0;
                while (!level.get(j).startsWith("END_BLOCKS")) {
                    j++;
                    symbolRowAnalysis(level.get(j).toCharArray(), k);

                    k++;
                }
            }
            lineAnalysis(level.get(j), i);
        }


    }

    /**
     * Line analysis.
     *
     * @param line the line
     * @param i    the
     */
    public void lineAnalysis(String line, int i) {
        //enter the right if, split the string in the way it need and put it in the right var
        if (line.startsWith("level_name:")) {
            this.currentLevel.setLevelName(line.replace("level_name:", ""));
        }
        if (line.startsWith("ball_velocities:")) {
            String allTheVelocityInString = line.replace("ball_velocities:", "");
            String[] velocityInString = allTheVelocityInString.split(" ", allTheVelocityInString.length());
            List<Velocity> ballVelocities = new ArrayList<Velocity>();
            for (int j = 0; j < velocityInString.length; j++) {
                String[] v = velocityInString[j].split(",");
                ballVelocities.add(Velocity.fromAngleAndSpeed(Integer.valueOf(v[0]), Integer.valueOf(v[1])));
            }
            this.currentLevel.setBallVelocities(ballVelocities);
        }
        if (line.startsWith("background:")) {
            String lineAfterChange = line.replace("background:", "");
            this.currentLevel.setBackground(new Background(ColorsParser.fillingFromString(lineAfterChange)));
        }
        if (line.startsWith("paddle_speed:")) {
            String paddleSpeedInStr = line.replace("paddle_speed:", "");
            this.currentLevel.setPaddleSpeed(Integer.valueOf(paddleSpeedInStr));
        }
        if (line.startsWith("paddle_width:")) {
            String paddleWidthInStr = line.replace("paddle_width:", "");
            this.currentLevel.setPaddleWidth(Integer.valueOf(paddleWidthInStr));
        }
        if (line.startsWith("block_definitions:")) {
            String blockTxtInStr = line.replace("block_definitions:", "");
            try {
                InputStreamReader is = new InputStreamReader(
                        ClassLoader.getSystemClassLoader().getResourceAsStream(blockTxtInStr));
                //FileReader reader = new FileReader(new File(blockTxtInStr));
                this.blocksFromSymbolsFactory = BlocksDefinitionReader.fromReader(is);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //blocks_start_x:25
        if (line.startsWith("blocks_start_x:")) {
            String newStr = line.replace("blocks_start_x:", "");
            this.blocksStartX = Integer.valueOf(newStr);
        }
        //blocks_start_y:80
        if (line.startsWith("blocks_start_y:")) {
            String newStr = line.replace("blocks_start_y:", "");
            this.blocksStartY = Integer.valueOf(newStr);
        }

        //row_height:50
        if (line.startsWith("row_height:")) {
            String newStr = line.replace("row_height:", "");
            this.rowHeight = Integer.valueOf(newStr);
        }

        //num_blocks:5
        if (line.startsWith("num_blocks:")) {
            String newStr = line.replace("num_blocks:", "");
            this.currentLevel.setNumberOfBlocksToRemove(Integer.valueOf(newStr));
        }


    }


    /**
     * make from symbol block or wight.
     *
     * @param symbolRow the symbol row
     * @param numRow    the num row
     */
    private void symbolRowAnalysis(char[] symbolRow, int numRow) {
        int nextBlockX = this.blocksStartX;
        for (int i = 0; i < symbolRow.length; i++) {
            String symbol = Character.toString(symbolRow[i]);

            if (this.blocksFromSymbolsFactory.isBlockSymbol(symbol)) {
                Block b = this.blocksFromSymbolsFactory.getBlock(
                        symbol, nextBlockX, this.blocksStartY + this.rowHeight * numRow);
                this.currentLevel.addBlock(b);
                nextBlockX += b.getCollisionRectangle().getWidth();

            }
            if (this.blocksFromSymbolsFactory.isSpaceSymbol(symbol)) {
                nextBlockX += this.blocksFromSymbolsFactory.getSpaceWidth(symbol);

            }

        }
    }
}
