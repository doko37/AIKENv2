/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package entity;

import AIKEN.AdventurePanel;
import AIKEN.KeyHandler;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 * This class represents the player in the adventure mini-game.
 * @author hunub
 */
public class Slime extends Entity {
    AdventurePanel ap;
    KeyHandler keyH;
    public Bullet bullet;
    
    public Slime(AdventurePanel ap, KeyHandler keyH) {
        this.ap = ap;
        this.keyH = keyH;
        this.bullet = new Bullet(ap);
        getSprite();
    }
    
    /**
     * By default set the location of player in the center of the screen (horizontally),
     * and at the bottom row of the screen.
     */
    public void setDefaultValues() {
        x = ap.tileSize*6;
        y = ap.screenHeight - ap.tileSize;
        speed = ap.tileSize;
    }
    
    /**
     * Updates the character's position based on key inputs.
     * 
     * If a key is not being pressed:
     * 
     * if left is pressed move the character left by one tile,
     * 
     * if right is pressed move the character right by one tile,
     * 
     * if the space bar is pressed and the bullet is not alive, play a sound, move the bullet to the
     * player's current position and make it alive.
     * 
     * If the bullet is alive, then move it up the screen by 20 pixels, if the bullet's y position is greater than 0, kill it.
     */
    public void update() {
        if(!keyH.keyPressed) {
            if(keyH.left) {
                if(x > 0) {
                    x -= ap.tileSize;
                } else {
                    x = 0;
                }
                keyH.keyPressed = true;
            } else if(keyH.right) {
                if(x + ap.tileSize < ap.screenWidth) {
                    x += ap.tileSize;
                } else {
                    x = ap.screenWidth - ap.tileSize;
                }
                keyH.keyPressed = true;
            } else if(keyH.shoot && !bullet.alive) {
                ap.playSound(2);
                this.bullet.set(x, y);
                this.bullet.alive = true;
                keyH.keyPressed = true;
            }
        }
        
        if(bullet.alive) {
           if(bullet.y > 0) {
               bullet.y -= 20;
           } else {
               bullet.alive = false;
           }
           
           
        }
    }
    
    public void getSprite() {
        sprite = new ImageIcon("./slimes/blue_back_resized.png").getImage();
    }
    
    // Draw the bullet and the character.
    public void draw(Graphics2D g2) {
        Image image = sprite;
        
        if(bullet.alive) {
            g2.drawImage(bullet.sprite, bullet.x, bullet.y, ap.tileSize/2, ap.tileSize/2, null);
        }
        
        g2.drawImage(image, x, y, ap.tileSize, ap.tileSize, null);
    }
}
