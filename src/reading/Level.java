package reading;


import inerfaces.LevelInformation;
import inerfaces.Sprite;
import basic.Background;
import basic.Velocity;
import basic.Block;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Level.
 */
public class Level implements LevelInformation {

    //level_name specified the name of the level.
    private String levelName;
    //ball_velocities specifies the ball velocities.
    // This field is a space-separated list of items,
    // each item is of the form a,s where s is the speed and a is the angle.
    private List<Velocity> ballVelocities = new ArrayList<Velocity>();
    //background specifies the level's background.
    // The format of its value is exactly the same as for the fill property
    // when defining a block so it should support the two color formats and the image format.
    private Sprite background;
    //paddle speed specifies the paddle speed.
    private int paddleSpeed;
    //paddle width specifies the paddle width.
    private int paddleWidth;
    private List<Block> blocks = new ArrayList<Block>();
    private Integer numberOfBlocksToRemove;

    /**
     * Instantiates a new Level.
     */
    public Level() {

    }

    /**
     * Instantiates a new Level.
     *
     * @param levelName              the level name
     * @param ballVelocities         the ball velocities
     * @param background             the background
     * @param paddleSpeed            the paddle speed
     * @param paddleWidth            the paddle width
     * @param blocks                 the blocks
     * @param numberOfBlocksToRemove the number of blocks to remove
     */
    public Level(String levelName,
                 List<Velocity> ballVelocities,
                 Background background,
                 int paddleSpeed,
                 int paddleWidth,
                 List<Block> blocks,
                 Integer numberOfBlocksToRemove) {
        this.background = background;
        this.ballVelocities = ballVelocities;
        this.blocks = blocks;
        this.levelName = levelName;
        this.numberOfBlocksToRemove = numberOfBlocksToRemove;
        this.paddleSpeed = paddleSpeed;
        this.paddleWidth = paddleWidth;
    }


    /**
     * Instantiates a new Level.
     *
     * @param another the another
     */
    public Level(LevelInformation another) {
        this.background = another.getBackground();
        this.ballVelocities = another.initialBallVelocities();
        //only te blocks change uring the game so we need to copy only them
        for (Block b : another.blocks()) {
            this.blocks.add(new Block(b));
        }
        this.levelName = another.levelName();
        this.numberOfBlocksToRemove = another.numberOfBlocksToRemove();
        this.paddleSpeed = another.paddleSpeed();
        this.paddleWidth = another.paddleWidth();
    }

    @Override
    public int numberOfBalls() {
        return this.ballVelocities.size();
    }

    @Override
    public List<Velocity> initialBallVelocities() {
        return this.ballVelocities;
    }

    /**
     * Sets ball velocities.
     *
     * @param velocities the velocities
     */
    public void setBallVelocities(List<Velocity> velocities) {
        this.ballVelocities = velocities;
    }


    @Override
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * Sets paddle speed.
     *
     * @param pSpeed the p speed
     */
    public void setPaddleSpeed(int pSpeed) {
        this.paddleSpeed = pSpeed;
    }

    @Override
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * Sets paddle width.
     *
     * @param pWidth the p width
     */
    public void setPaddleWidth(int pWidth) {
        this.paddleWidth = pWidth;
    }

    @Override
    public String levelName() {
        return this.levelName;
    }

    /**
     * Sets level name.
     *
     * @param levName the lev name
     */
    public void setLevelName(String levName) {
        this.levelName = levName;
    }

    @Override
    public Sprite getBackground() {
        return this.background;
    }

    /**
     * Sets background.
     *
     * @param b the b
     */
    public void setBackground(Background b) {
        this.background = b;
    }

    @Override
    public List<Block> blocks() {
        return blocks;
    }

    /**
     * Add block.
     *
     * @param b the b
     */
    public void addBlock(Block b) {
        this.blocks.add(b);
    }


    @Override
    public int numberOfBlocksToRemove() {
        return this.numberOfBlocksToRemove;
    }

    /**
     * Sets number of blocks to remove.
     *
     * @param numBlock the num block
     */
    public void setNumberOfBlocksToRemove(int numBlock) {
        this.numberOfBlocksToRemove = numBlock;
    }


}
