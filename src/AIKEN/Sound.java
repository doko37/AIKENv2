/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package AIKEN;

import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * This sound manages all the sound files for the game.
 * @author hunub
 */
public class Sound {
    Clip clip;
    File[] files;
    
    /**
     * At construction, store the files in an array.
     */
    public Sound() {
        files = new File[7];
        files[0] = new File("./sounds/collect_coin.wav");
        files[1] = new File("./sounds/hurt.wav");
        files[2] = new File("./sounds/shoot.wav");
        files[3] = new File("./sounds/use_item.wav");
        files[4] = new File("./sounds/purchase.wav");
        files[5] = new File("./sounds/health_up.wav");
        files[6] = new File("./sounds/health_down.wav");
    }
    
    /**
     * Load a sound file that corresponds to the index.
     * @param i 
     */
    public void setFile(int i) {
        try {
            AudioInputStream ai = AudioSystem.getAudioInputStream(files[i]);
            clip = AudioSystem.getClip();
            clip.open(ai);
        } catch(Exception e) {
            
        }
    }
    
    public void play() {
        clip.start();
    }
}
