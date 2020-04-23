package highscore;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.FileOutputStream;

/**
 * The type High scores table.
 */
public class HighScoresTable {
    private List<ScoreInfo> scoreInfos = new ArrayList<ScoreInfo>();
    private int size;

    /**
     * Create an empty high-scores table with the specified size.
     * @param size The size means that the table holds up to size top scores
     */
    public HighScoresTable(int size) {
        this.size = size;
    }

    /**
     * Add a high-score.
     *
     * @param score the score
     */
    public void add(ScoreInfo score) {
        if (getRank(score.getScore()) < size - 1) {
            this.scoreInfos.add(getRank(score.getScore()) - 1, score);

        }
    }

    /**
     * Return table size.
     *
     * @return the int table size
     */
    public int size() {
        return this.size;
    }

    /**
     * Gets high scores list.
     * The list is sorted such that the highest cores come first.
     *
     * @return the high scores
     */
    public List<ScoreInfo> getHighScores() {
        return this.scoreInfos;
    }

    /**
     * return the rank of the current score: where will it be on the list if added.
     * @param score the score
     * @return the rank of the score
     */
    public int getRank(int score) {
        //if the table is empty it will be the first
        if (this.scoreInfos.isEmpty()) {
            return 1;
        }
        for (int i = 0; i < this.scoreInfos.size(); i++) {
            if (score > this.scoreInfos.get(i).getScore()) {
                return i + 1;
            }
        }
        //the rank is the end of the list
        return this.scoreInfos.size() + 1;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.scoreInfos = new ArrayList<ScoreInfo>();
    }

    /**
     * Load table data from file.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void load(File filename) throws IOException {
        try {
            boolean cont = true;
            ObjectInputStream oos = new ObjectInputStream(new FileInputStream(filename));
            while (cont) {
                try {
                    ScoreInfo obj = (ScoreInfo) (oos.readObject());
                    if (obj != null) {
                        add(obj);
                    } else {
                        cont = false;
                    }

                } catch (java.io.EOFException e) {
                    cont = false;
                }
            }
            oos.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Save.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
// Save table data to the specified file.
    public void save(File filename) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
        for (int i = 0; i < this.scoreInfos.size(); i++) {
            oos.writeObject(this.scoreInfos.get(i));
        }
        oos.close();
    }

    /**
     * Load from file high scores table.
     *
     * @param filename the filename
     * @return the high scores table
     */
// Read a table from file and return it.
    // If the file does not exist, or there is a problem with
    // reading it, an empty table is returned.
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable highScoresTable = new HighScoresTable(10);

        try {
            highScoresTable.load(filename);
            return highScoresTable;

        } catch (IOException ex) {
            return new HighScoresTable(10);
        }
    }


}
