package basic;

import biuoop.DrawSurface;
import game.GameLevel;
import inerfaces.Collidable;
import inerfaces.Sprite;
import listeners.HitListener;
import listeners.HitNotifier;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * The type Block.
 */
public class Block implements Collidable, Sprite, HitNotifier {
    private Rectangle rect;
    private int hitPoint;
    private List<HitListener> hitListeners = new ArrayList<HitListener>();
    private Map<Integer, Filling> fillByHitPoint;
    private Filling defaltFilling;
    private Color stroke;


    /**
     * Instantiates a new Block.
     *
     * @param rect     the rectangle pattern of the block
     * @param hitPoint the hit point
     */
    public Block(Rectangle rect, int hitPoint) {
        this.rect = rect;
        this.hitPoint = hitPoint;
    }

    /**
     * Instantiates a new Block (copy block).
     *
     * @param b the block
     */
    public Block(Block b) {
        this.defaltFilling = b.defaltFilling;
        this.fillByHitPoint = b.fillByHitPoint;
        this.hitPoint = b.hitPoint;
        this.rect = b.rect;
        this.stroke = b.stroke;
    }

    /**
     * Instantiates a new Block.
     *
     * @param rect     the rect
     * @param hitPoint the hit point
     * @param color    the color
     */
    public Block(Rectangle rect, int hitPoint, java.awt.Color color) {
        this.rect = rect;
        this.hitPoint = hitPoint;
        this.defaltFilling = new Filling(color);
        this.rect.setColor(color);
    }


    /**
     * Instantiates a new Block.
     *
     * @param upperLeftPointX the upper left point x
     * @param upperLeftPointY the upper left point y
     * @param width           the width
     * @param height          the height
     * @param hitPoint        the hit point
     */
    public Block(double upperLeftPointX, double upperLeftPointY, double width, double height, int hitPoint) {
        Point upperLeftPoint = new Point(upperLeftPointX, upperLeftPointY);
        this.rect = new Rectangle(upperLeftPoint, width, height);
        this.hitPoint = hitPoint;
    }


    /**
     * Instantiates a new Block.
     *
     * @param upperLeftPointX the upper left point x
     * @param upperLeftPointY the upper left point y
     * @param width           the width
     * @param height          the height
     * @param hitPoint        the hit point
     * @param c               the c
     */
    public Block(double upperLeftPointX, double upperLeftPointY, double width, double height, int hitPoint, Color c) {
        Point upperLeftPoint = new Point(upperLeftPointX, upperLeftPointY);
        this.rect = new Rectangle(upperLeftPoint, width, height);
        this.hitPoint = hitPoint;
        this.defaltFilling = new Filling(c);
        this.rect.setColor(c);
    }

    /**
     * Sets hit point.
     *
     * @param hit the hit point
     */
    public void setHitPoint(int hit) {
        this.hitPoint = hit;
    }


    /**
     * Return the "collision shape" of the object..
     *
     * @return rect the rect of the block
     */
    public Rectangle getCollisionRectangle() {
        return this.rect;
    }


    /**
     * Notify the object that we collided with it at collisionPoint with a given velocity.
     * The return is the new velocity expected after the hit (based the force the object inflicted on us).
     *
     * @param collisionPoint  the collision point of te ball in the block
     * @param currentVelocity the velocity of the ball when it collide the block
     * @param hitter          the ball that collide the block
     * @return the new velocity (diffrent direction)
     */
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        this.notifyHit(hitter);
        //update the hit point
        if (hitPoint != 0) {
            if (this.fillByHitPoint != null && this.fillByHitPoint.containsKey(this.hitPoint)) {
                this.rect.setFilling(this.fillByHitPoint.get(this.hitPoint));
            } else {
                this.rect.setFilling(this.defaltFilling);
            }
            hitPoint--;
        }
        return currentVelocity.usualVelocityChange(this.rect, collisionPoint);
    }

    /**
     * Draw the block on the gui.
     *
     * @param d the DrawSurface of the game
     */
    public void drawOn(DrawSurface d) {
        this.rect.drawOn(d);
        //d.setColor(Color.WHITE);
    }

    /**
     * notify the sprite that time has passed.
     */
    public void timePassed() {

    }

    /**
     * Add to gameLevel.
     *
     * @param gameLevel the gameLevel
     */
    public void addToGame(GameLevel gameLevel) {
        gameLevel.addSprite(this);
        gameLevel.addCollidable(this);
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

    @Override
    public void addHitListener(HitListener hl) {
        hitListeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        hitListeners.remove(hl);
    }

    /**
     * Notify all the listener that an hit happened.
     *
     * @param hitter the ball that collide this block
     */
    private void notifyHit(Ball hitter) {
        // Make a copy of the hitListeners before iterating over them.
        List<HitListener> listeners = new ArrayList<HitListener>(this.hitListeners);
        // Notify all listeners about a hit event:
        for (HitListener hl : listeners) {
            hl.hitEvent(this, hitter);
        }
    }

    /**
     * Gets hit points.
     *
     * @return the hit points
     */
    public int getHitPoints() {
        return this.hitPoint;
    }

    /**
     * Sets color.
     *
     * @param c the c
     */
    public void setDefaltFillingColor(Color c) {
        this.setFilling(new Filling(c));
        this.rect.setColor(c);
    }

    /**
     * Sets filling.
     *
     * @param filling the filling
     */
    public void setFilling(Filling filling) {
        this.defaltFilling = filling;
        this.rect.setFilling(filling);
    }

    /**
     * Sets stroke.
     *
     * @param c the c
     */
    public void setStroke(Color c) {
        this.rect.setStroke(c);
    }

    /**
     * Sets the list of fill by hit point.
     *
     * @param fills the fills
     */
    public void setFillByHitPoint(Map<Integer, Filling> fills) {
        this.fillByHitPoint = fills;
    }

}
