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
    
    
    public Controller(View view, Model model) {
        this.view = view;
        this.model = model;
        this.view.addActionListener(this);
    }
    
    public void addItemsToListener() {
        this.view.addItemsToListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch(command) {
            case "Continue Game":
                model.getUserData(view.uInput.getText());
                addItemsToListener();
                break;
            case "New Game":
                model.newUser(view.uInput.getText());
                addItemsToListener();
                break;
            case "Shop":
                model.changeGameState(GameState.SHOP);
                break;
            case "Go Back":
                model.changeGameState(GameState.MAIN_MENU);
            default:
                model.useItem(command.split(" ")[0]);
                addItemsToListener();
                break;
        }
    }
}
