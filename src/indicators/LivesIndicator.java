package indicators;

import inerfaces.Sprite;
import basic.Rectangle;
import biuoop.DrawSurface;
import game.GameLevel;

import java.awt.Color;

/**
 * The type Lives indicator.
 */
public class LivesIndicator implements Sprite {
    private Rectangle rect;
    private Counter lives;

    /**
     * Instantiates a new Lives indicator.
     *
     * @param rect  the rect
     * @param lives the lives
     */
    public LivesIndicator(Rectangle rect, Counter lives) {
        this.rect = rect;
        this.lives = lives;
        rect.setColor(Color.LIGHT_GRAY);

    }

    /**
     * draw the sprite to the screen.
     *
     * @param d the gui drawSurface
     */
    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(100, (int) (this.rect.getMiddle().getY()) + 5,
                "LIVES: " + String.valueOf(lives.getValue()), 20);

    }


    /**
     * Add this ScoreIndicator to the game.
     *
     * @param g the game
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() {

    }
}
