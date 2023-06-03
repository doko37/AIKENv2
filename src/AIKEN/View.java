/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AIKEN;

import AIKEN.Data.GameState;
import javax.swing.*;
import java.util.Observable;
import java.util.Observer;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;

/**
 *
 * @author hunub
 */
public class View extends JFrame implements Observer {
    JPanel userPanel = new JPanel();
    JPanel mainScreen = new JPanel();
    JPanel statusPanel = new JPanel();
    JPanel startingScreen = new JPanel();
    JPanel inventoryPanel = new JPanel();
    JLabel petName = new JLabel("Bob");
    JLabel pet = new JLabel("");
    JButton shop = new JButton("Shop");
    JButton casino = new JButton("Casino");
    JButton quit = new JButton("Quit");
    JTextField uInput = new JTextField(10);
    JLabel uName = new JLabel("Pet Name");
    JLabel title = new JLabel("Welcome to AIKEN!");
    JLabel message = new JLabel("");
    JButton continueGame = new JButton("Continue Game");
    JButton newGame = new JButton("New Game");
    JProgressBar hungerBar = new JProgressBar(0, 100);
    JProgressBar happinessBar = new JProgressBar(0, 100);
    JProgressBar healthBar = new JProgressBar(0, 100);
    JLabel hunger = new JLabel("Hunger: ");
    JLabel happiness = new JLabel("Happiness: ");
    JLabel health = new JLabel("Health: ");
    JLabel slime = null;
    JLabel moneyIcon = null;
    JLabel money = new JLabel("0");
    JLabel filler = new JLabel("");

    
    public View() {
        super("AIKEN");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        title.setFont(new Font("System", Font.BOLD, 24));
        startingScreen.add(title);
        startingScreen.add(message);
        startingScreen.add(uName);
        startingScreen.add(uInput);
        startingScreen.add(continueGame);
        startingScreen.add(newGame);
        this.add(startingScreen, BorderLayout.CENTER);
        this.setVisible(true);
    }
    
    public void startGame() {        
        ImageIcon slimePic = new ImageIcon("./slimes/blue.png");
        this.slime = new JLabel(slimePic);
        
        this.mainScreen.add(slime, BorderLayout.CENTER);
        this.mainScreen.remove(uName);
        this.mainScreen.remove(uInput);
        this.mainScreen.remove(continueGame);
        this.mainScreen.remove(newGame);
        this.mainScreen.setBackground(Color.white);
        
        this.userPanel.setLayout(new FlowLayout());
        this.userPanel.add(shop);
        this.userPanel.add(casino);
        this.userPanel.add(quit);
        
        ImageIcon moneyPic = new ImageIcon("./items/money_tiny.png");
        this.moneyIcon = new JLabel(moneyPic);     
        this.statusPanel.setLayout(new FlowLayout());
        petName.setFont(new Font("System", Font.BOLD, 14));
        this.statusPanel.add(petName);
        hunger.setBorder(new EmptyBorder(0, 150, 0, 0));
        this.statusPanel.add(hunger);
        this.statusPanel.add(hungerBar);
        this.statusPanel.add(happiness);
        this.statusPanel.add(happinessBar);
        this.statusPanel.add(health);
        this.statusPanel.add(healthBar);
        filler.setBorder(new EmptyBorder(0, 0, 0, (125 - petName.getText().length())));
        this.statusPanel.add(filler);
        this.statusPanel.add(moneyIcon);
        this.statusPanel.add(money);
        
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.Y_AXIS));
        JPanel inventory = new JPanel();
        JPanel inventoryInterface = new JPanel();
        inventoryInterface.add(new JButton("Use"));
        inventory.setLayout(new GridLayout(3, 2));
        String[] test = new String[] {"pizza", "burger", "sushi", "ball", "joyboy", "toy"};
        
        for(String t : test) {
            JLabel label = new JLabel(t);
            label.setIcon(new ImageIcon("./items/" + t + ".png"));
            label.setHorizontalTextPosition(JLabel.CENTER);
            label.setVerticalTextPosition(JLabel.BOTTOM);
            label.setSize(32, 32);
            label.setBorder(BorderFactory.createLineBorder(Color.black));
            inventory.add(label);
        }
        
        inventoryPanel.add(inventory);
        inventoryPanel.add(inventoryInterface);
        
        this.getContentPane().removeAll();
        this.userPanel.setVisible(true);
        this.mainScreen.setVisible(true);
        this.userPanel.setVisible(true);
        this.inventoryPanel.setVisible(true);
        this.add(statusPanel, BorderLayout.NORTH);
        this.add(mainScreen, BorderLayout.CENTER);
        this.add(userPanel, BorderLayout.SOUTH);
        this.add(inventoryPanel, BorderLayout.WEST);
        this.pack();
        this.revalidate();
        this.repaint();
    }
    
    public void addActionListener(ActionListener listener) {
        this.newGame.addActionListener(listener);
        this.continueGame.addActionListener(listener);
        this.shop.addActionListener(listener);
        this.casino.addActionListener(listener);
        this.quit.addActionListener(listener);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        Data data = (Data) arg;
        if(data.gameState == GameState.STARTING_SCREEN) {
            if(!data.newGame) {
                if(data.user == null) {
                    this.message.setText("Pet name does not exist, please try again.");
                } else {
                    this.message.setText("");
                    this.petName.setText(data.user.getPet().getPetName());
                    this.pet.setText(data.user.getPet().toString());
                    startGame();
                }
            } else {
                if(data.user == null) {
                    this.message.setText("Please enter the name of your pet.");
                } else {
                    this.message.setText("");
                    this.petName.setText(data.user.getPet().getPetName());
                    this.pet.setText(data.user.getPet().toString());
                    startGame();
                }
            }
        } else if(data.gameState == GameState.MAIN_MENU) {
            this.hungerBar.setValue(data.user.getPet().getHunger());
            this.happinessBar.setValue(data.user.getPet().getHappiness());
            this.healthBar.setValue(data.user.getPet().getHealth());
        }
    }
}
