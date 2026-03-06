package plants;

import javax.swing.ImageIcon;

public class Fume extends Projectile
{

    /**
     * Constructor for the Fume class (a subclass of the Projectile class).
     * @param RANGE how far the Fume object can travel measured in Tiles
     * @param ROW the row origin of the Fume object in the game
     * @param COL the column origin of the Fume object in the game
     */

    protected Fume(int RANGE, int ROW, int COL)
    {
        super(15, RANGE, 0.3f, ROW, COL); 
        sprite = new ImageIcon("images/Fume.png");
    }
}
