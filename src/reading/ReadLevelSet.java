package reading;

import inerfaces.LevelInformation;
import biuoop.KeyboardSensor;
import game.GameFlow;
import animations.AnimationRunner;
import highscore.HighScoresTable;
import menu.Menu;
import menu.Task;

import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.Reader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Read level set.
 */
public class ReadLevelSet {

    /**
     * From reader.
     *
     * @param reader      the reader
     * @param menu        the menu
     * @param ar          the ar
     * @param ks          the ks
     * @param scoresTable the scores table
     */
    public void fromReader(Reader reader,
                           Menu<Task<Void>> menu,
                           AnimationRunner ar,
                           KeyboardSensor ks,
                           HighScoresTable scoresTable) {
        LineNumberReader is = null;
        //list of the level. each level represent by list of line
        List<List<String>> levelsInString = new ArrayList<List<String>>();
        try {
            is = new LineNumberReader(new BufferedReader(reader));
            String line;
            int i = 0;
            String key = null;
            String message = null;
            Task<Void> task = new Task<Void>() {
                @Override
                public Void run() {
                    return null;
                }
            };

            while ((line = is.readLine()) != null) {
                //Odd-numbered lines are level names
                if (is.getLineNumber() % 2 == 1) {
                    //example:  e:Easy
                    key = line.split(":")[0];
                    message = line.split(":")[1];
                }
                //even-numbered lines are the corresponding filenames, containing the level specifications
                if (is.getLineNumber() % 2 == 0) {
                    LevelSpecificationReader levelreader = new LevelSpecificationReader();
                    try {
                        InputStream readerLevels = ClassLoader.getSystemClassLoader().getResourceAsStream(line);
                        List<LevelInformation> levelslist = levelreader.fromReader(new InputStreamReader(readerLevels));
                        task = new GameFlow(ar, ks, levelslist, scoresTable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    menu.addSelection(key, message, task);
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}