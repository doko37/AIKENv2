/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AIKEN;

import AIKEN.Data.GameState;
import entity.Bullet;
import entity.ObstacleManager;
import entity.Slime;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import tile.TileManager;

/**
 *
 * @author hunub
 */
public class AdventurePanel extends JPanel implements Runnable {
    final int actualTileSize = 32;
    final int scale = 2;
    
    public final int tileSize = actualTileSize * scale;
    public final int maxScreenCol = 8;
    public final int maxScreenRow = 13;
    
    public final int screenWidth = tileSize * maxScreenRow; // 768px
    public final int screenHeight = tileSize * maxScreenCol; // 576px
    
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
        this.setBackground(Color.black);
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
        double drawInterval = 1000000000 / 60;
        double nextDrawTime = System.nanoTime() + drawInterval;
        
        while(gameState == GameState.ADVENTURE) {
            
            update();
            
            repaint();
            
            try {
                double remainingTime = nextDrawTime - System.nanoTime(); 
                remainingTime /= 1000000;
                
                if(remainingTime < 0) {
                    remainingTime = 0;
                }
                
                Thread.sleep((long) remainingTime);
                
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
