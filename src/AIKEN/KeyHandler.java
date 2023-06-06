/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AIKEN;

import entity.Slime;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
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
