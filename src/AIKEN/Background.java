/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AIKEN;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
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
