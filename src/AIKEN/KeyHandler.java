/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package AIKEN;

import entity.Slime;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class listens to key input from the user.
 * Used for handling input in the adventure mini-game.
 * @author hunub
 */
public class KeyHandler implements KeyListener {
    public boolean left, right, shoot;
    public boolean keyPressed = false;

    @Override
    public void keyTyped(KeyEvent e) {
        keyPressed(e);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // The character should only move one per key press. Having this flag prevents the user from being able to hold the key to move the character.
        if (keyPressed) {
            return;
        }
        
        int code = e.getKeyCode();

        switch(code) {
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
            case KeyEvent.VK_SPACE:
                shoot = true;
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        switch(code) {
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
            case KeyEvent.VK_SPACE:
                shoot = false;
                break;
        }
        
        keyPressed = false;
    }
    
}
