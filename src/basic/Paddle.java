package basic;

import inerfaces.Collidable;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import game.GameLevel;
import inerfaces.Sprite;

import java.awt.Color;

/**
 * The type Paddle.
 */
public class Paddle implements Sprite, Collidable {
    private biuoop.KeyboardSensor keyboard;
    private Rectangle rect;
    private int speed;
    private double leftLimit;
    private double rightLimit;


    /**
     * Instantiates a new Paddle.
     *
     * @param keyboard the keyboard
     * @param rect     the rect of the the shape of the paddle
     * @param speed    the speed ot he paddle
     */
    public Paddle(biuoop.KeyboardSensor keyboard, Rectangle rect, int speed) {
        this.keyboard = keyboard;
        this.rect = rect;
        this.speed = speed;
    }


    /**
     * Move left.
     */
    public void moveLeft() {
        //if the paddle in the limits - move until the edge
        if (this.rect.getUpperLeft().getX() - speed < this.leftLimit
                || this.rect.getUpperLeft().getX() - speed == this.leftLimit) {
            this.rect = new Rectangle(new Point(30, this.rect.getUpperLeft().getY()),
                    this.rect.getWidth(), this.rect.getHeight());
        } else {
            Point newUpperLeft = new Point(this.rect.getUpperLeft().getX() - speed, this.rect.getUpperLeft().getY());
            this.rect = new Rectangle(newUpperLeft, this.rect.getWidth(), this.rect.getHeight());
        }

    }

    /**
     * Move right.
     */
    public void moveRight() {
        //if the paddle in the limits - move until the edge
        if (this.rect.getUpperRight().getX() + speed > this.rightLimit
                || this.rect.getUpperRight().getX() + speed == this.rightLimit) {
            this.rect = new Rectangle(new Point(770 - this.rect.getWidth(), this.rect.getUpperLeft().getY()),
                    this.rect.getWidth(), this.rect.getHeight());
        } else {

            Point newUpperLeft = new Point(this.rect.getUpperLeft().getX() + speed, this.rect.getUpperLeft().getY());
            this.rect = new Rectangle(newUpperLeft, this.rect.getWidth(), this.rect.getHeight());
        }
    }

    /**
     * Remove from gameLevel.
     *
     * @param gameLevel the gameLevel
     */
    public void removeFromGame(GameLevel gameLevel) {
        gameLevel.removeCollidable(this);
        gameLevel.removeSprite(this);
    }

    /**
     * Move the paddle.
     */
    public void timePassed() {
        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }
        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    /**
     * Draw the paddle on the screen.
     *
     * @param d the gui drawSurfac
     */
    public void drawOn(DrawSurface d) {
        this.rect.setColor(Color.YELLOW);
        this.rect.drawOn(d);
    }

    /**
     * Get the collision rectangle.
     *
     * @return rect the collision rectangle
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }

    /**
     * Get the collision rectangle.
     *
     * @param collisionPoint  the collision point of the ball in the paddle
     * @param currentVelocity the velocity of the ball when it collide the paddle
     * @param hitter          the ball that collide the paddle
     * @return the new velocity (different direction)
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        //divide the up line of the paddle to 5 parts
        double conversation = this.rect.getWidth() / 5;
        Line l1 = new Line(this.rect.getUpperLeft(), this.rect.getUpperLeft().addToX(conversation));
        Line l2 = new Line(l1.end(), l1.end().addToX(conversation));
        Line l3 = new Line(l2.end(), l2.end().addToX(conversation));
        Line l4 = new Line(l3.end(), l3.end().addToX(conversation));
        Line l5 = new Line(l4.end(), this.rect.getUpperRight());
        Line pathOfOneVelocity = currentVelocity.getLineOfdirection(new Point(0, 0));
        double spead = pathOfOneVelocity.length();
        //change the velocity by the rules
        if (l1.isInterection(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(300, spead);
        }
        if (l2.isInterection(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(330, spead);
        }
        if (l4.isInterection(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(30, spead);
        }
        if (l5.isInterection(collisionPoint)) {
            return Velocity.fromAngleAndSpeed(60, spead);
        }
        //if(l3.isInterection(collisionPoint))
        return currentVelocity.usualVelocityChange(this.rect, collisionPoint);
    }

    /**
     * Add this paddle to the game.
     *
     * @param g the g
     */
    public void addToGame(GameLevel g) {
        g.addSprite(this);
        g.addCollidable(this);
    }

    /**
     * Set limits that tha paddle can move between them.
     *
     * @param rightLimitX the right limit (the x)
     * @param leftLimitX  the left limit (the x)
     */
    public void setLimits(double leftLimitX, double rightLimitX) {
        this.rightLimit = rightLimitX;
        this.leftLimit = leftLimitX;
    }
}