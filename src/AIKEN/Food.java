/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package AIKEN;

// This class is a sub class of the Item class.
// Food has the special attribute restoreLevel, which determines how much of the pet's hunger level is restored.
public class Food extends Item {
    private int restoreLevel;

    public Food(String _name, int _price, int _level) {
        super(_name, _price);
        restoreLevel = _level;
    }

    public int getRestoreLevel() {
        return this.restoreLevel;
    }

    public String toString() {
        return this.name + " " + this.price + " " + this.restoreLevel;
    }
}
