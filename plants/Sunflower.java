package plants;

import javax.swing.ImageIcon;

public class Sunflower extends DayPlant
{
    private static final int COST = 25;
    private static final float REGENERATE_RATE = 7.5f;

    /**
     * Constructor for the Sunflower class (subclass of the DayPlant class).
     * Runs the shoot() method.
     * @param ROW the row of where the Sunflower object would reside in the game
     * @param COL the column of where the Sunflower object would reside in the game
     * @param gameProjectiles the ArrayList of projectiles from the game
     */

    public Sunflower(int ROW, int COL)
    {
        super(60, 0, -0, 25.0f, ROW, COL);
        sprite = new ImageIcon("images/Sunflower.png");


        // generate sm sun
    }

    /**
     * Method for returning the sun cost of the Sunflower class.
     * @return the cost attribute of the Sunflower class.
     */

    public static int getCost()
    {
        return COST;
    }

    /**
     * Method for returning regeneration rate or the seed cooldown of the Sunflower class.
     * @return the regenerateRate attribute of the Sunflower class.
     */

    public static float getRegenerateRate()
    {
        return REGENERATE_RATE;
    }
}
