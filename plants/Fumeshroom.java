package plants;

import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Fumeshroom extends NightPlant implements AttackPlant
{
    private static final int COST = 75;
    private static final float REGENERATE_RATE = 7.5f;

    /**
     * Constructor for the Fumeshroom class (subclass of the NightPlant class that implements AttackPlant).
     * Runs the shoot() method.
     * @param ROW the row of where the Fumeshroom object would reside in the game
     * @param COL the column of where the Fumeshroom object would reside in the game
     * @param gameProjectiles the ArrayList of projectiles from the game
     */

    public Fumeshroom(int ROW, int COL, ArrayList<Projectile> gameProjectiles)
    {
        super(60, 0, 5, 0.5f, ROW, COL);
        sprite = new ImageIcon("images/Fumeshroom.png");
        shoot(Fume.class, gameProjectiles);
    }

    /**
     * Method for returning the sun cost of the Fumeshroom class.
     * @return the cost attribute of the Fumeshroom class.
     */

    public static int getCost()
    {
        return COST;
    }

    /**
     * Method for returning regeneration rate or the seed cooldown of the Fumeshroom class.
     * @return the regenerateRate attribute of the Fumeshroom class.
     */

    public static float getRegenerateRate()
    {
        return REGENERATE_RATE;
    }
}

