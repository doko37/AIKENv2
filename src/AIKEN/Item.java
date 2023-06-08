/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package AIKEN;

// This abstract class represents an item.
// An item has: a name and a price.
public abstract class Item {
    protected String name;
    protected int price;

    public Item(String _name, int _price) {
        name = _name;
        price = _price;
    }

    public String getName() {
        return this.name;
    }

    public int getPrice() {
        return this.price;
    }

    // The toString() method returns just the name of the item.
    public String toString() {
        return this.name;
    }
}
