/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package entity;

import AIKEN.AdventurePanel;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.ImageIcon;


/**
 * This class manages all the obstacles in the game.
 * @author hunub
 */
public final class ObstacleManager {
    ArrayList<Obstacle> spawnList;
    AdventurePanel ap;
    Random rand;
    Image[] images;
    int numAlive;
    
    public ObstacleManager(AdventurePanel ap) {
        this.ap = ap;
        this.rand = new Random();
        this.spawnList = new ArrayList<>();
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
    
    /**
     * This function iterates through each obstacle in the array and updates them based on game information.
     * 
     * If the number of obstacles alive is less than 1 then generate new obstacles.
     * 
     * For each obstacle:
     * Check if they are alive.
     * If they are, check if the y value of that obstacle is equal or greater than the final row.
     * If it is, then check if the x value is equal or greater than the character's x value AND the character's x value is
     * less than the obstacles x value + the tile size (tile size * 2 if it is a large tile).
     * If the obstacles coordinates match the character then check if the obstacle is a coin, if it is then addCoin and play a coin sound.
     * If it is not a coin, then damage the player and play a damage sound.
     * Set the obstacle's alive flag to false and move it outside of the screen so it cannot interfere with the game.
     * 
     * If the obstacle is hit by a bullet, the same procedures as above occur except the user does not take any damage, and the bullet is killed.
     * 
     * Move the obstacles y value by 6.
     * 
     * If the obstacle's y value is equal or greater than the height of the screen, kill it.
     * @param slime 
     */
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
    
    /**
     * This function populates an ArrayList with random Obstacles.
     * 
     * The number of obstacles generated is a random number between 3 and 12.
     * Each obstacle generated is also random.
     * There are 5 types of obstacles:
     * - Tree
     * - Large Tree
     * - Stone
     * - Large Stone
     * - Coin
     * 
     * All trees are destroyable with a bullet, however all stone cannot be destroyed.
     * Coins are a unique type of obstacle and can be destroyed.
     */
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
    }
    
    /**
     * Iterates through every obstacle in the arraylist and draws it on the screen.
     * @param g2 
     */
    public void draw(Graphics2D g2) {
        for(Obstacle ob : spawnList) {
            if(ob.alive) {
                int size = ob.large ? ap.tileSize*2 : ap.tileSize;
                g2.drawImage(ob.sprite, ob.x, ob.y, size, size, null);
            }
        }
    }
}
