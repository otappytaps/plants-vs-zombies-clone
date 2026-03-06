package plants;

import java.util.ArrayList;
import javax.swing.*;

public class SnowPeashooter extends DayPlant implements AttackPlant
{
    private static final int COST = 125;
    private static final float REGENERATE_RATE = 7.5f;

    /**
     * Constructor for the SnowPeashooter class (subclass of the DayPlant class that implements AttackPlant).
     * Runs the shoot() method.
     * @param ROW the row of where the SnowPeashooter object would reside in the game
     * @param COL the column of where the SnowPeashooter object would reside in the game
     * @param gameProjectiles the ArrayList of projectiles from the game
     */

    public SnowPeashooter(int ROW, int COL, ArrayList<Projectile> gameProjectiles)
    {
        super(60, 0, 9, 1.5f, ROW, COL);
        sprite = new ImageIcon("images/SnowPeashooter.png");
        shoot(SnowPea.class, gameProjectiles);
    }

    /**
     * Method for returning the sun cost of the SnowPeashooter class.
     * @return the cost attribute of the SnowPeashooter class.
     */

    public static int getCost()
    {
        return COST;
    }

    /**
     * Method for returning regeneration rate or the seed cooldown of the SnowPeashooter class.
     * @return the regenerateRate attribute of the SnowPeashooter class.
     */

    public static float getRegenerateRate()
    {
        return REGENERATE_RATE;
    }
}
