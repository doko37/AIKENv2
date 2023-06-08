/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package AIKEN;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;

/**
 * This class was used for creating JPanels with background images.
 * @author hunub
 */
public class Background extends JPanel {
    private Image img;
    
    public Background(Image img) {
        this.img = img;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(img, 0, 0, null);
    }
}
