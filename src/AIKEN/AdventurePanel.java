/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package AIKEN;

import AIKEN.Data.GameState;
import entity.Bullet;
import entity.ObstacleManager;
import entity.Slime;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import tile.TileManager;

/**
 * This is a JPanel which holds all the components for the adventure mini-game.
 * It manages the updating and drawing of the character, tiles and obstacles in a 2d plain.
 * @author hunub
 */
public class AdventurePanel extends JPanel implements Runnable {
    // Size of a tile 32x32px
    final int actualTileSize = 32;
    final int scale = 2;
    
    // Size of tile once scaled up.
    public final int tileSize = actualTileSize * scale;
    
    // The number of tiles on the screen: 8 columns and 13 rows.
    public final int maxScreenCol = 8;
    public final int maxScreenRow = 13;
    
    public final int screenWidth = tileSize * maxScreenRow; // 813px
    public final int screenHeight = tileSize * maxScreenCol; // 512px
    
    TileManager tileManager;
    ObstacleManager obManager;
    KeyHandler keyH;
    Thread thread;
    GameState gameState;
    Slime slime;
    Model model;
    Sound sound;
    
    public AdventurePanel(Model model, GameState gameState) {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setMinimumSize(new Dimension(screenWidth, screenHeight));
        this.setMaximumSize(new Dimension(screenWidth, screenHeight));
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setDoubleBuffered(true);
        this.keyH = new KeyHandler();
        this.slime = new Slime(this, keyH);
        this.slime.setDefaultValues();
        this.tileManager = new TileManager(this);
        this.obManager = new ObstacleManager(this);
        this.addKeyListener(keyH);
        this.setFocusable(true);
        this.model = model;
        this.sound = new Sound();
    }
    
    public void startGameThread() {
        this.thread = new Thread(this);
        this.thread.start();
    }
    
    public void killBullet() {
        this.slime.bullet.alive = false;
    }
    
    public void addCoin() {
        model.addCoin();
    }
    
    public void takeDamage() {
        model.takeDamage();
    }
    
    @Override
    public void run() {
        // The game will update 60 times a second. Meaning one billion nanoseconds / 60.
        double drawInterval = 1000000000 / 60;
        
        // The next time the game needs to update is the current time + (1 second / 60)
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        while(gameState == GameState.ADVENTURE) {
            
            update();
            
            repaint();
            
            try {
                // After the game updates, calculate how much time remains until the next update.
                double remainingTime = nextDrawTime - System.nanoTime(); 
                remainingTime /= 1000000;
                
                
                // If the remaining time is 0, (meaning it took more than 0.01667 seconds to process the previous frame),
                // then set the remaining time to 0.
                if(remainingTime < 0) {
                    remainingTime = 0;
                }
                
                // Wait for the remaining time before running the loop again.
                Thread.sleep((long) remainingTime);
                
                // Update the next update time.
                nextDrawTime += drawInterval;
            } catch(InterruptedException e) {
                
            }
        }
    }
    
    public void update() {
        slime.update();
        obManager.update(slime);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D) g;
        
        tileManager.draw(g2);
        
        obManager.draw(g2);
        
        slime.draw(g2);
        
        g2.dispose();
    }
    
    
    public void playSound(int i) {
        sound.setFile(i);
        sound.play();
    }
}
