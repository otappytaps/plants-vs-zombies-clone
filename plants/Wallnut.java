package plants;

import javax.swing.ImageIcon;

public class Wallnut extends DayPlant
{
    private static final int COST = 50;
    private static final float REGENERATE_RATE = 30.0f;


    /**
     * Constructor for the Wallnut class (subclass of the DayPlant class).
     * Runs the shoot() method.
     * @param ROW the row of where the Wallnut object would reside in the game
     * @param COL the column of where the Wallnut object would reside in the game
     */

    public Wallnut(int ROW, int COL)
    {
        super(800, 0, 0, 0, ROW, COL);
        sprite = new ImageIcon("images/Wallnut.png");
        // generate sm sun
    }

    /**
     * Method for returning the sun cost of the Wallnut class.
     * @return the cost attribute of the Wallnut class.
     */

    public static int getCost()
    {
        return COST;
    }

    /**
     * Method for returning regeneration rate or the seed cooldown of the Wallnut class.
     * @return the regenerateRate attribute of the Wallnut class.
     */

    public static float getRegenerateRate()
    {
        return REGENERATE_RATE;
    }
}
