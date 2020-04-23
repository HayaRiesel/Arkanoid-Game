package menu;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import animations.AnimationRunner;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Menu animation.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T> {
    private List<MenuSelection<T>> selections = new ArrayList<MenuSelection<T>>();
    private String menuTitle;
    private KeyboardSensor keyboardSensor;
    private T status;

    private Menu<T> subMenu;
    private String subMenuKey;
    private String subMenuMessage;

    private AnimationRunner ar;

    /**
     * Instantiates a new Menu animation.
     *
     * @param menuTitle      the menu title
     * @param keyboardSensor the keyboard sensor
     * @param ar             the ar
     */
    public MenuAnimation(String menuTitle, KeyboardSensor keyboardSensor, AnimationRunner ar) {
        this.menuTitle = menuTitle;
        this.keyboardSensor = keyboardSensor;
        this.status = null;
        this.ar = ar;
        this.subMenu = null;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        selections.add(new MenuSelection<T>(key, message, returnVal));
    }


    @Override
    public T getStatus() {
        return this.status;
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu1) {
        this.subMenu = subMenu1;
        this.subMenuKey = key;
        this.subMenuMessage = message;
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        //draw background
        try {
            Image img = ImageIO.read(
                    ClassLoader.getSystemClassLoader().getResourceAsStream("background_images/menu.jpg"));
            img = img.getScaledInstance(800, 600, Image.SCALE_DEFAULT);
            d.drawImage(0, 0, img);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //draw the text
        d.setColor(new Color(133, 245, 255));
        d.fillRectangle(30, 60, this.menuTitle.length() * 35, 70);
        d.setColor(Color.BLACK);
        d.drawText(40, 120, this.menuTitle, 70);
        int i = 0;
        while (i < selections.size()) {
            d.drawText(200, 300 + i * 100, "(" + this.selections.get(i).getKey() + ")", 50);
            d.drawText(300, 300 + i * 100, this.selections.get(i).getMessage(), 50);
            i++;
        }
        //draw the option of the submenu
        if (this.subMenu != null) {
            d.drawText(200, 300 + i * 100, "(" + this.subMenuKey + ")", 50);
            d.drawText(300, 300 + i * 100, this.subMenuMessage, 50);
        }

    }

    @Override
    public boolean shouldStop() {
        //check if the user choose one from the options
        for (int i = 0; i < this.selections.size(); i++) {
            if (this.keyboardSensor.isPressed(this.selections.get(i).getKey())) {
                //change the return val and stop the animation
                this.status = this.selections.get(i).getReturnVal();
                return true;
            }

        }
        //check if the user choose the option of the submenu
        if (this.keyboardSensor.isPressed(this.subMenuKey)) {
            //run the submenu
            ar.run(subMenu);
            //change the return val to the return val in the submene and stop the animation
            this.status = this.subMenu.getStatus();
            return true;
        }
        return false;
    }
}
