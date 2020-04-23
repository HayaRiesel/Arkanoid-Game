package game;

import inerfaces.Sprite;
import biuoop.DrawSurface;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Sprite collection.
 */
public class SpriteCollection {
    private List<Sprite> sprites;

    /**
     * Instantiates a new Sprite collection.
     */
    public SpriteCollection() {
        this.sprites = new ArrayList<Sprite>();

    }

    /**
     * Add sprite.
     *
     * @param s the sprite that need to be had
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    /**
     * Remove sprite.
     *
     * @param s the sprite that need to be remove
     */
    public void removeSprite(Sprite s) {
        sprites.remove(s);
    }


    /**
     * call timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        // Make a copy of the sprites before iterating over them.
        List<Sprite> spritesCopy = new ArrayList<Sprite>(this.sprites);

        for (Sprite s : spritesCopy) {
            s.timePassed();
        }
    }

    /**
     * call drawOn(d) on all sprites.
     *
     * @param d the gui drawSurface.
     */
    public void drawAllOn(DrawSurface d) {
        // Make a copy of the sprites before iterating over them.
        List<Sprite> spritesCopy = new ArrayList<Sprite>(this.sprites);

        for (Sprite s : spritesCopy) {
            s.drawOn(d);
        }
    }
}