/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package AIKEN;

import java.util.ArrayList;

/**
 * This class contains all the data necessary for the AIKEN game.
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
    boolean refreshStartMenu;
    Shop shop;
    ArrayList<String> userList;
}
