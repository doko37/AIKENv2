/*
 * Author: Peter Lee
 * ID: 18040190
 */

package AIKEN;

import java.util.HashMap;
import java.util.Map;

import AIKEN.Data.GameState;

// This class represents the user.
// A user holds 3 attributes: the pet, money, and an inventory.
// The pet is an instance of the Pet class. Money is an integer, and the inventory is a HashMap with the name of an item as the key and the amount of that item being the value.
public class User {
    private final int INITIAL_MONEY = 100;
    private Pet pet;
    private int money;
    private HashMap<String, Integer> inventory;

    public User(Pet pet) {
        this.pet = pet;
        this.money = INITIAL_MONEY;
        inventory = new HashMap<>();
        inventory.put("pizza", 2);
        inventory.put("ball", 2);
    }

    public User(Pet pet, int money, HashMap<String, Integer> inventory) {
        this.pet = pet;
        this.money = money;
        this.inventory = inventory;
    }

    public Pet getPet() {
        return this.pet;
    }

    public int getMoney() {
        return this.money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    // This method takes two parameters, key: the name of an item, and shop: an
    // instance of Shop.
    // First, the method checks if an item of with the same name as key exists in
    // the shop.
    // Then checks if this user can afford the item by comparing the item's price
    // value and the user's money value.
    // If the item exists, and the user can afford it, then decrease the money value
    // by the item's price and put the name of the item as a key in the inventory.
    // The value is incremented by 1 if the key value already exists in the
    // inventory, otherwise it defaults to 1.
    public boolean buyItem(String key, Shop shop) {
        Item item = shop.get(key);
        if (item == null || (money - item.getPrice()) < 0)
            return false;

        money -= item.getPrice();
        inventory.put(key, inventory.getOrDefault(key, 0) + 1);
        return true;
    }

    public boolean ownsItem(String key) {
        return inventory.containsKey(key);
    }

    // This method takes two parameters, key: the name of an item, and shop: an
    // instance of Shop.
    // The method checks if this user owns the item. If the user does, then check
    // the amount the user owns.
    // If the amount is 1, then remove the item from the inventory, otherwise
    // decrease the value by 1.
    // Get the item from shop, and call the feed method on pet using it.
    public void feed(String key, Shop shop) {
        if (ownsItem(key)) {
            if (inventory.get(key) == 1) {
                inventory.remove(key);
            } else {
                inventory.replace(key, inventory.get(key) - 1);
            }
            Item item = shop.get(key);
            pet.feed((Food) item);
        }
    }

    // This method takes two parameters, key: the name of an item, and shop: an
    // instance of Shop.
    // The method checks if this user owns the item. If the user does, then check
    // the amount the user owns.
    // If the amount is 1, then remove the item from the inventory, otherwise
    // decrease the value by 1.
    // Get the item from shop, and call the play method on pet using it.
    public void play(String key, Shop shop) {
        if (ownsItem(key)) {
            if (inventory.get(key) == 1) {
                inventory.remove(key);
            } else {
                inventory.replace(key, inventory.get(key) - 1);
            }
            Item item = shop.get(key);
            pet.play((Toy) item);
        }
    }

    public void setPetState(GameState state) {
        pet.setState(state);
    }

    // Returns the combination of getFoods() and getToys().
    public HashMap<String, Integer> getInventory() {
        return this.inventory;
    }

    // Returns all the Food objects inside of the inventory.
    public String getFoods(Shop shop) {
        String str = "";

        for (Map.Entry<String, Integer> entry : this.inventory.entrySet()) {
            Item item = shop.get(entry.getKey());
            if (item instanceof Food) {
                str += "Item: " + item.getName() + " | Restore Level: " + ((Food) item).getRestoreLevel()
                        + " | Amount: "
                        + entry.getValue() + "\n";
            }
        }

        return str;
    }

    // Returns all the Toy objects inside of the inventory.
    public String getToys(Shop shop) {
        String str = "";

        for (Map.Entry<String, Integer> entry : this.inventory.entrySet()) {
            Item item = shop.get(entry.getKey());
            if (item instanceof Toy) {
                str += "Item: " + item.getName() + " | Fun Level: " + ((Toy) item).getFunLevel() + " | Tiring Level: "
                        + ((Toy) item).getTiringLevel() + " | Amount: "
                        + entry.getValue() + "\n";
            }
        }

        return str;
    }

    // Returns a String containing information about the pet and the user.
    // The first line contains the information of the pet, each piece of information
    // of seperated by a single space (' ').
    // The pet's information is written in the following order:
    // 1. The name of the pet,
    // 2. Hunger level,
    // 3. Happiness level,
    // 4. Health level,
    // 5. User's money amount.

    // After the first line, every other line is an item in the user's inventory.
    // Each item is formatted as such:
    // 1. The name of the item in lowercase,
    // 2. The amount of that item the user owned.
    // e.g. "ball 2"
    public String toString() {
        String str = pet.getPetName() + " " + pet.getHunger() + " " + pet.getHappiness() + " " + pet.getHealth() + " "
                + this.money + "\n";
        for (Map.Entry<String, Integer> entry : this.inventory.entrySet()) {
            str += entry.getKey() + " " + entry.getValue() + "\n";
        }

        return str;
    }
}
