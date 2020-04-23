package inerfaces;

import biuoop.DrawSurface;
import game.GameLevel;

/**
 * The interface Sprite.
 */
public interface Sprite {
    /**
     * draw the sprite to the screen.
     *
     * @param d the gui drawSurface
     */
    void drawOn(DrawSurface d);

    /**
     * notify the sprite that time has passed.
     */
    void timePassed();

    /**
     * Add to the game.
     *
     * @param gameLevel the game level
     */
    void addToGame(GameLevel gameLevel);
}