package indicators;

import basic.Rectangle;
import biuoop.DrawSurface;
import game.GameLevel;
import inerfaces.Sprite;

import java.awt.Color;

/**
 * The type Level name indicator.
 */
public class LevelNameIndicator implements Sprite {
    private Rectangle rect;
    private String levelName;

    /**
     * Instantiates a new Lives indicator.
     *
     * @param rect      the rect
     * @param levelName the level name
     */
    public LevelNameIndicator(Rectangle rect, String levelName) {
        this.rect = rect;
        this.levelName = levelName;

    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);

        d.drawText(500, (int) (this.rect.getMiddle().getY()) + 5,
                "Level: " + this.levelName, 20);

    }


    @Override
    public void addToGame(GameLevel g) {
        g.addSprite(this);
    }

    @Override
    public void timePassed() {

    }

}
