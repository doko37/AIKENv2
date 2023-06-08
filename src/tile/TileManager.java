/*
 * Author: Peter Lee
 * ID: 18040190
 * PDC Assignment 2
 */

package tile;

import AIKEN.AdventurePanel;
import java.awt.Graphics2D;
import javax.swing.ImageIcon;

/**
 * This class manages an array of tiles.
 * @author hunub
 */
public final class TileManager {
    AdventurePanel ap;
    Tile[] tile;
    
    public TileManager(AdventurePanel ap) {
        this.ap = ap;
        
        tile = new Tile[4];
        
        getTileImage();
    }
    
    // Populate array with tiles.
    public void getTileImage() {
        tile[0] = new Tile();
        tile[0].image = new ImageIcon("./tiles/grass_tile.png").getImage();
        tile[1] = new Tile();
        tile[1].image = new ImageIcon("./tiles/tree_tile.png").getImage();
        tile[2] = new Tile();
        tile[2].image = new ImageIcon("./tiles/tree_tile2.png").getImage();
        tile[3] = new Tile();
        tile[3].image = new ImageIcon("./tiles/money_tile.png").getImage();
    }
    
    // Draw tiles on the screen based on the width and height of the screen, and the size of each tile.
    public void draw(Graphics2D g2) {
        int x = ap.tileSize, y = ap.tileSize; 
        
        for(int col = 0; col < ap.maxScreenCol; col++) {
            for(int row = 0; row < ap.maxScreenRow; row++) {
                g2.drawImage(tile[0].image, x*row, y*col, ap.tileSize, ap.tileSize, null);
            }
        }
    }
}
