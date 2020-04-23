package inerfaces;

import basic.Block;
import basic.Velocity;

import java.util.List;

/**
 * The interface Level information.
 */
public interface LevelInformation {
    /**
     * Number of balls.
     *
     * @return the int number
     */
    int numberOfBalls();

    /**
     * Initial ball velocities list of each ball.
     *
     * @return the list
     */
    List<Velocity> initialBallVelocities();

    /**
     * Paddle speed int.
     *
     * @return the int
     */
    int paddleSpeed();

    /**
     * Paddle width int.
     *
     * @return the int
     */
    int paddleWidth();

    /**
     * Level name string.
     *
     * @return the string
     */
    String levelName();

    /**
     * Returns a sprite with the background of the level.
     *
     * @return the background
     */
    Sprite getBackground();

    /**
     * The Blocks that make up this level, each block contains its size, color and location.
     *
     * @return the list
     */
    List<Block> blocks();

    /**
     * Number of levels that should be removed before the level is considered to be "cleared".
     *
     * @return the int
     */
    int numberOfBlocksToRemove();

    }