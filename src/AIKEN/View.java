/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
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
import java.awt.Point;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.border.EmptyBorder;

/**
 * This class is the JFrame that holds the entire GUI side of the application. 
 * @author hunub
 */
public final class View extends JFrame implements Observer {
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
    JButton backToStart = new JButton("Back to starting screen");
    JButton quit = new JButton("Quit Game");
    JButton back = new JButton("Go Back");
    JButton saveGame = new JButton("Save Game");
    JButton notSave = new JButton("No Thanks");
    JButton notDelete = new JButton("Don't delete my save!");
    JButton delete = new JButton("Delete my save");
    JButton backToMain = new JButton("Back to Main Screen");
    JTextField uInput = new JTextField(10);
    JComboBox existingUsers = new JComboBox();
    JLabel uName = new JLabel("Pet Name: ");
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
    JLabel happiness = new JLabel(" Happiness: ");
    JLabel health = new JLabel(" Health: ");
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
        this.adventure = ap;
        this.setIconImage(new ImageIcon("./slimes/blue_neutral.png").getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(798, 565));
        this.setMaximumSize(new Dimension(798, 565));
        this.setResizable(false);
        startMenu(userList);
        this.setVisible(true);
    }
    
    /**
     * Paints the starting menu onto the screen.
     * 
     * The starting menu contains one main component: the startingScreen Panel.
     * 
     * Contains the title of the game, and a small form where the user can:
     * - Choose to continue a game by choosing from a list of existing users and pressing the continue button
     * - Start a new game by entering a unique pet name and pressing the start button
     * - Exit the application.
     * 
     * If the user inputs any invalid pet names, a message will be prompted shown.
     * @param userList 
     */
    public void startMenu(ArrayList<String> userList) {
        this.getContentPane().removeAll();
        this.startingScreen.removeAll();
        this.startingScreen = new Background(new ImageIcon("./items/home_background_resized.jpg").getImage());
        this.startingScreen.setLayout(new BoxLayout(startingScreen, BoxLayout.Y_AXIS));
        this.startingScreen.setMinimumSize(new Dimension(798, 565));
        this.startingScreen.setMaximumSize(new Dimension(798, 565));
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setMinimumSize(new Dimension(400, 220));
        form.setMaximumSize(new Dimension(400, 220));
        form.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.title.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.title.setBorder(new EmptyBorder(50, 0, 50, 0));
        form.setBackground(new Color(0,0,0,175));
        this.existingUsers = new JComboBox(userList.toArray());
        JPanel row1 = new JPanel();
        row1.setOpaque(false);
        row1.setBorder(new EmptyBorder(5, 10, 0, 10));
        row1.add(existingUsers);
        row1.add(continueGame);
        row1.setAlignmentY(Component.CENTER_ALIGNMENT);
        
        JLabel label1 = new JLabel("Returning users, please choose your last save file: ");
        label1.setAlignmentX(Component.CENTER_ALIGNMENT);
        label1.setForeground(Color.white);
        label1.setBorder(new EmptyBorder(10, 0, 0, 0));
        form.add(label1);
        form.add(row1);
        
        JPanel row2 = new JPanel();
        row2.setBorder(new EmptyBorder(0, 10, 0, 10));
        row2.setLayout(new BoxLayout(row2, BoxLayout.Y_AXIS));
        JPanel row2sub1 = new JPanel();
        row2.setOpaque(false);
        row2sub1.setOpaque(false);
        this.uName.setForeground(Color.white);
        row2sub1.add(uName);
        row2sub1.add(uInput);
        row2sub1.add(newGame);
        
        JLabel label2 = new JLabel("Or, create a new game: ");
        label2.setAlignmentX(Component.CENTER_ALIGNMENT);
        label2.setForeground(Color.white);
        form.add(label2);
        row2.add(row2sub1);
        
        JPanel row2sub2 = new JPanel();
        row2sub2.setOpaque(false);
        message.setForeground(Color.white);
        row2sub2.add(message);
        row2.add(row2sub2);
        
        row2.setAlignmentY(Component.CENTER_ALIGNMENT);
        
        form.add(row2);

        quit.setAlignmentX(Component.CENTER_ALIGNMENT);
        form.setBorder(new EmptyBorder(0, 0, 15, 0));
        form.add(quit);
        File file = new File("./fonts/upheavtt.ttf");
        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, file);
            Font sizedFont = font.deriveFont(32f);
            title.setFont(sizedFont);
        } catch (Exception e) {
            
        }
        this.startingScreen.add(title);
        this.startingScreen.add(form);
        this.items = new ArrayList<>();
        this.shopItems = new HashMap<>();
        this.add(startingScreen, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
        this.pack();
    }
    
    /**
     * Prints the main menu of the game.
     * 
     * The main menu contains 3 main components:
     * - statusPanel, which contains the pet's information such as name, hunger, etc. and the user's money.
     * - The main screen, which contains the sprite of the pet, and the background image.
     * - The user panel which contains the inventory, and all the buttons for navigation.
     */
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
        this.userPanel.add(backToStart);
        
        ImageIcon moneyPic = new ImageIcon("./items/money_tiny.png");
        this.moneyIcon = new JLabel(moneyPic);     
        this.statusPanel.setLayout(new FlowLayout());
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
    }
    
    /**
     * Prints the shop onto the screen.
     * 
     * The shop contains the statusPanel and the userPanel like the main menu, but
     * the main screen contains a list of items with information such as price and a button for purchasing the item.
     * 
     * The items are retrieved by iterating through a HashMap which contains all of the items of the shop.
     * For each entry in the map, a new JPanel is created which contains all the components of the item
     * such as picture, name, price, etc.
     * 
     * If the user cannot afford an item, then a message is prompted shown on screen.
     * @param data 
     */
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
            panel.setBackground(new Color(0,0,0,125));
            main.setBackground(new Color(0,0,0,0));
            main.setBorder(new EmptyBorder(10, 0, 10, 0));
            mainScreen.add(main);
        }
        
        if(data.broke) {
            JLabel alert = new JLabel("You cannot afford this!");
            alert.setOpaque(true);
            alert.setBorder(new EmptyBorder(10,10,10,10));
            alert.setBackground(new Color(0,0,0,125));
            alert.setForeground(Color.white);
            alert.setAlignmentX(Component.CENTER_ALIGNMENT);
            alert.setBorder(new EmptyBorder(0, 0, 10, 0));
;           this.mainScreen.add(alert);
        }
        this.add(mainScreen, BorderLayout.CENTER);
        this.add(southPanel, BorderLayout.SOUTH);
    }
    
    /**
     * Add an ActionListener to all of the buttons.
     * @param listener 
     */
    public void addActionListener(ActionListener listener) {
        this.back.addActionListener(listener);
        this.newGame.addActionListener(listener);
        this.continueGame.addActionListener(listener);
        this.shop.addActionListener(listener);
        this.adventureButton.addActionListener(listener);
        this.backToStart.addActionListener(listener);
        this.saveGame.addActionListener(listener);
        this.notSave.addActionListener(listener);
        this.cancel.addActionListener(listener);
        this.notDelete.addActionListener(listener);
        this.delete.addActionListener(listener);
        this.backToMain.addActionListener(listener);
        this.quit.addActionListener(listener);
    }
    
    /**
     * Add an ActionListener to all the item buttons in the shop.
     * @param listener 
     */
    public void addShopItemsToListener(ActionListener listener) {
        for(Map.Entry<String, JButton> entry : shopItems.entrySet()) {
            JButton button = entry.getValue();
            button.setActionCommand("Buy " + entry.getKey());
            button.addActionListener(listener);
        }
    }
    
    /**
     * Add an ActionListener to all of the item buttons in the inventory.
     * @param listener 
     */
    public void addItemsToListener(ActionListener listener) {
        for(JButton item : items) {
            item.addActionListener(listener);
        }
    }
    
    /**
     * Prints the adventure mini-game.
     * 
     * Replaces the mainScreen with an AdventurePanel.
     */
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
    
    /**
     * Prompts the user when the user wants to go back to the starting screen or when the pet dies.
     * 
     * If the user wants to go back to the starting screen, prompt the user with three options:
     * - Save current game
     * - Don't save game
     * - Cancel
     * 
     * If cancel is pressed, then the game goes back to running, otherwise the user is sent back to the starting screen.
     * 
     * If the pet has died, then ask the user if they would like to delete their save, and then send them back to the starting screen.
     * @param data 
     */
    public void quitPrompt(Data data) {
        JPanel ctn = new JPanel();
        ctn.setLayout(new BoxLayout(ctn, BoxLayout.Y_AXIS));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        String prompt;
        
        boolean petDied = !data.user.getPet().isAlive;
        
        if(!petDied) {
            prompt = "Would you like you save your game?";
        } else {
            if(data.newGame) {
                prompt = data.user.getPet().getPetName() + " has died! Since you do not have a previous save nothing will be saved.";
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
                options.add(backToMain);
            } else {
                options.add(notDelete);
                options.add(delete);
            }
        } else {
            options.add(saveGame);
            options.add(notSave);
            options.add(cancel);
        }
        
        ctn.add(panel);
        ctn.add(options);
        
        quitPrompter.setLayout(new BoxLayout(quitPrompter, BoxLayout.Y_AXIS));
        quitPrompter.setLocationRelativeTo(this);
        Point location = this.getLocationOnScreen();
        quitPrompter.setLocation(location.x + (this.getWidth() / 4), location.y + (this.getHeight() / 2));
        quitPrompter.setAlwaysOnTop(true);
        quitPrompter.setContentPane(ctn);
        quitPrompter.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        quitPrompter.pack();
        
        quitPrompter.setModal(true);
        quitPrompter.setVisible(true);
    }
    
    /**
     * This function runs whenever the Model class notifies the observer with new data.
     * @param o
     * @param arg 
     */
    @Override
    public void update(Observable o, Object arg) {
        Data data = (Data) arg;
        
        if(quitPrompter.isVisible() || data.gameState != GameState.QUIT || data.gameState != GameState.PET_DIED) {
            quitPrompter.dispose();
        }
        
        switch(data.gameState) {
            // If the gameState is STARTING_SCREEN, then check if the user wants to create a new game, or continue a save.
            case STARTING_SCREEN:
                this.adventure.gameState = data.gameState;
                this.message.setText("");
                startMenu(data.userList);
                if(!data.refreshStartMenu) {
                    // If the user is continuing a save, then set the label components to the pet's information and create item buttons from the shop and store those buttons in a HashMap
                    // so an ActionListener can be added to them later.
                    if(!data.newGame) {
                        this.message.setText("");
                        this.petName.setText(data.user.getPet().getPetName());
                        this.pet.setText(data.user.getPet().toString());
                        for(Map.Entry<String, Item> entry : data.shop.getItems().entrySet()) {
                            JButton button = new JButton();
                            shopItems.put(entry.getKey(), button);
                        }
                        
                    // If the user is creating a new game then check that:
                    // the name is not an empty string, less than 10 characters long, and that the name does not already exist in the database.
                    // If all of the above are satisfied then set the label components to the pet's information and create item buttons from the shop and store those buttons in a HashMap
                    // so an ActionListener can be added to them later.
                    } else {
                        if (this.uInput.getText().equals("")) {
                            this.message.setText("Please enter the name of your pet.");
                        } else if(this.uInput.getText().length() > 10) {
                            this.message.setText("Your Pet name must be between 1 - 10 characters long.");
                        } else if(data.user == null) {
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
                }
                
                this.uInput.setText("");
                break;
            
            case MAIN_MENU:
                if(quitPrompter.isVisible()) quitPrompter.dispose();
                this.adventure.gameState = data.gameState;
                int petHealth = data.user.getPet().getHealth();
                
                // Change the sprite of the pet depending on its health.
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
                
                // If the screen is not being autoRefreshed, then create clear the current item button list and re-populate it with the user's current inventory.
                // Update the pet's status in the status panel.
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
                this.adventure.gameState = data.gameState;
                if(!quitPrompter.isVisible()) {
                    quitPrompt(data);
                }
                break;
            case EXIT:
                System.exit(0);
                break;
            default:
                break;
        }
    }
}