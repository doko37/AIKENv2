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
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;

/**
 *
 * @author hunub
 */
public class View extends JFrame implements Observer {
    JPanel userPanel = new JPanel();
    JPanel southPanel = new JPanel();
    JPanel mainScreen = new JPanel();
    JPanel statusPanel = new JPanel();
    JPanel startingScreen = new JPanel();
    JLabel itemLabel = new JLabel("Inventory: ");
    JPanel inventoryPanel = new JPanel();
    JLabel petName = new JLabel("Bob");
    JLabel pet = new JLabel("");
    JButton shop = new JButton("Shop");
    JButton casino = new JButton("Casino");
    JButton quit = new JButton("Quit");
    JButton back = new JButton("Go Back");
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
    ArrayList<JButton> items;
    HashMap<JButton, JSpinner> shopItems;

    
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
        items = new ArrayList<>();
        shopItems = new HashMap<>();
        this.add(startingScreen, BorderLayout.CENTER);
        this.setVisible(true);
    }
    
    public void startGame() {
        this.mainScreen.removeAll();
        this.userPanel.removeAll();
        this.statusPanel.removeAll();
        this.southPanel.removeAll();
        ImageIcon slimePic = new ImageIcon("./slimes/blue.png");
        this.slime = new JLabel(slimePic);
        
        this.mainScreen.setLayout(new BorderLayout());
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
        
        itemLabel.setBorder(new EmptyBorder(0, 0, 0, 500));
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.X_AXIS));
        
        inventoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        inventoryPanel.setBorder(new EmptyBorder(0, 0, 0, 400));
        itemLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        itemLabel.setBorder(new EmptyBorder(0, 0, 16, 0));
        southPanel.add(itemLabel);
        southPanel.add(inventoryPanel);
        southPanel.add(userPanel);
        
        this.getContentPane().removeAll();
        this.userPanel.setVisible(true);
        this.mainScreen.setVisible(true);
        this.userPanel.setVisible(true);
        this.inventoryPanel.setVisible(true);
        this.add(statusPanel, BorderLayout.NORTH);
        this.add(mainScreen, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
        this.pack();
        this.revalidate();
        this.repaint();
    }
    
    public void openShop(Data data) {
        southPanel.removeAll();
        mainScreen.removeAll();
        this.getContentPane().remove(southPanel);
        this.getContentPane().remove(mainScreen);
        mainScreen.setBackground(Color.white);
        southPanel.add(back);
        mainScreen.setLayout(new BoxLayout(mainScreen, BoxLayout.Y_AXIS));
        for(Map.Entry<String, Item> entry : data.shop.getItems().entrySet()) {
            Item item = entry.getValue();
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            JLabel img = new JLabel(new ImageIcon("./items/" + entry.getKey() + ".png"));
            img.setBorder(new EmptyBorder(0, 0, 0, 20));
            panel.add(img);
            panel.add(new JLabel(entry.getKey()));
            panel.add(new JLabel((item instanceof Food) ? "    |    Hunger restoration: " + ((Food) item).getRestoreLevel() : "    |    Happiness restoration: " + ((Toy) item).getFunLevel()));
            if(item instanceof Toy) panel.add(new JLabel("    |    Hunger loss: " + ((Toy) item).getTiringLevel()));
            JSpinner amount = new JSpinner();
            amount.setBorder(new EmptyBorder(0,0,0,0));
            JButton buy = new JButton("Buy " + entry.getKey());
            panel.add(amount);
            panel.add(buy);
            shopItems.put(buy, amount);
            panel.setBackground(Color.white);
            panel.setBorder(new EmptyBorder(10, 200, 10, 200));
            mainScreen.add(panel);
        }
        
        this.add(mainScreen, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();
        this.pack();
    }
    
    public void endGame(GameState gameState, boolean save) {
        
    }
    
    public void addActionListener(ActionListener listener) {
        this.back.addActionListener(listener);
        this.newGame.addActionListener(listener);
        this.continueGame.addActionListener(listener);
        this.shop.addActionListener(listener);
        this.casino.addActionListener(listener);
        this.quit.addActionListener(listener);
    }
    
    public void addItemsToListener(ActionListener listener) {
        for(JButton item : items) {
            item.addActionListener(listener);
        }
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
                    //startGame();
                }
            } else {
                if(data.user == null) {
                    this.message.setText("Please enter the name of your pet.");
                } else {
                    this.message.setText("");
                    this.petName.setText(data.user.getPet().getPetName());
                    this.pet.setText(data.user.getPet().toString());
                    //startGame();
                }
            }
        } else if(data.gameState == GameState.MAIN_MENU) {
            startGame();
            if(!data.autoRefresh) {
                int count = 0;
                this.items.clear();
                this.inventoryPanel.removeAll();
                for(Map.Entry<String, Integer> entry : data.user.getInventory().entrySet()) {
                    Item item = data.shop.get(entry.getKey());
                    JButton label = new JButton(entry.getKey() + " x" + entry.getValue());
                    label.setIcon(new ImageIcon("./items/" + entry.getKey().toLowerCase() + ".png"));
                    label.setHorizontalTextPosition(JLabel.CENTER);
                    label.setVerticalTextPosition(JLabel.BOTTOM);
                    label.setSize(32, 32);
                    label.addMouseListener(new java.awt.event.MouseAdapter() {
                        @Override
                        public void mouseEntered(java.awt.event.MouseEvent evt) {
                            String str = "    Item name: " + entry.getKey();
                            str += (item instanceof Food) ? "    |    Hunger restoration: " + ((Food) item).getRestoreLevel() : "    |    Happiness restoration: " + ((Toy) item).getFunLevel();
                            if(item instanceof Toy) str += "    |    Hunger loss: " + ((Toy) item).getTiringLevel();
                            itemLabel.setText("Inventory: " + str);
                            pack();
                        }

                        @Override
                        public void mouseExited(java.awt.event.MouseEvent evt) {
                            itemLabel.setText("Inventory: ");
                            pack();
                        }
                    });
                    this.items.add(label);
                    this.inventoryPanel.add(label);
                    
                    count++;
                }
                
                if(count < 1) itemLabel.setText("Inventory: ");
            }
            
            this.money.setText(Integer.toString(data.user.getMoney()));
            this.hungerBar.setValue(data.user.getPet().getHunger());
            this.happinessBar.setValue(data.user.getPet().getHappiness());
            this.healthBar.setValue(data.user.getPet().getHealth());
            this.revalidate();
            this.repaint();
            this.pack();
        } else if(data.gameState == GameState.SHOP) {
            openShop(data);
        } else if(data.gameState == GameState.CASINO) {
            
        } else if(data.gameState == GameState.QUIT || data.gameState == GameState.PET_DIED) {
            endGame(data.gameState, data.save);
        }
    }
}
