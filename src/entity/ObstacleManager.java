/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import AIKEN.AdventurePanel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Queue;
import java.util.Random;
import javax.swing.ImageIcon;


/**
 *
 * @author hunub
 */
public class ObstacleManager {
    Obstacle[] obstacles;
    ArrayList<Obstacle> spawnList;
    AdventurePanel ap;
    Random rand;
    Image[] images;
    int numAlive;
    
    public ObstacleManager(AdventurePanel ap) {
        this.ap = ap;
        this.rand = new Random();
        this.spawnList = new ArrayList<>();
        this.obstacles = new Obstacle[4];
        this.images = new Image[4];
        this.numAlive = 0;
        getImages();
    }
    
    public void getImages() {
        images[0] = new ImageIcon("./objects/stone.png").getImage();
        images[1] = new ImageIcon("./objects/tree.png").getImage();
        images[2] = new ImageIcon("./objects/tree2.png").getImage();
        images[3] = new ImageIcon("./objects/money.png").getImage();
    }
    
    public void update(Slime slime) {
        Bullet bullet = slime.bullet;
        
        if(numAlive < 1) {
            generateObstacles();
        }
        
        for(Obstacle curr : spawnList) {
            if(curr.alive) {
                if(curr.y + ap.tileSize >= ap.screenHeight - ap.tileSize && curr.y <= ap.screenHeight) {
                    if(slime.x >= curr.x && slime.x < (curr.x + (curr.large ? ap.tileSize*2 : ap.tileSize))) {
                        if(curr.coin) {
                            ap.addCoin();
                            ap.playSound(0);
                        } else {
                            ap.takeDamage();
                            ap.playSound(1);
                        }
                        curr.alive = false;
                        curr.x = -64;
                        curr.y = -64;
                        numAlive--;
                    }
                }
            
                if(bullet.alive && bullet.x >= curr.x && bullet.x <= (curr.x + (curr.large ? ap.tileSize*2 : ap.tileSize))) {
                    if(bullet.y <= curr.y + (curr.large ? ap.tileSize*2 : ap.tileSize)) {
                        if(!curr.bulletProof) {
                            if(curr.coin) {
                                ap.addCoin();
                                ap.playSound(0);
                            }
                            curr.alive = false;
                            curr.x = -64;
                            curr.y = -64;
                            numAlive--;
                        }
                        ap.killBullet();
                    }
                }
                
                curr.y += 6;
                if(curr.y >= ap.screenHeight) {
                    curr.alive = false;
                    numAlive--;
                    break;
                }
            }
        }
        
    }
    
    public void generateObstacles() {
        spawnList.clear();
        numAlive = 0;
        for(int i = 0; i < (rand.nextInt(10) + 3); i++) {
            int randNum = rand.nextInt(4);
            Obstacle temp = new Obstacle(images[randNum], randNum == 2 ? true : randNum == 0 ? rand.nextBoolean() : false, randNum == 0 ? true : false, randNum == 3 ? true : false);
            boolean unique = true;
            while(unique) {
                int randPos = ap.tileSize * rand.nextInt(13);
                for(Obstacle ob : spawnList) {
                    if(ob.x == randPos) {
                        unique = false;
                        break;
                    }
                }
                if(unique) {
                    temp.x = randPos;
                    break;
                }
            }
            
            temp.alive = true;

            spawnList.add(temp);
            numAlive++;
        }
        
        //spawnList.add(temp);
    }
    
    public void draw(Graphics2D g2) {
        for(Obstacle ob : spawnList) {
            if(ob.alive) {
                int size = ob.large ? ap.tileSize*2 : ap.tileSize;
                g2.drawImage(ob.sprite, ob.x, ob.y, size, size, null);
            }
        }
    }
}
