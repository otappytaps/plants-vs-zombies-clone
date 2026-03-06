package plants;

import javax.swing.ImageIcon;

public class Puff extends Projectile
{
    
    /**
     * Constructor for the Puff class (a subclass of the Projectile class).
     * @param RANGE how far the Puff object can travel measured in Tiles
     * @param ROW the row origin of the Puff object in the game
     * @param COL the column origin of the Puff object in the game
     */

    protected Puff(int RANGE, int ROW, int COL)
    {
        super(2, RANGE, 0.3f, ROW, COL); 
        sprite = new ImageIcon("images/Puff.png");
    }
}
