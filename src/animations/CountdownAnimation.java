package animations;

import biuoop.DrawSurface;
import game.SpriteCollection;

import java.awt.Color;

/**
 * The CountdownAnimation will display the given gameScreen
 * and  will show a countdown from countFrom back to 1.
 */
public class CountdownAnimation implements Animation {

    private double numOfSeconds;
    private int countFrom;
    private SpriteCollection gameScreen;
    private int numCounter;
    private long startTime;


    /**
     * Instantiates a new Countdown animation.
     *
     * @param numOfSeconds the num of seconds
     * @param countFrom    the count from
     * @param gameScreen   the game screen
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        // timing
        this.startTime = System.currentTimeMillis();
        this.numCounter = countFrom;

    }

    @Override
    public void doOneFrame(DrawSurface d) {
        //display the given gameScreen
        gameScreen.drawAllOn(d);
        d.setColor(Color.BLUE);
        //draw the count down
        d.drawText(390, 120, String.valueOf(numCounter), 40);

        //Calculates the time per count
        double timeForOneCountMillis = (numOfSeconds / countFrom) * 1000;
        //Calculates the time since the beginning of the one count
        long usedTime = System.currentTimeMillis() - startTime;
        if (timeForOneCountMillis < usedTime) {
            //update the first time for know how much time the next num should show
            this.startTime = System.currentTimeMillis();
            //update for the next count
            numCounter--;
        }

    }

    @Override
    public boolean shouldStop() {
        if (numCounter == 0) {
            return true;
        }
        return false;
    }
}