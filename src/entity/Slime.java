/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import AIKEN.AdventurePanel;
import AIKEN.KeyHandler;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
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
    
    public void setDefaultValues() {
        x = ap.tileSize*6;
        y = ap.screenHeight - ap.tileSize;
        speed = ap.tileSize;
    }
    
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
    
    public void draw(Graphics2D g2) {
//        g2.setColor(Color.white);
//        g2.fillRect(x, y, ap.tileSize, ap.tileSize);

        Image image = sprite;
        
        if(bullet.alive) {
            g2.drawImage(bullet.sprite, bullet.x, bullet.y, ap.tileSize/2, ap.tileSize/2, null);
        }
        
        g2.drawImage(image, x, y, ap.tileSize, ap.tileSize, null);
    }
}
