package basic;

import java.awt.Color;
import java.awt.Image;

/**
 * The type Filling.
 */
public class Filling {
    private Color color;
    private Image image;

    /**
     * Instantiates a new Filling.
     *
     * @param c the color
     */
    public Filling(Color c) {
        this.color = c;
        this.image = null;
    }

    /**
     * Instantiates a new Filling.
     *
     * @param image the image
     */
    public Filling(Image image) {
        this.image = image;
        this.color = null;
    }

    /**
     * Gets color.
     *
     * @return the color
     */
    public Color getColor() {
        return this.color;
    }

    /**
     * Is color boolean.
     *
     * @return the boolean
     */
    public boolean isColor() {
        if (this.image == null) {
            return true;
        }
        return false;
    }

    /**
     * Is image boolean.
     *
     * @return the boolean
     */
    public boolean isImage() {
        if (this.color == null) {
            return true;
        }
        return false;
    }

    /**
     * Gets image.
     *
     * @return the image
     */
    public Image getImage() {
        return this.image;
    }



}
