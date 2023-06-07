/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AIKEN;

import AIKEN.Data.GameState;

/**
 *
 * @author hunub
 */
public class AIKENMain {
    public static void main(String args[]) {
        Model model = new Model();
        AdventurePanel ap = new AdventurePanel(model, GameState.STARTING_SCREEN);
        View view = new View(ap, model.getExistingUsers());
        Controller controller = new Controller(view, model);
        model.addObserver(view);
    }
}
