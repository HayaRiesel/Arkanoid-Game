package animations;

import biuoop.DialogManager;
import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/**
 * The type Animation runner.
 */
public class AnimationRunner {
    private int framesPerSecond;
    private Sleeper sleeper = new Sleeper();
    private GUI gui = new GUI("GAME", 800, 600);
    private biuoop.KeyboardSensor keyboard = gui.getKeyboardSensor();


    /**
     * Instantiates a new Animation runner.
     *
     * @param framesPerSecond how much frames per second
     */
    public AnimationRunner(int framesPerSecond) {
        this.framesPerSecond = framesPerSecond;
    }

    /**
     * Run animation.
     *
     * @param animation the animation that need to be run
     */
    public void run(Animation animation) {

        int millisecondsPerFrame = 1000 / framesPerSecond;
        while (!animation.shouldStop()) {
            long startTime = System.currentTimeMillis(); // timing
            DrawSurface d = gui.getDrawSurface();

            animation.doOneFrame(d);

            gui.show(d);
            long usedTime = System.currentTimeMillis() - startTime;
            long milliSecondLeftToSleep = millisecondsPerFrame - usedTime;
            if (milliSecondLeftToSleep > 0) {
                this.sleeper.sleepFor(milliSecondLeftToSleep);
            }

        }
    }


    /**
     * Gets keyboard.
     *
     * @return the keyboard
     */
    public biuoop.KeyboardSensor getKeyboard() {
        return keyboard;
    }

    /**
     * Ask for name string with DialogManager.
     *
     * @return the string of the name
     */
    public String askForName() {
        DialogManager dialog = gui.getDialogManager();
        String name = dialog.showQuestionDialog("Name", "What is your name?", "");
        return name;

    }
}