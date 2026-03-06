package plants;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.ImageIcon;
import game.*;

public class CherryBomb extends Plant
{
    private static final int COST = 150;
    private static final float REGENERATE_RATE = 50.0f;

    private Timer delayTimer;
    private TimerTask delayTask;


    /**
     * Constructor for the CherryBomb class (subclass of the Plant class).
     * Runs the explode() method with a built in delay.
     * @param ROW the row of where the CherryBomb object would reside in the game
     * @param COL the column of where the CherryBomb object would reside in the game
     * @param map the Tile[][] array of the game
     */

    public CherryBomb(int ROW, int COL, Tile[][] map)
    {
        super(1000, 630, 3, 0, ROW, COL);
        sprite = new ImageIcon("images/CherryBomb.png");
        this.setZombieInRange(true);
        delayTimer = new Timer();
        delayTask = new TimerTask()
        {
            public void run()
            {
                explode(map);
            }
        };
        delayTimer.schedule(delayTask, 500);
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
     * Method for returning the sun cost of the CherryBomb class.
     * @return the cost attribute of the CherryBomb class.
     */

    public static int getCost()
    {
        return COST;
    }

    /**
     * Method for returning regeneration rate or the seed cooldown of the CherryBomb class.
     * @return the regenerateRate attribute of the CherryBomb class.
     */

    public static float getRegenerateRate()
    {
        return REGENERATE_RATE;
    }
}
