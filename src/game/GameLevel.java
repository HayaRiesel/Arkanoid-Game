package game;

import animations.Animation;
import animations.PauseScreen;
import animations.AnimationRunner;
import animations.CountdownAnimation;
import animations.KeyPressStoppableAnimation;
import basic.Block;
import basic.Paddle;
import basic.Ball;
import basic.Point;
import basic.Rectangle;
import biuoop.DrawSurface;
import indicators.Counter;
import indicators.LevelNameIndicator;
import indicators.LivesIndicator;
import indicators.ScoreIndicator;
import inerfaces.Collidable;
import inerfaces.LevelInformation;
import inerfaces.Sprite;
import listeners.BallRemover;
import listeners.BlockRemover;
import listeners.ScoreTrackingListener;

import java.awt.Color;
import java.util.List;


/**
 * The type GameLevel.
 */
public class GameLevel implements Animation {
    private SpriteCollection sprites = new SpriteCollection();
    private GameEnvironment environment = new GameEnvironment();
    private biuoop.KeyboardSensor keyboard;


    private AnimationRunner runner;
    private boolean running;


    private Counter countBlock;
    private Counter countBall;
    private Counter score;
    private Counter countLives;

    private Paddle paddle;
    private LevelInformation levelInformation;

    /**
     * Instantiates a new Game level.
     *
     * @param levelInformation the level information
     * @param keyboard         the keyboard
     * @param runner           the runner
     * @param score            the score
     * @param lives            the lives
     */
    public GameLevel(LevelInformation levelInformation, biuoop.KeyboardSensor keyboard, AnimationRunner runner,
                     Counter score, Counter lives) {
        this.keyboard = keyboard;
        this.levelInformation = levelInformation;
        this.runner = runner;

        this.countLives = lives;
        this.score = score;

        this.countBlock = new Counter(levelInformation.numberOfBlocksToRemove());
        this.countBall = new Counter(0);


    }

    /**
     * Add collidable.
     *
     * @param c the collidable that need to be add
     */
    public void addCollidable(Collidable c) {
        this.environment.addCollidable(c);
    }

    /**
     * Remove collidable.
     *
     * @param c the collidable that need to e remove
     */
    public void removeCollidable(Collidable c) {
        this.environment.removeCollidable(c);
    }

    /**
     * Add sprite.
     *
     * @param s the sprite that needs to be added
     */
    public void addSprite(Sprite s) {
        this.sprites.addSprite(s);
    }

    /**
     * Remove sprite.
     *
     * @param s the sprite that needs to be removed
     */
    public void removeSprite(Sprite s) {
        this.sprites.removeSprite(s);
    }

    /**
     * Gets game enviroment.
     *
     * @return the game enviroment
     */
    public GameEnvironment getGameEnviroment() {
        return this.environment;
    }

    /**
     * Initialize a new game: create the Blocks and add them to the game.
     */
    public void initialize() {

        //add background
        levelInformation.getBackground().addToGame(this);


        //build the limits of the game screen
        Block b1 = new Block(0, 0, 30, 600, 0, Color.GRAY);
        b1.addToGame(this);
        Block b2 = new Block(0, 40, 800, 30, 0, Color.GRAY);
        b2.addToGame(this);
        Block b3 = new Block(770, 0, 30, 600, 0, Color.GRAY);
        b3.addToGame(this);


        //create listener
        ScoreTrackingListener scoreTrackingListener = new ScoreTrackingListener(this.score);
        BallRemover ballRemoverListener = new BallRemover(this, this.countBall);
        BlockRemover blockRemoverListener = new BlockRemover(this, this.countBlock);

        //create the deathRegion for the balls
        Block deathRegion = new Block(-100, 605, 1000, 30, 0);
        addCollidable(deathRegion);
        deathRegion.addHitListener(ballRemoverListener);

        //add the listener to the regular block
        List<Block> copyBlocks = this.levelInformation.blocks();
        for (Block b : copyBlocks) {
            b.addHitListener(blockRemoverListener);
            b.addHitListener(scoreTrackingListener);
            b.addToGame(this);
        }

        //add score, levelName and live Indicator
        Block indicator = new Block(0, 0, 1000, 40, 0);
        this.addSprite(indicator);
        ScoreIndicator scoreIndicator = new ScoreIndicator(indicator.getCollisionRectangle(), this.score);
        scoreIndicator.addToGame(this);
        LivesIndicator livesIndicator = new LivesIndicator(indicator.getCollisionRectangle(), this.countLives);
        livesIndicator.addToGame(this);
        LevelNameIndicator nameLevel = new LevelNameIndicator(indicator.getCollisionRectangle(),
                this.levelInformation.levelName());
        nameLevel.addToGame(this);

    }

    /**
     * Return the numer of the Block in the game.
     *
     * @return countBlock the value.
     */
    public int getCountBlock() {
        return this.countBlock.getValue();
    }

    /**
     * Play one turn.
     */
    public void playOneTurn() {
        //create the balls and the paddle
        this.createBallsOnTopOfPaddle();
        // countdown before the level starts.
        this.runner.run(new CountdownAnimation(2, 3, this.sprites));

        //initialize the running
        this.running = true;
        while (!this.shouldStop()) {
            //problem
            // use runner to run the current animation -- which is one turn of the game.
            this.runner.run(new KeyPressStoppableAnimation(keyboard, "p", this));
            //run the pause screen
            if (keyboard.isPressed("p")) {
                this.runner.run(new KeyPressStoppableAnimation(this.keyboard, "space", new PauseScreen()));
            }
        }
        paddle.removeFromGame(this);
    }

    @Override
    public boolean shouldStop() {
        return !this.running;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        //draw and notify all the sprite
        this.sprites.drawAllOn(d);
        this.sprites.notifyAllTimePassed();

        //if there is no balls in the game - decrease the lives in 1 ans stop the animation (this turn)
        if (this.countBall.getValue() == 0) {
            this.countLives.decrease(1);
            this.running = false;
        }
        //if there is no blocks in the game - update the score and stop the animation (this turn)
        if (this.countBlock.getValue() == 0) {
            //add 100 points when all the blocks are removed
            this.score.increase(100);
            this.running = false;
        }
    }

    /**
     * Create balls on top of paddle.
     */
    public void createBallsOnTopOfPaddle() {
        //create i balls
        for (int i = 0; i < levelInformation.numberOfBalls(); i++) {
            Ball ball = new Ball(new Point(400, 500), 5, Color.BLUE);
            ball.setVelocity(levelInformation.initialBallVelocities().get(i));
            ball.addToGame(this);
            //add 1 to the counter
            countBall.increase(1);
        }
        //create paddle
        paddle = new Paddle(this.keyboard,
                new Rectangle(
                        new Point(400 - (levelInformation.paddleWidth() / 2), 570),
                        levelInformation.paddleWidth(), 20),
                levelInformation.paddleSpeed());
        paddle.addToGame(this);
        paddle.setLimits(30, 770);

    }

}