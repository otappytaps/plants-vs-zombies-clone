package plants;

import javax.swing.ImageIcon;

public class Pea extends Projectile
{
    
    /**
     * Constructor for the Pea class (a subclass of the Projectile class).
     * @param RANGE how far the Pea object can travel measured in Tiles
     * @param ROW the row origin of the Pea object in the game
     * @param COL the column origin of the Pea object in the game
     */

    protected Pea(int RANGE, int ROW, int COL)
    {
        super(7, RANGE, 1.5f, ROW, COL); 
        sprite = new ImageIcon("images/Pea.png");
    }
}
