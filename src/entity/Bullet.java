/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import AIKEN.AdventurePanel;
import AIKEN.KeyHandler;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author hunub
 */
public class Bullet extends Entity {
    AdventurePanel ap;
    
    
    public Bullet(AdventurePanel ap) {
        this.ap = ap;
        this.alive = false;
        getSprite();
    }
    
    public void set(int playerX, int playerY) {
        x = playerX + (ap.tileSize / 4) ; 
        y = playerY;
    }
    
    public void getSprite() {
        sprite = new ImageIcon("./objects/bullet.png").getImage();
    }
    
    public boolean touching(Obstacle obstacle) {
        if(x >= obstacle.x && x <= (obstacle.x + (obstacle.large ? ap.tileSize*2 : ap.tileSize))) {
            if(y <= obstacle.y + (obstacle.large ? ap.tileSize*2 : ap.tileSize)) {
                return true;
            }
        }
        
        return false;
    }
}
