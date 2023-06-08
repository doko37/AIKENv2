/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package AIKEN;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class manages the connection with the database.
 * @author hunub
 */
public class Database {
    Connection conn = null;
    String username = "pdc";
    String password = "pdc";
    String url = "jdbc:derby:AIKENDB; create=true";
    
    /**
     * Database set up.
     * 
     * Create 4 tables:
     * - UserInfo
     * - Pet
     * - Shop
     * - Inventory
     * 
     * UserInfo contains the name of the pet (which also functions as the username) and the amount of money.
     * 
     * Pet contains the name of the pet and the values of hunger, happiness, and health (all integer values).
     * 
     * The shop contains all the items that will be in the shop
     * Each item has an item name, price, restoration, a hunger loss value (if the item is a toy), and a type.
     * 
     * The inventory table contains all the items owned by users.
     * Each item is identified by the name of the user who owns it, and the name of item and the amount of that item the user owns.
     */
    public void dbSetup() {
        try {
            this.conn = DriverManager.getConnection(url, username, password);
            try(Statement stmt = conn.createStatement()) {
                String tableUser = "UserInfo";
                String tablePet = "Pet";
                String tableShop = "Shop";
                String tableInventory = "Inventory";

                if(!tableExists(tableUser)) {
                    stmt.executeUpdate("CREATE TABLE " + tableUser + " (PET_NAME VARCHAR(40), MONEY INT, PRIMARY KEY (PET_NAME))");
                }

                if(!tableExists(tablePet)) {
                    stmt.executeUpdate("CREATE TABLE " + tablePet + " (NAME VARCHAR(40), HUNGER INT, HAPPINESS INT, HEALTH INT, FOREIGN KEY (NAME) REFERENCES UserInfo(PET_NAME))");
                }

                if(!tableExists(tableShop)) {
                    stmt.executeUpdate("CREATE TABLE " + tableShop + " (ITEM_NAME VARCHAR(30), PRICE INT, RESTORATION INT, HUNGER_LOSS INT, ITEM_TYPE VARCHAR(10), PRIMARY KEY (ITEM_NAME))");
                    stockShop();
                }

                if(!tableExists(tableInventory)) {
                    stmt.executeUpdate("CREATE TABLE " + tableInventory + " (PET_NAME VARCHAR(40), ITEM_NAME VARCHAR(30), AMOUNT INT, FOREIGN KEY(ITEM_NAME) REFERENCES Shop(ITEM_NAME), FOREIGN KEY(PET_NAME) REFERENCES UserInfo(PET_NAME))");
                }

                stmt.close();
            }          
        } catch(SQLException e) {
            System.out.println(e);
        }
    }
    
    /**
     * Returns a list of usernames.
     * @return 
     */
    public ArrayList<String> getExistingUsers() {
        ArrayList list = new ArrayList<>();
        
        try {
            Statement stmt = conn.createStatement();
            
            ResultSet rs = stmt.executeQuery("SELECT PET_NAME FROM UserInfo");
            while(rs.next()) {
                list.add(rs.getString("PET_NAME"));
            }
        } catch(SQLException e) {
            
        }
        
        return list;
    }
    
    /**
     * Retrieves user info based on the pet name.
     * 
     * Queries for userInfo and Pet where the name is equal to the input.
     * If no results are found, return null.
     * 
     * Otherwise get all the items owned by that user and save it into a HashMap.
     * Create a new user with the data retrieved and return it.
     * @param petName
     * @return 
     */
    public User getUserData(String petName) {
        User user = null;
        Pet pet = null;
        int money = 0;
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT UserInfo.PET_NAME, UserInfo.MONEY, Pet.HUNGER, Pet.HAPPINESS, Pet.HEALTH FROM UserInfo, Pet WHERE UserInfo.PET_NAME = Pet.NAME AND UserInfo.PET_NAME = '" + petName + "'");
            if(rs.next()) {
                pet = new Pet(petName, rs.getInt("HUNGER"), rs.getInt("HAPPINESS"), rs.getInt("HEALTH"));
                money = rs.getInt("MONEY");
            } else {
                return null;
            }
            
            rs = stmt.executeQuery("SELECT ITEM_NAME, AMOUNT FROM Inventory WHERE PET_NAME = '" + petName + "'");
            
            HashMap<String, Integer> inventory = new HashMap<>();
            while(rs.next()) {
                inventory.put(rs.getString("ITEM_NAME"), rs.getInt("AMOUNT"));
            }
            
            user = new User(pet, money, inventory);
        } catch(SQLException e) {
            
        }
        
        return user;
    }
    
    
    /**
     * Inserts a new row inside the UserInfo and Pet tables.
     * @param data 
     */
    public void createNewUser(Data data) {
        User user = data.user;
        Pet pet = user.getPet();
        
        try {
            Statement stmt = conn.createStatement();
            stmt.addBatch("INSERT INTO UserInfo VALUES ('" + pet.getPetName() + "', " + user.getMoney() + ")");
            stmt.addBatch("INSERT INTO Pet VALUES ('" + pet.getPetName() + "', " + pet.getHunger() + ", " + pet.getHappiness() + ", " + pet.getHealth() + ")");
            stmt.executeBatch();
        } catch(SQLException e) {
            
        }
    }
    
    /**
     * Deletes all rows from the Pet, Inventory, and UserInfo table where the name of the pet is equal to the input.
     * @param data 
     */
    public void deleteUser(Data data) {
        User user = data.user;
        Pet pet = user.getPet();
        
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("DELETE FROM Pet WHERE NAME = '" + pet.getPetName() + "'");
            stmt.execute("DELETE FROM Inventory WHERE PET_NAME = '" + pet.getPetName() + "'");
            stmt.execute("DELETE FROM UserInfo WHERE PET_NAME = '" + pet.getPetName() +"'");
        } catch(SQLException e) {
            
        }
    }
    
    /**
     * Returns a HashMap containing all the items in the Shop table, where the name of the item is the key, and the item itself is the value.
     * @return 
     */
    public HashMap<String, Item> getShopItems() {
        HashMap<String, Item> shopItems = new HashMap<>();
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Shop");
            while(rs.next()) {
                String type = rs.getString("ITEM_TYPE");
                if(type.equals("toy")) {
                    shopItems.put(rs.getString("ITEM_NAME").toLowerCase(), new Toy(rs.getString("ITEM_NAME"), rs.getInt("PRICE"), rs.getInt("RESTORATION"), rs.getInt("HUNGER_LOSS")));
                } else if(type.equals("food")) {
                    //shopItems.put(rs.getString("ITEM_NAME").toLowerCase(), new Food(rs.getString("ITEM_NAME"), rs.getInt("PRICE"), rs.getInt("RESTORATION")));
                    String name = rs.getString("ITEM_NAME");
                    int price = rs.getInt("PRICE");
                    int restoration = rs.getInt("RESTORATION");
                    
                    shopItems.put(name.toLowerCase(), new Food(name, price, restoration));
                }
            }
        } catch(SQLException e) {
            return null;
        }
        
        return shopItems;
    }
    
    /**
     * Updates the UserInfo, Pet and Inventory tables with new data.
     * @param data 
     */
    public void saveGame(Data data) {
        User user = data.user;
        Pet pet = user.getPet();
        
        try {
            Statement statement = conn.createStatement();
            if(data.newGame) {
                createNewUser(data);
            } else {
                statement.addBatch("UPDATE UserInfo SET MONEY = " + user.getMoney() + " WHERE PET_NAME = '" + pet.getPetName() + "'");
                statement.addBatch("UPDATE Pet SET HUNGER = " + pet.getHunger() + ", HAPPINESS = " + pet.getHappiness() + ", HEALTH = " + pet.getHealth() + " WHERE NAME = '" + pet.getPetName() + "'");
            }
            
            // Delete all rows with items the user used to own.
            statement.addBatch("DELETE FROM Inventory WHERE PET_NAME = '" + pet.getPetName() + "'");
            
            // Re the current items the user owns.
            for(Map.Entry<String, Integer> entry : user.getInventory().entrySet()) {
                
                statement.addBatch("INSERT INTO Inventory (PET_NAME, ITEM_NAME, AMOUNT) VALUES ('" + pet.getPetName() + "', '" + entry.getKey() + "', " + entry.getValue() + ")");
            }
            
            statement.executeBatch();
        } catch(SQLException e) {
            
        }
    }
    
    /**
     * Used to populate the Shop table with items if a new one was created.
     */
    private void stockShop() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO Shop VALUES ('burger', 20, 14, null, 'food'), ('pizza', 16, 10, null, 'food'), ('sushi', 9, 6, null, 'food'), " +
                    "('cube', 14, 7, 1, 'toy'), ('joyboy', 50, 30, 3, 'toy'), ('ball', 9, 5, 2, 'toy')");
        } catch(SQLException e) {
            
        }
    }
    
    /**
     * Returns true if table specified table exists, false otherwise.
     * @param newTableName
     * @return 
     * @source Copied from PDC lab 9
     */
    private boolean tableExists(String newTableName) {
        boolean flag = false;
        try {
            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);//types);
            //Statement dropStatement=null;
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    flag = true;
                }
            }
            if (rsDBMeta != null) {
                rsDBMeta.close();
            }
        } catch (SQLException ex) {
        }
        return flag;
    }
}
