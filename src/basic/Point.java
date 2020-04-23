package basic;

/**
 * The type Point.
 */
public class Point {
    private double x;
    private double y;

    /**
     * constructor.
     *
     * @param x the x of the point.
     * @param y the y of the point.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * return the distance of this point to the other point.
     *
     * @param other the other point.
     * @return the distance between the 2 points.
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2));
    }

    /**
     * return true is the points are equal, false otherwise.
     *
     * @param other the other point.
     * @return true i the points are equal false if not.
     */
    public boolean equals(Point other) {
        //check
        if (Double.compare(other.x, this.x) == 0 && Double.compare(other.y, this.y) == 0) {
            return true;
        }
        return false;
    }

    /**
     * Return the x value of this point.
     *
     * @return the x.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Return the x value of this point.
     *
     * @return the y.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Add a double to the x point and return new point.
     *
     * @param add the add to the x
     * @return the new point with the add x
     */
    public Point addToX(double add) {
        return new Point(this.x + add, this.y);
    }

    /**
     * Add a double to the y point and return new point.
     *
     * @param add the add to the y
     * @return the new point with the add y
     */
    public Point addToY(double add) {
        return new Point(this.x, this.y + add);
    }
}