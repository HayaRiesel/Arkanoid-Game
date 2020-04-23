package animations;

import biuoop.DrawSurface;

/**
 * The type Quit animation.
 */
public class QuitAnimation implements Animation {

    @Override
    public void doOneFrame(DrawSurface d) {
        System.exit(0);

    }

    @Override
    public boolean shouldStop() {
        return false;
    }
}
