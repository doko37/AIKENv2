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
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
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
    JButton adventureButton = new JButton("Go Adventure!");
    JButton quit = new JButton("Quit");
    JButton back = new JButton("Go Back");
    JButton saveGame = new JButton("Save Game");
    JButton notSave = new JButton("No Thanks");
    JButton notDelete = new JButton("Quit without deleting");
    JButton delete = new JButton("Delete and quit");
    JTextField uInput = new JTextField(10);
    JComboBox existingUsers = new JComboBox();
    JLabel uName = new JLabel("Pet Name");
    JLabel title = new JLabel("Welcome to AIKEN!");
    JLabel message = new JLabel("");
    JButton continueGame = new JButton("Continue Game");
    JButton newGame = new JButton("New Game");
    JButton cancel = new JButton("Cancel");
    JDialog quitPrompter = new JDialog();
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
    ImageIcon slimeIcon = new ImageIcon("");
    AdventurePanel adventure;
    ArrayList<JButton> items;
    HashMap<String, JButton> shopItems;

    
    public View(AdventurePanel ap, ArrayList<String> userList) {
        super("AIKEN");
        this.setIconImage(new ImageIcon("./slimes/blue_neutral.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 650);
        this.title.setFont(new Font("System", Font.BOLD, 24));
        this.startingScreen.add(title);
        this.existingUsers = new JComboBox(userList.toArray());
        this.startingScreen.add(existingUsers);
        this.startingScreen.add(continueGame);
        this.startingScreen.add(uName);
        this.startingScreen.add(uInput);
        this.startingScreen.add(newGame);
        this.startingScreen.add(message);
        this.adventure = ap;
        this.items = new ArrayList<>();
        this.shopItems = new HashMap<>();
        this.setResizable(false);
        this.add(startingScreen, BorderLayout.CENTER);
        this.setVisible(true);
    }
    
    public void startGame() {
        this.mainScreen.removeAll();
        this.mainScreen = new Background(new ImageIcon("./items/park_background2.png").getImage());
        this.userPanel.removeAll();
        this.statusPanel.removeAll();
        this.southPanel.removeAll();
        this.slime = new JLabel(slimeIcon);
        
        this.mainScreen.setLayout(new BorderLayout());
        this.setMinimumSize(new Dimension(768, 512));
        this.setMaximumSize(new Dimension(1000, 600));
        this.mainScreen.add(slime, BorderLayout.CENTER);
        this.mainScreen.remove(uName);
        this.mainScreen.remove(uInput);
        this.mainScreen.remove(continueGame);
        this.mainScreen.remove(newGame);
        this.mainScreen.setBackground(Color.white);
        
        this.userPanel.setLayout(new FlowLayout());
        this.userPanel.add(shop);
        this.userPanel.add(adventureButton);
        this.userPanel.add(quit);
        
        ImageIcon moneyPic = new ImageIcon("./items/money_tiny.png");
        this.moneyIcon = new JLabel(moneyPic);     
        this.statusPanel.setLayout(new FlowLayout());
        petName.setFont(new Font("System", Font.BOLD, 14));
        this.statusPanel.add(petName);
        hunger.setBorder(new EmptyBorder(0, 50, 0, 0));
        this.statusPanel.add(hunger);
        this.statusPanel.add(hungerBar);
        this.statusPanel.add(happiness);
        this.statusPanel.add(happinessBar);
        this.statusPanel.add(health);
        this.statusPanel.add(healthBar);
        filler.setBorder(new EmptyBorder(0, 0, 0, (50 - petName.getText().length())));
        this.statusPanel.add(filler);
        this.statusPanel.add(moneyIcon);
        this.statusPanel.add(money);
        
        //itemLabel.setBorder(new EmptyBorder(0, 0, 0, 500));
        inventoryPanel.setLayout(new BoxLayout(inventoryPanel, BoxLayout.X_AXIS));
        inventoryPanel.setMinimumSize(new Dimension(this.getWidth(), 60));
        
        inventoryPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        //inventoryPanel.setBorder(new EmptyBorder(0, 0, 0, 400));
        itemLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        southPanel.setLayout(new BoxLayout(southPanel, BoxLayout.Y_AXIS));
        itemLabel.setBorder(new EmptyBorder(0, 0, 16, 0));
        southPanel.add(itemLabel);
        southPanel.add(inventoryPanel);
        southPanel.add(userPanel);
        
        userPanel.setBackground(Color.LIGHT_GRAY);
        inventoryPanel.setBorder(new EmptyBorder(0, 0, 10, 0));
        
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
        money.setText(Integer.toString(data.user.getMoney()));
        this.getContentPane().remove(southPanel);
        this.getContentPane().remove(mainScreen);
        mainScreen.setBackground(Color.white);
        mainScreen.setAlignmentX(Component.CENTER_ALIGNMENT);
        userPanel.removeAll();
        userPanel.add(back);
        southPanel.add(userPanel);
        mainScreen = new Background(new ImageIcon("./items/shop_background2.jpg").getImage());
        mainScreen.setLayout(new BoxLayout(mainScreen, BoxLayout.Y_AXIS));
        for(Map.Entry<String, JButton> entry : shopItems.entrySet()) {
            Item item = data.shop.get(entry.getKey());
            JPanel main = new JPanel();
            main.setLayout(new BoxLayout(main, BoxLayout.X_AXIS));
            main.setAlignmentX(Component.CENTER_ALIGNMENT);
            JPanel panel = new JPanel();
            panel.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.setMinimumSize(new Dimension(500, 60));
            panel.setMaximumSize(new Dimension(500, 60));
            panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
            JLabel img = new JLabel(new ImageIcon("./items/" + entry.getKey() + ".png"));
            img.setBorder(new EmptyBorder(0, 15, 0, 20));
            panel.add(img);
            JLabel name = new JLabel(entry.getKey());
            name.setForeground(Color.white);
            panel.add(name);
            JLabel desc1 = new JLabel((item instanceof Food) ? "    |    Hunger restoration: " + ((Food) item).getRestoreLevel() : "    |    Happiness restoration: " + ((Toy) item).getFunLevel());
            desc1.setForeground(Color.white);
            panel.add(desc1);
            if(item instanceof Toy) {
                JLabel desc2 = new JLabel("    |    Hunger loss: " + ((Toy) item).getTiringLevel());
                desc2.setForeground(Color.white);
                panel.add(desc2);
            }
            
            JPanel panelR = new JPanel();
            panelR.setLayout(new BoxLayout(panelR, BoxLayout.X_AXIS));
            JButton buy = entry.getValue();
            buy.setText(Integer.toString(item.price));
            buy.setMinimumSize(new Dimension(100, 60));
            buy.setMaximumSize(new Dimension(100, 60));
            buy.setIcon(new ImageIcon("./items/money_tiny.png"));
            buy.setAlignmentX(Component.RIGHT_ALIGNMENT);
            panelR.setAlignmentX(RIGHT_ALIGNMENT);
            panelR.setMinimumSize(new Dimension(100, 60));
            panelR.setMaximumSize(new Dimension(100, 60));
            panelR.setBackground(new Color(0,0,0,0));
            //panelR.add(amount);
            panelR.add(buy);
            main.add(panel);
            main.add(panelR);
            shopItems.put(entry.getKey(), buy);
            panel.setBackground(new Color(0,0,0,125));;
            main.setBackground(new Color(0,0,0,0));
            main.setBorder(new EmptyBorder(10, 0, 10, 0));
            mainScreen.add(main);
        }
        
        this.add(mainScreen, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }
    
    public void addActionListener(ActionListener listener) {
        this.back.addActionListener(listener);
        this.newGame.addActionListener(listener);
        this.continueGame.addActionListener(listener);
        this.shop.addActionListener(listener);
        this.adventureButton.addActionListener(listener);
        this.quit.addActionListener(listener);
        this.saveGame.addActionListener(listener);
        this.notSave.addActionListener(listener);
        this.cancel.addActionListener(listener);
        this.notDelete.addActionListener(listener);
        this.delete.addActionListener(listener);
    }
    
    public void addShopItemsToListener(ActionListener listener) {
        for(Map.Entry<String, JButton> entry : shopItems.entrySet()) {
            JButton button = entry.getValue();
            button.setActionCommand("Buy " + entry.getKey());
            button.addActionListener(listener);
        }
    }
    
    public void addItemsToListener(ActionListener listener) {
        for(JButton item : items) {
            item.addActionListener(listener);
        }
    }
    
    public void startAdventure() {
        this.getContentPane().removeAll();
        this.adventure.gameState = GameState.ADVENTURE;
        this.add(adventure, BorderLayout.CENTER);
        userPanel.removeAll();
        userPanel.add(back);
        southPanel.removeAll();
        southPanel.add(userPanel);
        this.add(statusPanel, BorderLayout.NORTH);
        this.add(southPanel, BorderLayout.SOUTH);
        this.adventure.requestFocusInWindow();
        adventure.gameState = GameState.ADVENTURE;
        adventure.startGameThread();
        
        this.pack();
        this.revalidate();
        this.repaint();
    }
    
    public void quitPrompt(Data data) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        String prompt;
        
        boolean petDied = !data.user.getPet().isAlive;
        
        if(!petDied) {
            prompt = "Would you like you save your game?";
        } else {
            if(data.newGame) {
                prompt = data.user.getPet().getPetName() + " has died! Since you do not have a previous saved nothing will be saved.";
            } else {
                prompt = data.user.getPet().getPetName() + " has died! Would you like to delete your save?";
            }
        }
        
        panel.add(new JLabel(prompt));
        if(!petDied)
            panel.add(new JLabel("(Your game will be saved under the name \"" + data.user.getPet().getPetName() + "\")"));
        
        JPanel options = new JPanel();
        
        if(petDied) {
            if(data.newGame) {
                JButton quit2 = new JButton("Quit");
                quit2.addActionListener((ActionEvent e) -> {
                    System.exit(0); 
                });
                        
                options.add(quit2);
            } else {
                options.add(notDelete);
                options.add(delete);
            }
        } else {
            options.add(saveGame);
            options.add(notSave);
            options.add(cancel);
        }
        
        panel.add(options);
        
        quitPrompter.setLocationRelativeTo(this);
        Point location = this.getLocationOnScreen();
        quitPrompter.setLocation(location.x + (this.getWidth() / 4), location.y + (this.getHeight() / 2));
        quitPrompter.setAlwaysOnTop(true);
        quitPrompter.setContentPane(panel);
        quitPrompter.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        quitPrompter.pack();
        
        quitPrompter.setModal(true);
        quitPrompter.setVisible(true);
    }
    
    @Override
    public void update(Observable o, Object arg) {
        Data data = (Data) arg;
        switch(data.gameState) {
            case STARTING_SCREEN:
                if(!data.newGame) {
                if(data.user == null) {
                    this.message.setText("Pet name does not exist, please try again.");
                } else {
                    this.message.setText("");
                    this.petName.setText(data.user.getPet().getPetName());
                    this.pet.setText(data.user.getPet().toString());
                    for(Map.Entry<String, Item> entry : data.shop.getItems().entrySet()) {
                        JButton button = new JButton();
                        shopItems.put(entry.getKey(), button);
                    }
                }
                } else {
                    if (this.uInput.getText().equals("")) {
                        this.message.setText("Please enter the name of your pet.");
                    }
                    else if(data.user == null) {
                        this.message.setText("Username already exists, please choose a different name.");
                    } else {
                        this.message.setText("");
                        this.petName.setText(data.user.getPet().getPetName());
                        this.pet.setText(data.user.getPet().toString());
                        for(Map.Entry<String, Item> entry : data.shop.getItems().entrySet()) {
                            JButton button = new JButton();
                            shopItems.put(entry.getKey(), button);
                        }
                    }
                }
                break;
            
            case MAIN_MENU:
                if(quitPrompter.isVisible()) quitPrompter.dispose();
                adventure.gameState = GameState.MAIN_MENU;
                int petHealth = data.user.getPet().getHealth();
                if(petHealth < 20) {
                    slimeIcon = new ImageIcon("./slimes/blue_sick.png");
                } else if(petHealth < 40) {
                    slimeIcon = new ImageIcon("./slimes/blue_unhappy.png");
                } else if(petHealth < 70){
                    slimeIcon = new ImageIcon("./slimes/blue_neutral.png");
                } else {
                    slimeIcon = new ImageIcon("./slimes/blue_happy.png");
                }
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

                    if(count < 1) {
                        itemLabel.setText("Inventory: ");
                        inventoryPanel.add(new JLabel("Your inventory is empty! Go to the shop to buy more items."));
                    }
                }

                this.money.setText(Integer.toString(data.user.getMoney()));
                this.hungerBar.setValue(data.user.getPet().getHunger());
                this.happinessBar.setValue(data.user.getPet().getHappiness());
                this.healthBar.setValue(data.user.getPet().getHealth());
                this.revalidate();
                this.repaint();
                this.pack();
                break;
            case SHOP:
                openShop(data);
                this.revalidate();
                this.repaint();
                this.pack();
                break;
            case ADVENTURE:
                if(adventure.gameState != GameState.ADVENTURE) {
                    startAdventure();
                }

                this.money.setText(Integer.toString(data.user.getMoney()));
                this.hungerBar.setValue(data.user.getPet().getHunger());
                this.happinessBar.setValue(data.user.getPet().getHappiness());
                this.healthBar.setValue(data.user.getPet().getHealth());

                this.revalidate();
                this.repaint();
                this.pack();
                break;
            case QUIT:
            case PET_DIED:
                if(!quitPrompter.isVisible()) {
                    quitPrompt(data);
                }
                break;
            case EXIT:
                System.exit(0);
                break;
        }
    }
}