/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AIKEN;

/**
 *
 * @author hunub
 */
public class Data {
    enum GameState {
        STARTING_SCREEN,
        MAIN_MENU,
        INVENTORY,
        SHOP,
        ADVENTURE,
        QUIT,
        PET_DIED,
        EXIT
    }
    
    GameState gameState;
    User user;
    boolean newGame;
    boolean autoRefresh;
    boolean broke;
    Shop shop;
}
