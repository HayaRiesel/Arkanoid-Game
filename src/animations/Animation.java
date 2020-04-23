package animations;

import biuoop.DrawSurface;

/**
 * The interface Animation.
 */
public interface Animation {
    /**
     * Do one frame.
     *
     * @param d the d
     */
    void doOneFrame(DrawSurface d);

    /**
     * Return if the animation should stop.
     *
     * @return the boolean true- should stop, false- doesnwt need to stop
     */
    boolean shouldStop();
}