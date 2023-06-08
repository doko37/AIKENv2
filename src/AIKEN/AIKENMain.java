/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package AIKEN;

import AIKEN.Data.GameState;
import java.awt.Font;
import java.io.File;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

/**
 * MAIN CLASS
 * @author hunub
 */
public class AIKENMain {
    public static void main(String args[]) {
        Model model = new Model();
        AdventurePanel ap = new AdventurePanel(model, GameState.STARTING_SCREEN);
        
        File file = new File("./fonts/upheavtt.ttf");
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, file);
            Font sizedFont = font.deriveFont(12f);
            final FontUIResource f = new FontUIResource(sizedFont);
            setUIFont(f);
        } catch (Exception e) {
            System.out.println(e);
        }
        
        View view = new View(ap, model.getExistingUsers());
        Controller controller = new Controller(view, model);
        model.addObserver(view);
    }
    
    /**
     * Support function that sets the default font for all components in the GUI.
     * @param f 
     */
    private static void setUIFont (javax.swing.plaf.FontUIResource f){
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof javax.swing.plaf.FontUIResource)
              UIManager.put (key, f);
        }
    } 
}


