package plants;

import java.util.ArrayList;
import javax.swing.*;

public class Peashooter extends DayPlant implements AttackPlant
{
    private static final int COST = 100;
    private static final float REGENERATE_RATE = 7.5f;

    /**
     * Constructor for the Peashooter class (subclass of the DayPlant class that implements AttackPlant).
     * Runs the shoot() method.
     * @param ROW the row of where the Peashooter object would reside in the game
     * @param COL the column of where the Peashooter object would reside in the game
     * @param gameProjectiles the ArrayList of projectiles from the game
     */

    public Peashooter(int ROW, int COL, ArrayList<Projectile> gameProjectiles)
    {
        super(60, 0, 9, 1.5f, ROW, COL);
        sprite = new ImageIcon("images/Peashooter.png");
        shoot(Pea.class, gameProjectiles);
    }

    /**
     * Method for returning the sun cost of the Peashooter class.
     * @return the cost attribute of the Peashooter class.
     */

    public static int getCost()
    {
        return COST;
    }

    /**
     * Method for returning regeneration rate or the seed cooldown of the Peashooter class.
     * @return the regenerateRate attribute of the Peashooter class.
     */

    public static float getRegenerateRate()
    {
        return REGENERATE_RATE;
    }
}