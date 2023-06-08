/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package entity;

import AIKEN.AdventurePanel;
import javax.swing.ImageIcon;

/**
 * This class represents the bullet the slime shoots in the adventure mini-game.
 * @author hunub
 */
public class Bullet extends Entity {
    AdventurePanel ap;
    
    
    public Bullet(AdventurePanel ap) {
        this.ap = ap;
        this.alive = false;
        getSprite();
    }
    
    // Set the position to the player's position (centered x-axis).
    public void set(int playerX, int playerY) {
        x = playerX + (ap.tileSize / 4) ; 
        y = playerY;
    }
    
    public void getSprite() {
        sprite = new ImageIcon("./objects/bullet.png").getImage();
    }
}
