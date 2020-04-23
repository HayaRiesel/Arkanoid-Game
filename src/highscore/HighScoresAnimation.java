package highscore;

import animations.Animation;
import biuoop.DrawSurface;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Color;

/**
 * The type High scores animation.
 */
public class HighScoresAnimation implements Animation {
    private HighScoresTable scores;

    /**
     * Instantiates a new High scores animation.
     *
     * @param scores the scores
     */
    public HighScoresAnimation(HighScoresTable scores) {
        this.scores = scores;

    }

    @Override
    public void doOneFrame(DrawSurface d) {
        try {
            Image img = ImageIO.read(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("background_images/menu.jpg"));
            img = img.getScaledInstance(800, 600, Image.SCALE_DEFAULT);
            d.drawImage(0, 0, img);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //drawing the table
        int startX = 260;
        int startY = 200;
        //if the table is empty shw this message
        if (scores.getHighScores().isEmpty()) {
            d.drawText(startX - 100, startY + 40, "the high score table is empty right now", 30);
        }
        d.setColor(new Color(145, 179, 175));
        d.fillRectangle(startX - 20, startY - 30, 300, 40 * this.scores.getHighScores().size());
        d.setColor(Color.BLACK);
        for (int i = 0; i < this.scores.getHighScores().size(); i++) {
            d.drawText(startX, startY + 40 * i, this.scores.getHighScores().get(i).getName(), 30);
            d.drawText(startX + 200,
                    startY + 40 * i,
                    String.valueOf(this.scores.getHighScores().get(i).getScore()),
                    30);

        }

    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
