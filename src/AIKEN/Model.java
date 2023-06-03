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
    private Shop shop;
    
    public Model() {
        this.db = new Database();
        this.db.dbSetup();
        this.data = new Data();
        this.shop = new Shop(db.getShopItems());
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
    
    public void startGame() {
        this.data.gameState = GameState.MAIN_MENU;
        setChanged();
        notifyObservers(data);
    }
    
    public void updatePetStatus() {
        setChanged();
        notifyObservers(data);
    }
}
