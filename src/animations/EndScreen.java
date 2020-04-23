package animations;

import biuoop.DrawSurface;
import indicators.Counter;

import javax.imageio.ImageIO;
import java.awt.Image;

/**
 * The type End screen.
 */
public class EndScreen implements Animation {

    private boolean stop;
    private Counter lives;
    private Counter score;

    /**
     * Instantiates a new End screen.
     *
     * @param lives the lives that left
     * @param score the score of the game
     */
    public EndScreen(Counter lives, Counter score) {
        this.stop = false;
        this.lives = lives;
        this.score = score;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        if (lives.getValue() == 0) {
            try {
                Image img = ImageIO.read(
                        ClassLoader.getSystemClassLoader().getResourceAsStream("background_images/lose.png"));
                img = img.getScaledInstance(900, 700, Image.SCALE_DEFAULT);
                d.drawImage(0, 0, img);
            } catch (Exception e) {
                e.printStackTrace();
            }
            d.drawText(20, d.getHeight() / 4, "Game Over. Your score is " + String.valueOf(score.getValue()), 32);

        } else {
            try {
                Image img = ImageIO.read(
                        ClassLoader.getSystemClassLoader().getResourceAsStream("background_images/win.png"));
                img = img.getScaledInstance(800, 670, Image.SCALE_DEFAULT);
                d.drawImage(0, 0, img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

}
