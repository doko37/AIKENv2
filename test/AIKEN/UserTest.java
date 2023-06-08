/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package AIKEN;

import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author hunub
 */
public class UserTest {
    
    public UserTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of buyItem method, of class User.
     */
    @Test
    public void testBuyItemButItemDoesNotExist() {
        System.out.println("testBuyItemButItemDoesNotExist");
        String key = "itemThatDoesNotExist";
        Database db = new Database();
        db.dbSetup();
        Shop shop = new Shop(db.getShopItems());
        User instance = new User(new Pet("test"));
        boolean expResult = false;
        boolean result = instance.buyItem(key, shop);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of buyItem method, of class User.
     */
    @Test
    public void testBuyItemCannotAfford() {
        System.out.println("testBuyItemCannotAfford");
        String key = "pizza";
        Database db = new Database();
        db.dbSetup();
        Shop shop = new Shop(db.getShopItems());
        User instance = new User(new Pet("test"));
        instance.setMoney(0);
        boolean expResult = false;
        boolean result = instance.buyItem(key, shop);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of buyItem method, of class User.
     */
    @Test
    public void testBuyItemCanAfford() {
        System.out.println("testBuyItemCanAfford");
        String key = "pizza";
        Database db = new Database();
        db.dbSetup();
        Shop shop = new Shop(db.getShopItems());
        User instance = new User(new Pet("test"));
        boolean expResult = true;
        boolean result = instance.buyItem(key, shop);
        assertEquals(expResult, result);
    }
}
