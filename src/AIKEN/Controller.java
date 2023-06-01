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
    
    
    public Controller(View view) {
        this.view = view;
        this.view.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
    }
}
