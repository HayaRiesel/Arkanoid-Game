import animations.AnimationRunner;
import game.RunGame;

import java.io.InputStream;

/**
 * The type Ass 7.
 */
public class Ass7game {
    /**
     * The entry point of the play.
     *
     * @param args the input arguments the numbers of the level need to run
     */
    public static void main(String[] args) {
        AnimationRunner runner = new AnimationRunner(60);
        //choose the path to the resources folder
        InputStream is;
        if (args.length == 0) {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream("level_sets.txt");
            //is = new InputStreamReader(InputStream("resources/level_sets.txt"));
        } else {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(args[0]);

        }
        //ren the game
        RunGame game = new RunGame(runner, runner.getKeyboard(), is);
        game.run();


    }
}
