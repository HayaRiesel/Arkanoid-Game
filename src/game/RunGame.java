package game;

import animations.Animation;
import animations.KeyPressStoppableAnimation;
import animations.AnimationRunner;
import animations.ShowAnimation;
import animations.QuitAnimation;

import biuoop.KeyboardSensor;
import highscore.HighScoresAnimation;
import highscore.HighScoresTable;
import menu.Menu;
import menu.MenuAnimation;
import menu.Task;
import reading.ReadLevelSet;

import java.io.InputStream;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * The type Run game.
 */
public class RunGame {
    private AnimationRunner ar;
    private KeyboardSensor ks;
    private HighScoresTable scoresTable;
    private InputStream inputStream;
    //private List<LevelInformation> levels;


    /**
     * Instantiates a new Run game.
     *
     * @param ar          the AnimationRunner
     * @param ks          the KeyboardSensor
     * @param inputStream the inputStream
     */
    public RunGame(AnimationRunner ar, KeyboardSensor ks, InputStream inputStream) {
        this.ar = ar;
        this.ks = ks;
        this.scoresTable = new HighScoresTable(10);
        this.inputStream = inputStream;


    }

    /**
     * Run the menu.
     */
    public void run() {


        loadScoreTable();
        //create menu
        Menu<Task<Void>> menu = new MenuAnimation<Task<Void>>("Arkanoid:", this.ks, ar);
        Animation hiScore = new KeyPressStoppableAnimation(this.ks, "space", new HighScoresAnimation(scoresTable));
        menu.addSelection("h", "High scores", new ShowAnimation().getTask(ar, hiScore));
        menu.addSelection("q", "quit", new ShowAnimation().getTask(ar, new QuitAnimation()));
        //create submenu
        Menu<Task<Void>> subMenu = new MenuAnimation<Task<Void>>("Level Sets:", this.ks, ar);
        //read the level set and make y this the selection of the submenu
        ReadLevelSet readLevelSet = new ReadLevelSet();
        try {
            readLevelSet.fromReader(new InputStreamReader(inputStream), subMenu, ar, ks, scoresTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        menu.addSubMenu("s", "start the game", subMenu);


        while (true) {
            // wait for user selection
            ar.run(menu);
            //run the user selection
            Task<Void> task = menu.getStatus();
            task.run();
        }

    }

    /**
     * Load score table.
     */
    public void loadScoreTable() {
        Path path = Paths.get("highscores");
        File fl = new File("highscores");

        if (Files.exists(path)) {
            // the table read from that file.
            this.scoresTable = HighScoresTable.loadFromFile(fl);
        }
        //If high-scores file does not exist, and we create a new table and immediately save it to a file
        if (Files.notExists(path)) {
            try {
                this.scoresTable.save(fl);
            } catch (Exception e2) {
                e2.printStackTrace();

            }

        }

    }

}
