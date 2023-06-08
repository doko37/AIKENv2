/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package AIKEN;

import AIKEN.Data.GameState;
import java.util.ArrayList;
import java.util.Observable;

/**
 * This class manages all the data in the game and interacts with the database.
 * 
 * As this class extends Observable, it can update the observers whenever the data changes.
 * @author hunub
 */
public class Model extends Observable {
    private Database db;
    private Data data;
    private Sound sound;
    
    public Model() {
        this.db = new Database();
        this.db.dbSetup();
        this.data = new Data();
        this.data.shop = new Shop(db.getShopItems());
        this.data.gameState = GameState.STARTING_SCREEN;
        this.data.userList = db.getExistingUsers();
        this.sound = new Sound();
    }
    
    /**
     * This function is used when the user continues a game with an existing save.
     * 
     * If the input is an empty string, return.
     * Otherwise get the user by querying the database and set the current user to it.
     * Notify observers and set the gameState to MAIN_MENU.
     * @param petName 
     */
    public void getUserData(String petName) {
        this.data.newGame = false;
        this.data.refreshStartMenu = false;
        if(petName.equals("")) {
            setChanged();
            this.notifyObservers(data);
        } else {
            this.data.user = db.getUserData(petName);
            if(this.data.user == null) {
                setChanged();
                this.notifyObservers(data);
                return;
            } 
            this.data.user.getPet().model = this;
            this.data.user.getPet().start();
            setChanged();
            this.notifyObservers(data);
            changeGameState(GameState.MAIN_MENU);
        }
    }
    
    /**
     * Returns a string of usernames.
     * @return 
     */
    public ArrayList<String> getExistingUsers() {
        return db.getExistingUsers();
    }
    
    /**
     * This function is used when a user attempts to create a new game.
     * 
     * If the name already exists in the database, or if the name is empty or longer than 10 characters, then return.
     * 
     * Otherwise create a new User with default values and set the current user to it.
     * Notify observers and set the gameState to MAIN_MENU.
     * @param petName 
     */
    public void newUser(String petName) {
        this.data.newGame = true;
        this.data.refreshStartMenu = false;
        if(petName.equals("") || db.getUserData(petName) != null || petName.length() > 10) {
            setChanged();
            this.notifyObservers(data);
            return;
        }
        
        this.data.user = new User(new Pet(petName));
        this.data.user.getPet().model = this;
        this.data.user.getPet().start();
        setChanged();
        this.notifyObservers(data);
        changeGameState(GameState.MAIN_MENU);
    }
    
    /**
     * Function called when user attempts to use an item.
     * 
     * Get the item using the item name, check if the item is an instance of Food or Toy, 
     * and call the appropriate function (either user.feed() or user.play()).
     * 
     * @param itemName 
     */
    public void useItem(String itemName) {
        Item item = data.shop.get(itemName);
        if(item instanceof Food) {
            data.user.feed(itemName, data.shop);
        } else {
            data.user.play(itemName, data.shop);
        }
        
        this.data.autoRefresh = false;
        setChanged();
        notifyObservers(data);
    }
    
    /**
     * Function called when user attempts to buy an item.
     * 
     * Call the buyItem() function inside the User class and set the result to data.broke.
     * 
     * If the user is not broke (can afford the item) then play a sound.
     * @param itemName 
     */
    public void buyItem(String itemName) {
        data.broke = !data.user.buyItem(itemName, data.shop);
        if(!data.broke) {
            sound.setFile(4);
            sound.play();
        }
        data.autoRefresh = false;
        setChanged();
        notifyObservers(data);
    }
    
    /**
     * Update the gameStatus of the Pet class.
     */
    public void updatePetStatus() {
        this.data.autoRefresh = true;
        if(!data.user.getPet().isAlive) {
            data.gameState = GameState.PET_DIED;
        }
        setChanged();
        notifyObservers(data);
    }
    
    /**
     * This function updates the gameState of the game.
     * 
     * If the gameState is MAIN_MENU, then notify the pet so the thread continues.
     * 
     * If the gameState is STARTING_SCREEN, then set user to null.
     * @param gameState 
     */
    public void changeGameState(GameState gameState) {
        this.data.user.getPet().setState(gameState);
        this.data.broke = false;
        if(gameState == GameState.MAIN_MENU) {
            synchronized (this.data.user.getPet()) {
                this.data.user.getPet().notify();
            }
        } 
        
        if(gameState == GameState.STARTING_SCREEN) {
            this.data.user = null;
            this.data.refreshStartMenu = true;
        }
        this.data.gameState = gameState;
        data.autoRefresh = false;
        setChanged();
        notifyObservers(data);
    }
    
    /**
     * Increase the user's money by 10.
     */
    public void addCoin() {
        if(data.user != null) {
            data.user.setMoney(data.user.getMoney() + 10);
            setChanged();
            notifyObservers(data);
        }
    }
    
    /**
     * Decrease the user's health by 5.
     * 
     * If the health drops below 1, then set user's isAlive is true.
     */
    public void takeDamage() {
        if(data.user != null) {
            data.user.getPet().takeDamage(5);
            if(data.user.getPet().getHealth() < 1) {
                data.user.getPet().isAlive = false;
                data.gameState = GameState.PET_DIED;
            }
            setChanged();
            notifyObservers(data);
        }
    }
    
    /**
     * Set the gameState is QUIT if the gameState isn't already QUIT.
     */
    public void quitPrompt() {
        if(data.gameState != GameState.QUIT) {
            changeGameState(GameState.QUIT);
        }
    }
    
    /**
     * Call the deleteUser function in the database, then update the list of users.
     */
    public void deleteUser() {
        db.deleteUser(data);
        this.data.userList = getExistingUsers();
        changeGameState(GameState.STARTING_SCREEN);
    }
    
    /**
     * Save the user's game and then update the list of users.
     */
    public void saveGame() {
       db.saveGame(data);
       this.data.userList = getExistingUsers();
       changeGameState(GameState.STARTING_SCREEN);
    }
    
    /**
     * Set the gameState to EXIT.
     */
    public void quit() {
        data.gameState = GameState.EXIT;
        setChanged();
        notifyObservers(data);
    }
}
