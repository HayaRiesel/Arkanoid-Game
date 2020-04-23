package reading;

import basic.Filling;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Field;


/**
 * The type Colors parser.
 */
public class ColorsParser {
    /**
     * Filling from string filling.
     *
     * @param s the s
     * @return the filling
     */
    public static Filling fillingFromString(String s) {

        if (s.contains("color")) {
            //delete the word that didnt represent the color
            String sAfterChange = (String) s.subSequence(0, s.length() - 1);
            sAfterChange = sAfterChange.replace("color(", "");
            return new Filling(colorFromString(sAfterChange.replace("background:color(", "")));
        }
        if (s.contains("image")) {
            String sAfterChange = (String) s.subSequence(0, s.length() - 1);
            return new Filling(imageFromString(sAfterChange.replace("image(", "")));

        }
        //ERROR
        return null;
    }

    /**
     * Color from string color.
     *
     * @param s the s
     * @return the color
     */
    public static Color colorFromString(String s) {
        //color(RGB(x,y,z)), where x, y, z are integers.
        if (s.contains("RGB")) {
            s = s.replace("RGB(", "");
            s = s.replace(")", "");
            String[] rgb = s.split(",");
            return new Color(Integer.valueOf(rgb[0]), Integer.valueOf(rgb[1]), Integer.valueOf(rgb[2]));
        }
        //return color from string of the name ( for example: "red")
        try {
            Field field = Class.forName("java.awt.Color").getField(s);
            return (Color) field.get(null);
        } catch (Exception e) {
            //ERROR
            return null;
        }

    }

    /**
     * Image from string image.
     *
     * @param s the s
     * @return the image
     */
    public static Image imageFromString(String s) {
        Image img = null;
        try {
            img = ImageIO.read(ClassLoader.getSystemClassLoader().getResourceAsStream(s));

            //img = ImageIO.read(new File(s));
            return img;
        } catch (IOException e) {
            e.printStackTrace();
            //ERROR
            return null;
        }
    }
}
