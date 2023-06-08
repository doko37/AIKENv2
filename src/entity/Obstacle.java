/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package entity;

import java.awt.Image;

/**
 * This class represents the obstacle in the adventure mini-game.
 * 
 * An obstacle is an Entity with the following properties:
 * - large flag
 * - bulletproof flag
 * - coin flag
 * @author hunub
 */
public class Obstacle extends Entity {
    boolean large;
    boolean bulletProof;
    boolean coin;
    
    public Obstacle(Image image, boolean large, boolean bulletProof, boolean coin) {
        sprite = image;
        speed = 4;
        y = 0;
        this.large = large;
        this.bulletProof = bulletProof;
        this.coin = coin;
    }
}
