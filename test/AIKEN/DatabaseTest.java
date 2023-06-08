/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package AIKEN;

import java.util.ArrayList;
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
public class DatabaseTest {
    
    public DatabaseTest() {
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
     * Test of getUserData method, of class Database.
     * petName is empty, expected result: null.
     */
    @Test
    public void testGetUserDataEmptyPetName() {
        System.out.println("getUserDataEmptyPetName");
        String petName = "";
        Database instance = new Database();
        instance.dbSetup();
        User expResult = null;
        User result = instance.getUserData(petName);
        assertEquals(expResult, result);
    }
    
        /**
     * Test of getUserData method, of class Database.
     * User of petName does not exist in the database, expected result: null.
     */
    @Test
    public void testGetUserDataUserDoesNotExist() {
        System.out.println("getUserDataDoesNotExist");
        String petName = "userThatDoesNotExist";
        Database instance = new Database();
        instance.dbSetup();
        User expResult = null;
        User result = instance.getUserData(petName);
        assertEquals(expResult, result);
    }
    
        /**
     * Test of getUserData method, of class Database.
     * User exists, expected result: the user.
     */
    @Test
    public void testGetUserDataUserExists() {
        System.out.println("getUserDataUserExists");
        String petName = "userThatExists";
        Database instance = new Database();
        instance.dbSetup();
        Data data = new Data();
        data.user = new User(new Pet(petName));
        data.newGame = true;
        instance.saveGame(data);
        User expResult = data.user;
        User result = instance.getUserData(petName);
        
        Pet exPet = expResult.getPet();
        Pet resPet = result.getPet();
        
        if(!exPet.getPetName().equals(resPet.getPetName())) {
            fail("Name is not the same.");
        }
        
        if(expResult.getMoney() != expResult.getMoney()){
            fail("Money is not the same.");
        }
        
        if(exPet.getHunger() != resPet.getHunger()) {
            fail("Hunger is not the same.");
        }
        
        if(exPet.getHappiness() != resPet.getHappiness()) {
            fail("Happiness is not the same.");
        }
        
        if(exPet.getHealth() != resPet.getHealth()) {
            fail("Health is not the same.");
        }
    }
}
