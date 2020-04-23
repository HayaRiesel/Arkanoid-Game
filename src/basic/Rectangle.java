package basic;

import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Rectangle.
 */
public class Rectangle {
    private Point upperLeft;
    private double width;
    private double height;
    private Point upperRight;
    private Point downLeft;
    private Point downRight;
    private Line leftLine;
    private Line upLine;
    private Line rightLine;
    private Line downLine;
    private Filling filling;
    private Color stroke;

    /**
     * Instantiates a new Rectangle.
     *
     * @param upperLeft the upper left point of the rectangle
     * @param width     the width of the rectangle
     * @param height    the height of the rectangle
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;
        //Calculates the other points
        this.upperRight = new Point((this.upperLeft.getX() + this.width), this.upperLeft.getY());
        this.downLeft = new Point(this.upperLeft.getX(), this.upperLeft.getY() + this.height);
        this.downRight = new Point(this.upperRight.getX(), this.downLeft.getY());
        this.leftLine = new Line(this.upperLeft, this.downLeft);
        this.upLine = new Line(this.upperLeft, this.upperRight);
        this.rightLine = new Line(this.upperRight, this.downRight);
        this.downLine = new Line(this.downLeft, this.downRight);
    }

    /**
     * Return a (possibly empty) List of intersection points with the specified line.
     *
     * @param line the line
     * @return the list of intersection points with the line
     */
    public List<Point> intersectionPoints(Line line) {
        List<Point> list = new ArrayList<Point>();
        Line[] rectangleLine = {this.leftLine, this.upLine, this.rightLine, this.downLine};
        for (int i = 0; i < 4; i++) {
            if (line.isIntersecting((rectangleLine[i]))) {
                list.add(line.intersectionWith(rectangleLine[i]));
            }
        }
        return list;
    }

    /**
     * check if the left line has intersecion with the point.
     *
     * @param p the point
     * @return the boolean
     */
    public boolean isLeftLineHasIntersecion(Point p) {
        if (this.leftLine.isInterection(p)) {
            return true;
        }
        return false;
    }

    /**
     * check if the up line has intersecion with the point.
     *
     * @param p the point
     * @return the boolean
     */
    public boolean isUpLineHasIntersecion(Point p) {
        if (this.upLine.isInterection(p)) {
            return true;
        }
        return false;
    }

    /**
     * check if the right line has intersecion with the point.
     *
     * @param p the point
     * @return the boolean
     */
    public boolean isRightLineHasIntersecion(Point p) {
        if (this.rightLine.isInterection(p)) {
            return true;
        }
        return false;
    }

    /**
     * check if the down line has intersecion with the point.
     *
     * @param p the point
     * @return the boolean
     */
    public boolean isDownLineHasIntersecion(Point p) {
        if (this.downLine.isInterection(p)) {
            return true;
        }
        return false;
    }

    /**
     * Gets width.
     *
     * @return the width
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Gets height.
     *
     * @return the height
     */
    public double getHeight() {
        return this.height;
    }

    /**
     * Gets the upper right point of the rectangle.
     *
     * @return the upper right
     */
    public Point getUpperRight() {
        return this.upperRight;
    }

    /**
     * Gets the upper left point of the rectangle.
     *
     * @return the upper left
     */
    public Point getUpperLeft() {
        return this.upperLeft;
    }

    /**
     * Set color of the rectangle.
     *
     * @param c the color
     */
    public void setColor(Color c) {
        this.filling = new Filling(c);
    }

    /**
     * Draw the rectangle on the screen.
     *
     * @param surface the gui surface
     */
    public void drawOn(DrawSurface surface) {
        if (this.filling.isColor()) {
            surface.setColor(this.filling.getColor());
            surface.fillRectangle((int) (this.upperLeft.getX()), (int) (this.upperLeft.getY()),
                    (int) (this.width), (int) (this.height));
            if (this.stroke == null) {
                surface.setColor(Color.BLACK);
            } else {
                surface.setColor(stroke);
            }

            surface.drawRectangle((int) (this.upperLeft.getX()), (int) (this.upperLeft.getY()),
                    (int) (this.width), (int) (this.height));
        }
        if (this.filling.isImage()) {
            surface.drawImage((int) (this.upperLeft.getX()), (int) (this.upperLeft.getY()), this.filling.getImage());
        }
    }

    /**
     * Gets the  middle point of the rectangle.
     *
     * @return the middle
     */
    public Point getMiddle() {
        double x = this.upLine.middle().getX();
        double y = this.leftLine.middle().getY();
        return new Point(x, y);
    }

    /**
     * Gets color.
     *
     * @return the color of the rect
     */
    public Color getColor() {
        return this.filling.getColor();
    }


    /**
     * Sets filling.
     *
     * @param f the f
     */
    public void setFilling(Filling f) {
        this.filling = f;
    }

    /**
     * Sets stroke.
     *
     * @param c the c
     */
    public void setStroke(Color c) {
        this.stroke = c;
    }
}
