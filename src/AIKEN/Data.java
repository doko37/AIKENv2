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
        CASINO,
        QUIT
    }
    
    GameState gameState;
    User user;
    boolean newGame;
}
