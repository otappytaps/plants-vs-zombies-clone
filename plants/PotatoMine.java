package plants;

import game.*;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;

public class PotatoMine extends Plant implements AttackPlant
{
    private static final int COST = 25;
    private static final float REGENERATE_RATE = 30.0f;

    private Timer delayTimer;
    private TimerTask delayTask;

    /**
     * Constructor for the PotatoMine class (subclass of the Plant class that implements AttackPlant).
     * Runs the explode() method with a built in delay.
     * @param ROW the row of where the PotatoMine object would reside in the game
     * @param COL the column of where the PotatoMine object would reside in the game
     * @param map the Tile[][] array of the game
     */

    public PotatoMine(int ROW, int COL, Tile[][] map)
    {
        super(60, 630, 0, 0, ROW, COL);
        sprite = new ImageIcon("images/PotatoMineUnready.png");
        delayTimer = new Timer();
        delayTask = new TimerTask()
        {
            public void run()
            {
                sprite = new ImageIcon("images/PotatoMine.png");
                explode(map);
            }
        };
        delayTimer.schedule(delayTask, 15000);
    }

    /**
     * Method for killing the Timers and Timer Tasks used by the PotatoMine object.
     * This includes the delayTimer and delayTask exclusive to the PotatoMine object.
     */

    @Override
    public void killTimer()
    {
        if (shootTimer != null && shootTask != null)
        {
            shootTimer.cancel();
            shootTask.cancel();
            shootTimer = null;
            shootTask = null;
        }

        if (explodeTimer != null && explodeTask != null)
        {
            explodeTimer.cancel();
            explodeTask.cancel();
            explodeTimer = null;
            explodeTask = null;
        }
        
        if (delayTimer != null && delayTask != null)
        {
            delayTimer.cancel();
            delayTask.cancel();
            delayTimer = null;
            delayTask = null;
        }
    }

    /**
     * Method for returning the sun cost of the PotatoMine class.
     * @return the cost attribute of the PotatoMine class.
     */

    public static int getCost()
    {
        return COST;
    }

    /**
     * Method for returning regeneration rate or the seed cooldown of the PotatoMine class.
     * @return the regenerateRate attribute of the PotatoMine class.
     */

    public static float getRegenerateRate()
    {
        return REGENERATE_RATE;
    }
}
