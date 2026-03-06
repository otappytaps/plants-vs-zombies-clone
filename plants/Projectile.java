package plants;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.Image;

public abstract class Projectile 
{
    protected ImageIcon sprite;
    private final int DAMAGE;
    private final int RANGE;

    private final int ROW;
    private final int COL;

    private int currentCol;

    private final float SPEED;
    private boolean active = false;

    public Timer moveTimer;
    public TimerTask moveTask;

    /**
     * Constructor for Projectile class used by Projectile subclasses.
     * @param DAMAGE the damage that would be dealt when hitting a zombie
     * @param RANGE the range the projectile could travel measured in the number of Tiles
     * @param SPEED the speed by which the projectile could move 1 Tile measured in seconds
     * @param ROW the row the projectile is currently in, in the game
     * @param COL the column the projectile is current in, in the game
     */

    protected Projectile(int DAMAGE, int RANGE, float SPEED, int ROW, int COL)
    {
        this.DAMAGE = DAMAGE;
        this.RANGE = RANGE;
        this.SPEED = SPEED;
        this.ROW = ROW;
        this.COL = COL;

        currentCol = COL;
    }

     /**
     * Method for returning the damage of the Projectile object.
     * @return the DAMAGE attribute of the Projectile object
     */

    public int getDamage()
    {
        return DAMAGE;
    }

    /**
     * Method for returning the range the Projectile object could travel measured in the number of Tiles.
     * @return the RANGE attribute of the Projectile object
     */

    public int getRange()
    {
        return RANGE;
    }

    /**
     * Method for returning the speed by which the Projectile object could move 1 Tile measured in seconds.
     * @return the SPEED attribute of the Projectile object
     */

    public float getSpeed()
    {
        return SPEED;
    }

    /**
     * Method for returning the active status of the Projectile object, that is if it is moving or not.
     * @return the active attribute of the Projectile object
     */

    public boolean getActive()
    {
        return active;
    }

    /**
     * Method for returning the row where the Projectile object is on in game.
     * @return the rowTile attribute of the Projectile object
     */

    public int getRow()
    {
        return ROW;
    }

    /**
     * Method for returning the column where the Projectile object came from in game.
     * @return the colTile attribute of the Projectile object
     */

    public int getCol()
    {
        return COL;
    }

    /**
     * Method for returning the column where the Projectile object is currently on in game.
     * @return the currentColTile attribute of the Projectile object
     */

    public int getCurrentCol()
    {
        return currentCol;
    }

    /**
     * Method for returning the sprite Image of the Projectile object.
     * @return the sprite attribute of the Projectile object
     */

    public Image getSprite()
    {
        return sprite.getImage();
    }

    /**
     * Method for modifying the active status of the Projectile object.
     * Mainly used to update all projectiles in the game.
     * @param active the new value for the active attribute of the Projectile object
     */

    public void setActive(boolean active) 
    {
        this.active = active;
    }

    /**
     * Method for modifying the current column of the Projectile object in game.
     * Mainly used to simulate movement across the game map.
     * @param currentCol the new value for the currentColTile attribute of the Projectile object
     */

    public void setCurrentCol(int currentCol)
    {
        this.currentCol = currentCol;
    }

    /**
     * Method for killing the Timer and TimerTask used by the Projectile object.
     * Used when the Projectile object despawns or if it hits a Zombie.
     */

    public void killTimer()
    {
        if (moveTimer != null && moveTask != null)
        {
            moveTimer.cancel();
            moveTimer = null;
            moveTask.cancel();
            moveTask = null;
        }
    }

}
