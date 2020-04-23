package basic;

import biuoop.DrawSurface;
import game.GameEnvironment;
import game.GameLevel;
import inerfaces.Sprite;

/**
 * The type Ball.
 */
public class Ball implements Sprite {
    private Point center;
    private int r;
    private java.awt.Color color;
    private Velocity velocity;
    private GameEnvironment gameEnvironment;
    private CollisionInfo lastCollision;

    /**
     * Constructor.
     *
     * @param center the center of the ball.
     * @param r      the radius of the ball.
     * @param color  the color of the ball.
     */
    public Ball(Point center, int r, java.awt.Color color) {
        this.center = center;
        this.r = r;
        this.color = color;
        this.velocity = new Velocity(0, 0);
    }

    /**
     * Constructor.
     *
     * @param x     the x of the the center of the ball.
     * @param y     the y of the the center of the ball.
     * @param r     the radius of the ball.
     * @param color the color of the ball.
     */
    public Ball(int x, int y, int r, java.awt.Color color) {
        this.center = new Point(x, y);
        this.r = r;
        this.color = color;
        this.velocity = new Velocity(0, 0);
    }

    /**
     * Accessors.
     *
     * @return the x of the center of the ball
     */
    public int getX() {
        return (int) (this.center.getX());
    }

    /**
     * accessors.
     *
     * @return the y of the center of the ball
     */
    public int getY() {
        return (int) (this.center.getY());
    }

    /**
     * accessors.
     *
     * @return the radius of the ball
     */
    public int getSize() {
        return this.r;
    }

    /**
     * accessors.
     *
     * @return the velocity of the ball
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /**
     * draw the ball on the given drawSurface.
     *
     * @param surface the DrawSurface.
     */
    public void drawOn(DrawSurface surface) {
        surface.setColor(this.color);
        surface.fillCircle(this.getX(), this.getY(), this.getSize());
    }


    /**
     * set the velocity of the ball.
     *
     * @param v the velocity of the ball
     */
    public void setVelocity(Velocity v) {
        this.velocity = v;
    }

    /**
     * set the velocity of the ball.
     *
     * @param dx the velocity of the x of the ball
     * @param dy the velocity of the y of the ball
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
    }

    /**
     * * take care on hit situation.
     *
     * @param trajectory the trajectory of the ball
     */
    private void hitSituation(Line trajectory) {
        CollisionInfo collision = this.gameEnvironment.getClosestCollision(trajectory);
        Point collisionPoint = collision.collisionPoint();
        //Promotes the ball almost to the point of collision
        this.center = collisionPoint.addToX(-0.0001 * this.velocity.getDx()).addToY(-0.0001 * this.velocity.getDy());
        Rectangle rect = collision.collisionObject().getCollisionRectangle();
        //set the velocity
        this.setVelocity(collision.collisionObject()
                .hit(this, trajectory.closestIntersectionToStartOfLine(rect), this.velocity));

        this.lastCollision = collision;
    }


    /**
     * move the ball (change his center) one step by his velocity and handle collision.
     */
    public void moveOneStep() {
        Line trajectory = this.velocity.getLineOfdirection(this.center);
        //for prevent a initialization problem;
        CollisionInfo closestCollision = this.gameEnvironment.getClosestCollision(trajectory);
        if (closestCollision != null) {
            //for prevent a initialization problem;
            if (lastCollision != null) {
                //prevent a problem of the ball stay on the paddle because his movement
                //it happen if the ball hit in the sides of the paddle
                if (!this.gameEnvironment
                        .equal(closestCollision.collisionObject(), this.lastCollision.collisionObject())) {
                    hitSituation(trajectory);
                    return;
                } else {
                    this.center = this.getVelocity().applyToPoint(this.center);
                }
            } else {
                //it can happen only in the first time of collision so there is no problem
                hitSituation(trajectory);
            }

        } else {
            //there is no collision so step usual
            this.center = this.getVelocity().applyToPoint(this.center);
        }
    }

    /**
     * Notify the sprite that time has passed.
     */
    public void timePassed() {
        moveOneStep();

    }

    /**
     * Add to gameLevel.
     *
     * @param gameLevel the gameLevel
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
        this.gameEnvironment = gameLevel.getGameEnviroment();
    }

    /**
     * Remove from gameLevel.
     *
     * @param gameLevel the gameLevel
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeSprite(this);
    }

    /**
     * Gets color.
     *
     * @return the color of the ball
     */
    public java.awt.Color getColor() {
        return this.color;
    }
}



