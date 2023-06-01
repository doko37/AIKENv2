/*
 * Author: Peter Lee
 * ID: 18040190
 */

package AIKEN;

import java.util.Random;

// This class holds a Random object.
public class Casino {
    Random rand;

    public Casino() {
        rand = new Random();
    }

    // Return a random boolean (true or false).
    public boolean flip() {
        return rand.nextBoolean();
    }
}