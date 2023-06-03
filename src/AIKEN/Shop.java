/*
 * Author: Peter Lee
 * ID: 18040190
 */

package AIKEN;

import java.util.HashMap;
import java.util.Map;

// This class represents the Shop in the game.
// The shop holds items which then can be bought by the player using money.
// Each item in the shop is stored in a HashMap, where the key values are the names of the item in lower case, and the value is the item itself.
public class Shop {
    private HashMap<String, Item> items;

    public Shop(HashMap<String, Item> items) {
        this.items = items;
    }

    // Manually add an item to the shop
    public void add(Item item) {
        items.put(item.getName().toLowerCase(), item);
    }

    public Item get(String name) {
        return items.getOrDefault(name.toLowerCase(), null);
    }

    public String toString() {
        String str = "";

        for (Map.Entry<String, Item> entry : items.entrySet()) {
            Item temp = entry.getValue();
            str += "Item: " + temp.getName() + " (" + temp.getClass().getSimpleName() + ") | Price: " + temp.getPrice();
            if (temp instanceof Food) {
                str += " | Restore Level: " + ((Food) temp).getRestoreLevel();
            } else {
                str += " | Fun Level: " + ((Toy) temp).getFunLevel() + " | Tiring Level: "
                        + ((Toy) temp).getTiringLevel();
            }

            str += "\n";
        }

        return str;
    }
}
