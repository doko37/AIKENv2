/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package AIKEN;

import AIKEN.Data.GameState;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;

/**
 * The Controller class of AIKEN.
 * This class listens to all actions from the GUI (such as button presses and text inputs)
 * and depending on which action was sent will change the game data.
 * @author hunub
 */
public class Controller implements ActionListener {
    public View view;
    public Model model;
    Sound sound;
    
    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        this.sound = new Sound();
        this.view.addActionListener(this);
    }
    
    // Add this as a listener to the item buttons.
    public void addItemsToListener() {
        this.view.addItemsToListener(this);
    }
    
    // Add this as a listener to the shop item buttons.
    public void addShopItemsToListener() {
        this.view.addShopItemsToListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // Updates the observer asynchronously
        SwingUtilities.invokeLater(() -> {
            String command = e.getActionCommand();
            
            switch(command) {
                // Continue game with existing user.
                case "Continue Game":
                    if(view.existingUsers.getSelectedItem() != null) {
                        model.getUserData((String) view.existingUsers.getSelectedItem());
                        addItemsToListener();
                        addShopItemsToListener();
                    }
                    break;

                // Start a new game.
                case "New Game":
                    model.newUser(view.uInput.getText().toLowerCase());
                    addItemsToListener();
                    addShopItemsToListener();
                    break;

                // Open the shop.
                case "Shop":
                    model.changeGameState(GameState.SHOP);
                    addItemsToListener();
                    break;

                // Go back to main menu.
                case "Go Back":
                    model.changeGameState(GameState.MAIN_MENU);
                    addItemsToListener();
                    break;

                // Open the adventure mini-game.
                case "Go Adventure!":
                    model.changeGameState(GameState.ADVENTURE);
                    break;

                // Prompt user if they want to go back to the starting screen.
                case "Back to starting screen":
                    model.quitPrompt();
                    break;

                // Go back to starting screen without deleting the user's save.
                case "Don't delete my save!":
                    model.changeGameState(GameState.STARTING_SCREEN);
                    break;

                // Delete the user's save and go back to starting screen.
                case "Delete my save":
                    model.deleteUser();
                    break;

                // Save the user's game and go back to starting screen.
                case "Save Game":
                    model.saveGame();
                    break;

                // Go back to starting screen.
                case "No Thanks":
                    model.changeGameState(GameState.STARTING_SCREEN);
                    break;

                // Go back to main menu.
                case "Cancel":
                    model.changeGameState(GameState.MAIN_MENU);
                    addItemsToListener();
                    break;

                // Go back to starting screen.
                case "Back to Main Screen":
                    model.changeGameState(GameState.STARTING_SCREEN);
                    break;

                // Quit the game.
                case "Quit Game":
                    model.quit();
                    break;
                    
                /**
                 * The default case is when an item button is pressed.
                 * If an item button was pressed, then one of two things are possible:
                 * 1. The user is buying and item,
                 * 2. The user is using an item.
                 * 
                 * If the user is buying an item, then string will be "Buy " followed by the name of the item.
                 * The string is split into two sub-strings to obtain the name of the item.
                 * 
                 * If the user is using and item, then the string will be the name of the item followed by " xTheAmount".
                 * We can do the same processing as above to get the name of the item.
                 */
                default:
                    String cmd = command.split(" ")[0];
                    if(cmd.equals("Buy")) {
                        model.buyItem(command.split(" ")[1]);
                    } else {
                        model.useItem(command.split(" ")[0]);
                        sound.setFile(3);
                        sound.play();
                    }

                    addItemsToListener();
                break;
            }
        });
    }
}
