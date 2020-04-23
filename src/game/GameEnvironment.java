package game;

import basic.CollisionInfo;
import basic.Line;
import basic.Point;
import basic.Rectangle;
import inerfaces.Collidable;

import java.util.ArrayList;
import java.util.List;

/**
 * The type GameLevel environment.
 */
public class GameEnvironment {
    private List<Collidable> collidables;

    /**
     * Instantiates a new GameLevel environment.
     */
    public GameEnvironment() {
        this.collidables = new ArrayList<Collidable>();
    }

    // add the given collidable to the environment.


    /**
     * Add the given collidable to the environment.
     *
     * @param c the collidable
     */
    public void addCollidable(Collidable c) {
        collidables.add(c);
    }

    /**
     * Remove the given collidable from the environment.
     *
     * @param c the collidable
     */
    public void removeCollidable(Collidable c) {
        collidables.remove(c);
    }


    /**
     * Check if the 2 collidable are the same one.
     *
     * @param c1 the collidable 1
     * @param c2 the collidable 2
     * @return the boolean
     */
    public boolean equal(Collidable c1, Collidable c2) {
        if (c1 == null || c2 == null) {
            return false;
        }
        if (collidables.indexOf(c1) == collidables.indexOf(c2)) {
            return true;
        }
        return false;
    }

    /**
     * Gets the information about the closest collision that is going to occur.
     * If this object will not collide with any of the collidables in this collection, return null.
     *
     * @param trajectory the trajectory
     * @return the closest collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        // Make a copy of the collidables before iterating over them.
        List<Collidable> collidablesCopy = new ArrayList<Collidable>(this.collidables);

        List<Point> clashPoints = new ArrayList<Point>();
        Point smallest;
        List<Collidable> clashCollidable = new ArrayList<Collidable>();
        //do list of the most close point of the collidables who have intersection with the trajectory
        for (Collidable c : collidablesCopy) {
            Rectangle r = c.getCollisionRectangle();
            Point collisionPoint = trajectory.closestIntersectionToStartOfLine(r);
            if (collisionPoint != null) {
                clashPoints.add(collisionPoint);
                clashCollidable.add(c);
            }
        }
        //this object not collide with any of the collidables in this collection
        if (clashCollidable.size() == 0) {
            return null;
        }
        //sort the colision point by distance from the start of the trajectory and return the closest collision
        smallest = clashPoints.get(0);
        for (Point p : clashPoints) {
            for (Point q : clashPoints) {
                if (q.distance(trajectory.start()) <= p.distance(trajectory.start())) {
                    smallest = q;
                }
            }
            if (smallest.equals(p)) {
                return new CollisionInfo(clashCollidable.get(clashPoints.indexOf(p)), p);
            }
        }
        return new CollisionInfo(clashCollidable.get(clashPoints.indexOf(smallest)), smallest);
    }

}
