package listeners;

import basic.Ball;
import basic.Block;
import game.GameLevel;
import indicators.Counter;

/**
 * The type Ball remover.
 * a BlockRemover is in charge of removing blocks from the gameLevel, as well as keeping count
 * of the number of blocks that remain.
 */
public class BallRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBalls;

    /**
     * Instantiates a new Ball remover.
     *
     * @param gameLevel           the gameLevel
     * @param remainingBalls the counter that count the remaining balls
     */
    public BallRemover(GameLevel gameLevel, Counter remainingBalls) {
        this.gameLevel = gameLevel;
        this.remainingBalls = remainingBalls;
    }
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        //all the balls that are hit the deadBlock should be removed from the gameLevel
        hitter.removeFromGame(this.gameLevel);
        //decrease 1 from the count of the numbers of balls in the gameLevel
        this.remainingBalls.decrease(1);
    }
}
