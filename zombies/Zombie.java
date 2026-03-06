package zombies;

import plants.*;

import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
import java.awt.Image;

public abstract class Zombie 
{
    protected ImageIcon sprite;
    private float speed;
    private int health;
    private final int DAMAGE;

    private boolean active = false;
    private final int ROW;

    private int currentCol;

    public Timer moveTimer;
    public TimerTask moveTask;

    public Timer slowTimer;
    public TimerTask slowTask;

    private Timer eatTimer;
    private TimerTask eatTask;
    private boolean isEating = false;

    private boolean isSlowed = false;

    /**
     * Constructor for the Zombie class.
     * @param speed the speed of the Zombie object measured in seconds
     * @param health the health of the Zombie object
     * @param DAMAGE the damage the Zombie object deals
     * @param ROW the current row where the Zombie object is currently in, in game
     * @param currentCol the current column where the Zombie object is currently in, in game
     */

     public Zombie(float speed, int health, int DAMAGE, int ROW, int currentCol)
     {
        this.speed = speed;
        this.health = health;
        this.DAMAGE = DAMAGE;
        this.ROW = ROW;
        this.currentCol = currentCol;
     }

    /**
     * Method for returning the sprite Image of the Zombie object.
     * @return the sprite attribute of the Zombie object
     */

    public Image getSprite()
    {
        return sprite.getImage();
    }

    /**
     * Method for allowing the Zombie object to take a set amount of damage.
     * @param damage the damage to be taken by the Zombie object
     */

     public void takeDamage(int damage)
     {
        health-=damage;
     }


    /**
     * Method for returning the Zombie object's health.
     * @return the health attribute of the Zombie object
     */
    
    public int getHealth()
    {
        return health;
    }

    /**
     * Method for returning the Zombie object's speed.
     * @return the speed attribute of the Zombie object
     */

    public float getSpeed()
    {
        return speed;
    }

    /**
     * Method for returning the Zombie object's damage.
     * @return the DAMAGE attribute of the Zombie object
     */

    public int getDamage()
    {
        return DAMAGE;
    }

    /**
     * Method for returning the Zombie object's active status (whether it is moving or not).
     * @return the active attribute of the Zombie object
     */

    public boolean getActive()
    {
        return active;
    }

    /**
     * Method for returning the Zombie object's row in the game.
     * @return the ROW attribute of the Zombie object
     */

    public int getRow()
    {
        return ROW;
    }

    /**
     * Method for returning the Zombie object's current column in the game.
     * @return the currentCol attribute of the Zombie object
     */

    public int getCurrentCol()
    {
        return currentCol;
    }

    /**
     * Method for returning the Zombie object's flag if it is eating or not.
     * @return the isEating attribute of the Zombie object
     */

    public boolean getIsEating()
    {
        return isEating;
    }

    /**
     * Method for returning the Zombie object's flag if it is slowed or not.
     * @return the isSlowed attribute of the Zombie object
     */

    public boolean getIsSlowed()
    {
        return isSlowed;
    }

    /**
     * Method for modifying the flag that indicates whether the Zombie object is moving or not.
     * @param active the new value for the active attribute of the Zombie object
     */

    public void setActive(boolean active)
    {
        this.active = active;
    }

    /**
     * Method for modifying the speed of the Zombie object.
     * @param speed the new value for the Speed attribute of the Zombie object
     */

    public void setSpeed(float speed){

        this.speed = speed;
    }

    /**
     * Method for modifying and updating the current column to where the Zombie is in the game.
     * @param currentCol the new value for the currentColTile attribute of the Zombie object
     */

    public void setCurrentCol(int currentCol)
    {
        this.currentCol = currentCol;
    }

    /**
     * Method for modifying the Zombie object's flag if it is eating or not.
     * @param isEating the new value for the isEating attribute of the Zombie object
     */

    public void setIsEating(boolean isEating)
    {
        this.isEating = isEating;
    }

    /**
     * Method for modifying the Zombie object's flag if it is slowed or not.
     * @param isSlowed the new value for the isSlowed attribute of the Zombie object
     */

    public void setIsSlowed(boolean isSlowed)
    {
        this.isSlowed = isSlowed;
    }

    /**
     * Method for cancelling the Timer and TimerTask instantiated by the Zombie object.
     */

    public void killTimer()
    {
        if (moveTimer != null && moveTask != null)
        {
            moveTimer.cancel();
            moveTask.cancel();
            moveTimer = null;
            moveTask = null;
        }

        if (eatTimer != null && eatTask != null)
        {
            eatTimer.cancel();
            eatTask.cancel();
            eatTimer = null;
            eatTask = null;
        }

        if (slowTimer != null && slowTask != null)
        {
            slowTimer.cancel();
            slowTask.cancel();
            slowTimer = null;
            slowTask = null;
        }
    }

    /**
     * Method that allows the Zombie object to eat the plant in the same Tile it is in.
     * @param plant the Plant object to be eaten by the Zombie object
     */

    public void eatPlant(Plant plant)
    {
        isEating = true;
        eatTimer = new Timer();
        eatTask = new TimerTask()
        {
            public void run()
            {
                if (plant != null && plant.getHealth() > 0)
                {
                    plant.takeDamage(DAMAGE);
                }
                else
                {
                    isEating = false;
                    eatTimer.cancel();
                    eatTask.cancel();
                    eatTimer = null;
                    eatTask = null;
                    return;
                }
            }
        };
        eatTimer.scheduleAtFixedRate(eatTask, 0, 1000);
    }
}
