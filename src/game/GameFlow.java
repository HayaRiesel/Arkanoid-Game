package game;

import inerfaces.LevelInformation;
import animations.KeyPressStoppableAnimation;
import biuoop.KeyboardSensor;
import animations.AnimationRunner;
import animations.EndScreen;
import highscore.HighScoresAnimation;
import highscore.HighScoresTable;
import highscore.ScoreInfo;
import indicators.Counter;
import menu.Task;
import reading.Level;

import java.io.File;
import java.util.List;

/**
 * The type Game flow.
 */
public class GameFlow implements Task<Void> {
    private AnimationRunner ar;
    private KeyboardSensor ks;
    private List<LevelInformation> levels;
    private HighScoresTable scoresTable;

    private Counter score;
    private Counter countLives;


    /**
     * Instantiates a new Game flow.
     *
     * @param ar          the animation runner
     * @param ks          the keyboard Sensor
     * @param levels      the levels
     * @param scoresTable the scores table
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, List<LevelInformation> levels, HighScoresTable scoresTable) {
        this.ar = ar;
        this.ks = ks;
        this.levels = levels;
        this.scoresTable = scoresTable;
    }

    /**
     * Run the game.
     *
     * @return Void
     */
    public Void run() {

        score = new Counter(0);
        countLives = new Counter(7);
        List<LevelInformation> copyLevelsList = levels;
        for (LevelInformation levelInfo : copyLevelsList) {
            //copy the level before send it to the GameLevel (the blocks change in it)
            LevelInformation copyLevel = new Level(levelInfo);

            GameLevel level = new GameLevel(copyLevel, this.ks, this.ar, this.score, this.countLives);

            level.initialize();

            //if there is no lives  to the game - stop the game
            while (this.countLives.getValue() != 0) {
                //if there is no blocks in the level - stop the level
                if (level.getCountBlock() == 0) {
                    break;
                }
                level.playOneTurn();
            }

        }
        //run the end screen
        ar.run(new KeyPressStoppableAnimation(this.ks, "space", new EndScreen(countLives, score)));

        //check if the player get to the score table
        if (scoresTable.getRank(score.getValue()) < scoresTable.size() - 1) {
            String name = ar.askForName();
            scoresTable.add(new ScoreInfo(name, this.score.getValue()));

            //save
            try {
                scoresTable.save(new File("highscores"));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        //run the HighScore animation
        ar.run(new KeyPressStoppableAnimation(this.ks, "space", new HighScoresAnimation(scoresTable)));
        return null;

    }

}