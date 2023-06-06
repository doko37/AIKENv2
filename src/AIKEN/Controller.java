/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AIKEN;

import AIKEN.Data.GameState;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
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
    
    public void addItemsToListener() {
        this.view.addItemsToListener(this);
    }
    
    public void addShopItemsToListener() {
        this.view.addShopItemsToListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch(command) {
            case "Continue Game":
                model.getUserData(view.uInput.getText());
                addItemsToListener();
                addShopItemsToListener();
                break;
            case "New Game":
                model.newUser(view.uInput.getText());
                addItemsToListener();
                addShopItemsToListener();
                break;
            case "Shop":
                model.changeGameState(GameState.SHOP);
                addItemsToListener();
                break;
            case "Go Back":
                model.changeGameState(GameState.MAIN_MENU);
                addItemsToListener();
                break;
            case "Go Adventure!":
                model.changeGameState(GameState.ADVENTURE);
                break;
            default:
                String cmd = command.split(" ")[0];
                if(cmd.equals("Buy")) {
                    model.buyItem(command.split(" ")[1]);
                    sound.setFile(4);
                    sound.play();
                } else {
                    model.useItem(command.split(" ")[0]);
                    sound.setFile(3);
                    sound.play();
                }
                
                addItemsToListener();
                break;
        }
    }
}
