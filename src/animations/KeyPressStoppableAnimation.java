package animations;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/**
 * The type Key press stoppable animation.
 */
public class KeyPressStoppableAnimation implements Animation {
    private KeyboardSensor sensor;
    private String key;
    private Animation animation;
    private boolean isAlreadyPressed;
    private boolean stop;


    /**
     * Instantiates a new Key press stoppable animation.
     *
     * @param sensor    the keyboard Sensor
     * @param key       the key that need to press to stop the animation
     * @param animation the animation that need to run
     */
    public KeyPressStoppableAnimation(KeyboardSensor sensor, String key, Animation animation) {
        this.sensor = sensor;
        this.key = key;
        this.animation = animation;
        this.isAlreadyPressed = true;
        this.stop = false;
    }

    @Override
    public boolean shouldStop() {
        if (this.animation.shouldStop()) {
            return true;
        }
        if (this.stop) {
            this.stop = false;
            return true;
        }
        return false;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        //if the key was pressed before the animation started, we ignore the key press.
        if (sensor.isPressed(key) && !this.isAlreadyPressed) {
            this.stop = true;
        }

        this.animation.doOneFrame(d);
        //there was a time point after the animation started in which "key" was not pressed
        if (!sensor.isPressed(key)) {
            isAlreadyPressed = false;
        }

    }
}

