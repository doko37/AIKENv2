/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package AIKEN;

import java.io.File;
import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 *
 * @author hunub
 */
public class Sound {
    Clip clip;
    File[] files;
    
    public Sound() {
        files = new File[5];
        files[0] = new File("./sounds/collect_coin.wav");
        files[1] = new File("./sounds/hurt.wav");
        files[2] = new File("./sounds/shoot.wav");
        files[3] = new File("./sounds/use_item.wav");
        files[4] = new File("./sounds/purchase.wav");
    }
    
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
