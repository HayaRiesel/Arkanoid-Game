package basic;

import inerfaces.Collidable;

/**
 * The type Collision info.
 */
public class CollisionInfo {
    private Collidable collidable;
    private Point collisionPoint;


    /**
     * Instantiates a new Collision info.
     *
     * @param collidable     the collidable
     * @param collisionPoint the collision point of the ball in the collidable
     */
    public CollisionInfo(Collidable collidable, Point collisionPoint) {
        this.collidable = collidable;
        this.collisionPoint = collisionPoint;
    }

    /**
     * return the collision point of the ball with this collidable.
     *
     * @return the point
     */
// the point at which the collision occurs.
    public Point collisionPoint() {
        return this.collisionPoint;
    }

    /**
     * return the collidable.
     *
     * @return the collidable
     */
// the collidable object involved in the collision.
    public Collidable collisionObject() {
        return this.collidable;
    }


}