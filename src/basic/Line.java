package basic;

import biuoop.DrawSurface;

import java.awt.Color;
import java.util.List;

/**
 * The type Line.
 */
public class Line {
    private Point start;
    private Point end;
    private Color color;

    /**
     * Constructor.
     *
     * @param start the start of the line.
     * @param end   the end of the line.
     */
    public Line(Point start, Point end) {
        //take care that the start will be first.
        if (end.getX() <= start.getX()) {
            this.start = end;
            this.end = start;
        } else {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * Constructor.
     *
     * @param x1 the x of the first point.
     * @param y1 the y of th first point.
     * @param x2 the x of the second point.
     * @param y2 the y of the second point.
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /**
     * Return the length of the line.
     *
     * @return the length of the line.
     */
    public double length() {

        return start.distance(end);
    }

    /**
     * Return the middle of the line.
     *
     * @return th middle point of the line.
     */
    public Point middle() {
        return new Point((this.start.getX() + this.end.getX()) / 2, (this.start.getY() + this.end.getY()) / 2);
    }

    /**
     * accessors.
     *
     * @return the start point of the line.
     */
    public Point start() {

        return this.start;
    }

    /**
     * accessors.
     *
     * @return the end point of the line
     */
    public Point end() {

        return this.end;
    }

    /**
     * Find the intersection point in cae there is vertical lines.
     *
     * @param other the other line
     * @return the intersection point
     */
    private Point verticalLine(Line other) {
        double x = 0;
        double y = 0;
        //in case the 2 lines are verticals
        if (compareDouble(this.end.getX(), this.start.getX()) && compareDouble(other.end.getX(), other.start.getX())) {
            //one line start in the point the other line ends
            if (compareDouble(this.start.getY(), other.end.getY())) {
                return this.start;
            }
            if (compareDouble(other.start.getY(), this.end.getY())) {
                return other.start;
            } else {
                return null;
            }
        }
        //if this line is vertical
        if (compareDouble(this.end.getX(), this.start.getX())) {
            return oneIsVerticel(this, other);
        }
        //if other line is vertical
        if (compareDouble(other.end.getX(), other.start.getX())) {
            return oneIsVerticel(other, this);
        }
        return new Point(x, y);
    }

    /**
     * Find the intersection point in case only one from the lines is vertical.
     *
     * @param vertical the vertical line
     * @param line     the line is not vertical
     * @return the point of the intersection
     */
    private Point oneIsVerticel(Line vertical, Line line) {
        double x = 0;
        double y = 0;
        if ((line.start.getX() <= vertical.end.getX() || compareDouble(line.start.getX(), vertical.end.getX()))
                && (vertical.end.getX() <= line.end.getX()) || compareDouble(vertical.end.getX(), line.end.getX())) {
            x = vertical.end.getX();
            //y=m1*(x-x1)+y1
            y = (line.end.getY() - line.start.getY()) / (line.end.getX() - line.start.getX())
                    * (x - line.start.getX()) + line.start.getY();
        }
        return new Point(x, y);

    }

    /**
     * Find the intersection of the equation of the lines.
     *
     * @param other the other line
     * @return the intersection point of the equation of the lines
     */
    private Point generalIntersection(Line other) {
        double x;
        double y;
        double otherIncline;
        double thisIncline;
        //check in case the incline is 0
        if (compareDouble(this.end.getX(), this.start.getX()) || compareDouble(other.end.getX(), other.start.getX())) {
            return this.verticalLine(other);
        }
        //Calculates the incline of the lines (y1-y2)/(x1-x2)
        thisIncline = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
        otherIncline = (other.end.getY() - other.start.getY()) / (other.end.getX() - other.start.getX());
        //if the incline are equal there is no intersection point
        if (compareDouble(thisIncline, otherIncline)) {
            return null;
        }
        //Calculates the point of the intersection by the equation of the lines
        //x=(m1*x1-y1-m2*x2+y2)/(m1-m2)
        x = ((thisIncline * this.start.getX() - this.start.getY() - otherIncline * other.start.getX()
                + other.start.getY()) / (thisIncline - otherIncline));
        //y=m1*(x-x1)+y1
        y = thisIncline * (x - this.start.getX()) + this.start.getY();
        return new Point(x, y);
    }

    /**
     * Returns true if the lines intersect, false otherwise.
     *
     * @param other the other line
     * @return true if the lines intersect, false otherwise
     */
    public boolean isIntersecting(Line other) {
        Point intersection = this.generalIntersection(other);
        if (intersection == null) {
            return false;
        }
        //check if the intersection point on the lines
        if (this.isInterection(intersection) && other.isInterection(intersection)) {
            return true;
        }
        return false;
    }

    /**
     * Returns the intersection point if the lines intersect and null otherwise.
     *
     * @param other the other line
     * @return the intersection point if the lines intersect and null otherwise.
     */
    public Point intersectionWith(Line other) {
        if (this.isIntersecting(other)) {
            return this.generalIntersection(other);
        } else {
            return null;
        }
    }

    /**
     * Is interection boolean.
     *
     * @param point the point
     * @return the boolean
     */
    public boolean isInterection(Point point) {
        double y;
        //in case the line is vertical
        if (compareDouble(this.start.getX(), this.end.getX()) && compareDouble(point.getX(), this.start.getX())) {
            y = point.getY();
        } else {
            double thisIncline = (this.end.getY() - this.start.getY()) / (this.end.getX() - this.start.getX());
            //y=m1*(x-x1)+y1
            y = thisIncline * (point.getX() - this.start.getX()) + this.start.getY();
        }
        //check if the point on the general line
        if (compareDouble(point.getY(), y)) {
            //check if the point on the line
            if (this.start.getX() <= point.getX() && point.getX() <= this.end.getX()
                    && (Math.min(this.start.getY(), this.end.getY()) <= point.getY()
                    || compareDouble(point.getY(), Math.min(this.start.getY(), this.end.getY())))
                    && (point.getY() <= Math.max(this.end.getY(), this.start.getY())
                    || compareDouble(point.getY(), Math.max(this.end.getY(), this.start.getY() - y)))) {
                return true;
            }
        }

        return false;

    }

    /**
     * Return true is the lines are equal, false otherwise.
     *
     * @param other the other line
     * @return true is the lines are equal, false otherwise.
     */
    public boolean equals(Line other) {
        if (this.start.equals(other.start) && this.end.equals(other.end)) {
            return true;
        }
        return false;
    }


    /**
     * If this line does not intersect with the rectangle, return null.
     * Otherwise, return the closest intersection point to the start of the line.
     *
     * @param rect the rect
     * @return the point
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        Line line = new Line(this.start, this.end);
        List<Point> list = rect.intersectionPoints(line);
        //it can be maximum 2 point that the rect intersection to the line
        if (list.size() == 0) {
            return null;
        }
        if (list.size() == 1) {
            return list.get(0);
        }
        //return the point that most close to the start of line
        if (list.get(0).distance(this.start) < list.get(1).distance(this.start)) {
            return list.get(0);
        } else {
            return list.get(1);
        }
    }

    /**
     * Compare double. if the double are same return true else return false
     *
     * @param d1 the first double
     * @param d2 the second double
     * @return the boolean
     */
    static boolean compareDouble(double d1, double d2) {
        if (Math.abs(d1 - d2) < 1e-6) {
            return true;
        }
        return false;
    }

    /**
     * Draw the line on the DrawSurface.
     *
     * @param d the DrawSurface
     */
    public void drawOn(DrawSurface d) {
        d.setColor(this.color);
        d.drawLine((int) (this.start.getX()), (int) (this.start.getY()), (int) (this.end.getX()),
                (int) (this.end.getY()));
    }

    /**
     * Sets color.
     *
     * @param c the color
     */
    public void setColor(Color c) {
        this.color = c;
    }

}