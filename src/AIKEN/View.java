/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AIKEN;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;

/**
 *
 * @author hunub
 */
public class View extends JFrame implements Observer {
    public View() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 400);
        this.setVisible(true);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        
    }
}
