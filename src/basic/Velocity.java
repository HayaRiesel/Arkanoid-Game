package basic;

/**
 * The type Velocity specifies the change in position on the `x` and the `y` axes.
 */
public class Velocity {
    private double dx;
    private double dy;

    /**
     * Constructor.
     *
     * @param dx the change in the x.
     * @param dy the change in the y.
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /**
     * accessors.
     *
     * @return the dx
     * @erturn the dx.
     */
    public double getDx() {
        return this.dx;
    }

    /**
     * accessors.
     *
     * @return the dy
     * @erturn the dy.
     */
    public double getDy() {
        return this.dy;
    }

    /**
     * Take a point with position (x,y) and return a new point
     * with position (x+dx, y+dy).
     *
     * @param p the point of the corrent position of the object.
     * @return the point of the position of the object after the movement.
     */
    public Point applyToPoint(Point p) {
        double x = p.getX() + this.dx;
        double y = p.getY() + this.dy;
        return new Point(x, y);
    }

    /**
     * create  velocity from angle and speed.
     *
     * @param angle the angle of the direction of the velocity.
     * @param speed the speed of the velocity.
     * @return new velocity by the usual way (dx,dy).
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        angle = Math.toRadians(angle);
        double dx = speed * Math.sin(angle);
        double dy = -speed * Math.cos(angle);
        return new Velocity(dx, dy);
    }

    /**
     * Gets the line of direction of the speed.
     *
     * @param start the center
     * @return the line ofdirection
     */
    public Line getLineOfdirection(Point start) {
        return new Line(start.getX(), start.getY(), this.dx + (start.getX()), this.dy + (start.getY()));
    }

    /**
     * usual velocity change velocity when the ball collision in collidables.
     *
     * @param rect the rectangle of the collidables
     * @param collisionPoint the collision point
     * @return the new velocity
     */
    public Velocity usualVelocityChange(Rectangle rect, Point collisionPoint) {
        if (rect.isDownLineHasIntersecion(collisionPoint) && this.getDy() < 0) {
            return new Velocity(this.getDx(), -this.getDy());
        }
        if (rect.isUpLineHasIntersecion(collisionPoint) && this.getDy() > 0) {
            return new Velocity(this.getDx(), -this.getDy());
        }
        if (rect.isLeftLineHasIntersecion(collisionPoint) && this.getDx() > 0) {
            return new Velocity(-this.getDx(), this.getDy());
        }
        if (rect.isRightLineHasIntersecion(collisionPoint) && this.getDx() < 0) {
            return new Velocity(-this.getDx(), this.getDy());

        }
        return this;
    }
}