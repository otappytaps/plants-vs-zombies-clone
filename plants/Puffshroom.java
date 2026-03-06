package plants;

import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Puffshroom extends NightPlant implements AttackPlant
{
    private static final int COST = 0;
    private static final float REGENERATE_RATE = 7.5f;

    /**
     * Constructor for the Puffshroom class (subclass of the NightPlant class that implements AttackPlant).
     * Runs the shoot() method.
     * @param ROW the row of where the Puffshroom object would reside in the game
     * @param COL the column of where the Puffshroom object would reside in the game
     * @param gameProjectiles the ArrayList of projectiles from the game
     */

    public Puffshroom(int ROW, int COL, ArrayList<Projectile> gameProjectiles)
    {
        super(60, 0, 4, 1.0f, ROW, COL);
        sprite = new ImageIcon("images/Puffshroom.png");
        shoot(Puff.class, gameProjectiles);
    }

    /**
     * Method for returning the sun cost of the Puffshroom class.
     * @return the cost attribute of the Puffshroom class.
     */

    public static int getCost()
    {
        return COST;
    }

    /**
     * Method for returning regeneration rate or the seed cooldown of the Puffshroom class.
     * @return the regenerateRate attribute of the Puffshroom class.
     */

    public static float getRegenerateRate()
    {
        return REGENERATE_RATE;
    }
}
