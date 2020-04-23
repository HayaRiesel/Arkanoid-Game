package animations;

import biuoop.DrawSurface;

/**
 * The type Pause screen.
 */
public class PauseScreen implements Animation {
    private boolean stop;

    /**
     * Instantiates a new Pause screen.
     *
     */
    public PauseScreen() {
        this.stop = false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        d.drawText(10, d.getHeight() / 2, "paused -- press space to continue", 32);
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }
}