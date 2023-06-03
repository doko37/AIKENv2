/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AIKEN;

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
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        
        switch(command) {
            case "Continue Game":
                model.getUserData(view.uInput.getText());
                break;
            case "New Game":
                model.newUser(view.uInput.getText());
                break;
        }
    }
}
