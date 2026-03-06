package plants;

import javax.swing.ImageIcon;

public class SnowPea extends SlowingProjectile
{
    
    /**
     * Constructor for the SnowPea class (a subclass of the SlowingProjectile class).
     * @param RANGE how far the SnowPea object can travel measured in Tiles
     * @param ROW the row origin of the SnowPea object in the game
     * @param COL the column origin of the SnowPea object in the game
     */

    protected SnowPea(int RANGE, int ROW, int COL)
    {
        super(7, RANGE, 1.5f, ROW, COL); 
        sprite = new ImageIcon("images/SnowPea.png");
        slowFactor = 1.5f;
    }
}
