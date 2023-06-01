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
import java.awt.Color;

/**
 *
 * @author hunub
 */
public class View extends JFrame implements Observer {
    private JPanel userPanel = new JPanel();
    private JPanel mainScreen = new JPanel();
    private JPanel statusPanel = new JPanel();
    private JLabel petName = new JLabel("Bob");
    private JButton inventory = new JButton("Inventory");
    private JButton shop = new JButton("Shop");
    private JButton casino = new JButton("Casino");
    private JButton quit = new JButton("Quit");
    
    public View() {
        super("AIKEN");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        
        this.mainScreen.add(petName);
        userPanel.setBackground(Color.gray);
        userPanel.add(inventory);
        userPanel.add(shop);
        userPanel.add(casino);
        userPanel.add(quit);
        this.add(statusPanel, BorderLayout.NORTH);
        this.add(mainScreen, BorderLayout.CENTER);
        this.add(userPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }
    
    public void addActionListener(ActionListener listener) {
        this.inventory.addActionListener(listener);
        this.shop.addActionListener(listener);
        this.casino.addActionListener(listener);
        this.quit.addActionListener(listener);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        
    }
}
