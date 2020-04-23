package indicators;

import biuoop.DrawSurface;
import game.GameLevel;
import basic.Rectangle;
import inerfaces.Sprite;

import java.awt.Color;

/**
 * The type Score indicator.
 */
public class ScoreIndicator implements Sprite {
    private Rectangle rect;
    private Counter score;

    /**
     * Instantiates a new Score indicator.
     *
     * @param rect  the rectangle - the place of the ScoreIndicator
     * @param score the score
     */
    public ScoreIndicator(Rectangle rect, Counter score) {
        this.rect = rect;
        this.score = score;
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(300, (int) (this.rect.getMiddle().getY()) + 5,
                "SCORE: " + String.valueOf(score.getValue()), 20);


    }

    /**
     * Add this ScoreIndicator to the game.
     *
     * @param g the g
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * notify the sprite that time has passed.
     */
    @Override
    public void timePassed() {

    }
}
