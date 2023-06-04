/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AIKEN;

import AIKEN.Data.GameState;
import java.util.Observable;

/**
 *
 * @author hunub
 */
public class Model extends Observable {
    private Database db;
    private Data data;
    
    public Model() {
        this.db = new Database();
        this.db.dbSetup();
        this.data = new Data();
        this.data.shop = new Shop(db.getShopItems());
        this.data.gameState = GameState.STARTING_SCREEN;
    }
    
    public void getUserData(String petName) {
        this.data.newGame = false;
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
            startGame();
        }
    }
    
    public void newUser(String petName) {
        this.data.newGame = true;
        if(petName.equals("")) {
            setChanged();
            this.notifyObservers(data);
            return;
        }
        
        this.data.user = new User(new Pet(petName));
        this.data.user.getPet().model = this;
        this.data.user.getPet().start();
        setChanged();
        this.notifyObservers(data);
        startGame();
    }
    
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
    
    public void startGame() {
        this.data.gameState = GameState.MAIN_MENU;
        setChanged();
        notifyObservers(data);
    }
    
    public void updatePetStatus() {
        this.data.autoRefresh = true;
        if(!data.user.getPet().isAlive) {
            data.gameState = GameState.PET_DIED;
        }
        setChanged();
        notifyObservers(data);
    }
    
    public void changeGameState(GameState gameState) {
        this.data.user.getPet().setState(gameState);
        if(gameState == GameState.MAIN_MENU) {
            synchronized (this.data.user.getPet()) {
                this.data.user.getPet().notify();
            }
        } 
        this.data.gameState = gameState;
        setChanged();
        notifyObservers(data);
    }
}
