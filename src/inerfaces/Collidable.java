package inerfaces;

import basic.Ball;
import basic.Point;
import basic.Rectangle;
import basic.Velocity;

/**
 * The interface Collidable.
 */
public interface Collidable {
    /**
     * Gets collision rectangle.
     *
     * @return the collision rectangle
     */
// Return the "collision shape" of the object.
    Rectangle getCollisionRectangle();

    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based on the force the object inflicted on us).
     *
     * @param collisionPoint  the collision point
     * @param currentVelocity the current velocity
     * @param hitter the ball that collide the collidable
     * @return the velocity
     */

    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);
}
