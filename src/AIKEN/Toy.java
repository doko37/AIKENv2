/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package AIKEN;

// This class is a sub class of the Item class.
// Toy has the special attributes funLevel and tiringLevel.
// funLevel determines how much of the pet's happiness level is restored and tiringLevel determines how much of the pet's hunger level of decreased.
public class Toy extends Item {
    private int funLevel;
    private int tiringLevel;

    public Toy(String _name, int _price, int _funLevel, int _tiringLevel) {
        super(_name, _price);
        funLevel = _funLevel;
        tiringLevel = _tiringLevel;
    }

    public int getFunLevel() {
        return this.funLevel;
    }

    public int getTiringLevel() {
        return this.tiringLevel;
    }

    public String toString() {
        return this.name + " " + this.price + " " + this.funLevel + " " + this.tiringLevel;
    }
}
