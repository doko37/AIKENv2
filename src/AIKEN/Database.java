/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AIKEN;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hunub
 */
public class Database {
    Connection conn = null;
    String username = "pdc";
    String password = "pdc";
    String url = "jdbc:derby:AIKENDB; create=true";
    
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
    
    public User getUserData(String petName) {
        User user = null;
        Pet pet = null;
        int money = 0;
        
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT UserInfo.PET_NAME, UserInfo.MONEY, Pet.HUNGER, Pet.HAPPINESS, Pet.HEALTH FROM UserInfo, Pet WHERE UserInfo.PET_NAME = Pet.NAME AND UserInfo.PET_NAME = " + petName);
            if(rs.next()) {
                pet = new Pet(petName, rs.getInt("HUNGER"), rs.getInt("HAPPINESS"), rs.getInt("HEALTH"));
                money = rs.getInt("MONEY");
            } else {
                return null;
            }
            
            rs = stmt.executeQuery("SELECT ITEM_NAME, AMOUNT FROM Inventory WHERE PET_NAME = " + petName);
            
            HashMap<String, Integer> inventory = new HashMap<>();
            while(rs.next()) {
                inventory.put(rs.getString("ITEM_NAME"), rs.getInt("AMOUNT"));
            }
            
            user = new User(pet, money, inventory);
        } catch(SQLException e) {
            
        }
        
        return user;
    }
    
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
    
    public void quitGame(User user, Pet pet) {
        try {
            Statement statement = conn.createStatement();
            statement.addBatch("UPDATE UserInfo SET MONEY = " + user.getMoney() + " WHERE PET_NAME = " + pet.getPetName());
            statement.addBatch("UPDATE Pet SET HUNGER = " + pet.getHunger() + ", HAPPINESS = " + pet.getHappiness() + ", HEALTH = " + pet.getHealth() + " WHERE NAME = " + pet.getPetName());
            for(Map.Entry<String, Integer> entry : user.getInventory().entrySet()) {
                statement.addBatch("INSERT INTO Inventory (PET_NAME, ITEM_NAME, AMOUNT) VALUES (" + pet.getPetName() + ", " + entry.getKey() + ", " + entry.getValue() + ")");
            }
            
            statement.executeBatch();
        } catch(SQLException e) {
            
        }
    }
    
    private void stockShop() {
        try {
            Statement stmt = conn.createStatement();
            stmt.execute("INSERT INTO Shop VALUES ('Burger', 20, 14, null, 'food'), ('Pizza', 16, 10, null, 'food'), ('Sushi', 9, 6, null, 'food'), " +
                    "('Cube', 14, 7, 1, 'toy'), ('Joyboy', 50, 30, 3, 'toy'), ('Ball', 9, 5, 2, 'toy')");
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
            System.out.println("check existing tables.... ");
            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);//types);
            //Statement dropStatement=null;
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    System.out.println(tableName + "  is there");
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
