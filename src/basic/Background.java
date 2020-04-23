package basic;

import biuoop.DrawSurface;
import game.GameLevel;
import inerfaces.Sprite;

import java.awt.Color;
import java.awt.Image;


/**
 * Make Background for Levels.
 */
public class Background implements Sprite {
    private Color backgroundColor;
    private Image backgroundImage;

    /**
     * Instantiates a new Background.
     *
     * @param backgroundColor the background color
     */
    public Background(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    /**
     * Instantiates a new Background.
     *
     * @param backgroundFilling the background filling
     */
    public Background(Filling backgroundFilling) {
        if (backgroundFilling.isColor()) {
            this.backgroundColor = backgroundFilling.getColor();
        }
        if (backgroundFilling.isImage()) {
            this.backgroundImage = backgroundFilling.getImage().getScaledInstance(
                    800, 600, Image.SCALE_DEFAULT);
        }
    }


    /**
     * Instantiates a new Background.
     *
     * @param img the img
     */
    public Background(Image img) {
        this.backgroundImage = img.getScaledInstance(800, 600, Image.SCALE_DEFAULT);
    }



    @Override
    public void drawOn(DrawSurface d) {
        // draw the image at the background
        if (this.backgroundImage != null) {
            d.drawImage(0, 0, this.backgroundImage);
            return;
        }
        //draw te color of the background
        d.setColor(this.backgroundColor);
        //draw all the shape in the background
        d.fillRectangle(0, 0, d.getWidth(), d.getHeight());
    }

    @Override
    public void timePassed() {

    }

    @Override
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
    }
}
