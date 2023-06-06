/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.awt.Image;

/**
 *
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
