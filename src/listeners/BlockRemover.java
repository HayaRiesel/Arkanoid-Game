package listeners;

import basic.Ball;
import basic.Block;
import game.GameLevel;
import indicators.Counter;

/**
 * The type Block remover.
 * a BlockRemover is in charge of removing blocks from the gameLevel, as well as keeping count
 * of the number of blocks that remain.
 */
public class BlockRemover implements HitListener {
    private GameLevel gameLevel;
    private Counter remainingBlocks;

    /**
     * Instantiates a new Block remover.
     *
     * @param gameLevel          the gameLevel
     * @param removedBlocks the removed blocks
     */
    public BlockRemover(GameLevel gameLevel, Counter removedBlocks) {
        this.gameLevel = gameLevel;
        this.remainingBlocks = removedBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        //first update the listener and after that the hit point (because that the iht point==1)
        if (beingHit.getHitPoints() == 1) {
            //remove this listener from the block
            beingHit.removeHitListener(this);
            //Blocks that are hit and reach 0 hit-points should be removed
            beingHit.removeFromGame(this.gameLevel);
            this.remainingBlocks.decrease(1);
        }
    }
}