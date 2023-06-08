/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package entity;

import java.awt.Image;

/**
 * This is an abstract class that represents an entity.
 * 
 * An entity has 5 attributes:
 * - x value
 * - y value
 * - speed
 * - image
 * - and a alive flag.
 * @author hunub
 */
public class Entity {
    public int x, y;
    public int speed;
    public Image sprite;
    public boolean alive;
}
