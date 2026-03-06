package plants;

import game.*;
import zombies.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javax.swing.*;
import java.awt.Image;

public abstract class Plant
{
    protected ImageIcon sprite;
    private int health;
    private final int DEATH_DAMAGE;
    private final int RANGE;

    private final float ATTACK_SPEED;

    private final int ROW;
    private final int COL;

    private boolean sleep = false;
    private boolean zombieInRange = false;

    protected Timer shootTimer;
    protected TimerTask shootTask;

    protected Timer explodeTimer;
    protected TimerTask explodeTask;

    /**
     * Constructor for the Plant class used by the Plant subclasses.
     * @param health the health points a Plant object has
     * @param DEATH_DAMAGE the damage the Plant object does when it dies
     * @param RANGE the range where a Plant object can attack measured in the number of Tiles
     * @param ATTACK_SPEED the interval that the Plant object attacks measured in seconds
     * @param ROW the current row in the Tile map of a Plant object
     * @param COL the current column in the Tile map of a Plant object
     */

    protected Plant(int health, int DEATH_DAMAGE, int RANGE, float ATTACK_SPEED, int ROW, int COL)
    {
        this.health = health;
        this.DEATH_DAMAGE = DEATH_DAMAGE;
        this.RANGE = RANGE;
        this.ATTACK_SPEED = ATTACK_SPEED;
        this.ROW = ROW;
        this.COL = COL;
    }

    /**
     * Method for returning the isprite Image of the Plant object.
     * @return the sprite attribute of the Plant object
     */

    public Image getSprite()
    {
        return sprite.getImage();
    }

    /**
     * Method for returning the health of the Plant object.
     * @return the health attribute of the Plant object
     */

    public int getHealth()
    {
        return health;
    }

    /**
     * Method for returning the death damage of the Plant object.
     * @return the DEATH_DAMAGE attribute fo the Plant object
     */

    public int getDeathDamage()
    {
        return DEATH_DAMAGE;
    }

    /**
     * Method for returning the range to which the Plant object can shoot measured in the number of Tiles.
     * @return the RANGE attribute fo the Plant object
     */

    public int getRange()
    {
        return RANGE;
    }

    /**
     * Method for returning the attack speed of the Plant object.
     * @return the ATTACK_SPEED attribute of the Plant object
     */

    public float getAttackSpeed()
    {
        return ATTACK_SPEED;
    }

    /**
     * Method for returning the row which the Plant object resides in the game.
     * @return the ROW attribute of the Plant object
     */

    public int getRow()
    {
        return ROW;
    }

    /**
     * Method for returning the column which the Plant object resides in the game.
     * @return the COL attribute of the Plant object
     */

    public int getCol()
    {
        return COL;
    }

    /**
     * Method for returning the sleep status of the Plant object.
     * @return the sleep attribute of Plant object
     */

    public boolean getSleep()
    {
        return sleep;
    }

    /**
     * Method for returning the status indicating if there is a zombie in range of the Plant object
     * @return the zombieInRange attribute of the Plant object
     */

    public boolean getZombieInRange()
    {
        return zombieInRange;
    }

    /**
     * Method for modifying the sleep attribute of the Plant object.
     * It is mainly used for making night plants sleep in day maps.
     * @param sleep the new value for the sleep attribute of the Plant object
     */

    public void setSleep(boolean sleep)
    {
        this.sleep = sleep;
    }

    /**
     * Method for modifying the flag of the Plant object that tells if there is a zombie in range or not
     * It is being updated by the Logic method searchZombie().
     * @param zombieInRange the new value of the zombieInRange attribute of the Plant object
     */

    public void setZombieInRange(boolean zombieInRange)
    {
        this.zombieInRange = zombieInRange;
    }

    /**
     * Method for allowing the Plant object to generate projectiles at an interval defined by its attack speed.
     * @param projectile the projectile subclass to which the Plant object would generate.
     */

    protected void shoot(Class<? extends Projectile> projectile, ArrayList<Projectile> gameProjectiles)
    {
        try
        {
            Constructor <?extends Projectile> constructor = projectile.getDeclaredConstructor(int.class, int.class, int.class);
            constructor.setAccessible(true);

            shootTimer = new Timer();
            shootTask = new TimerTask() 
            {
                public void run()
                {
                    try
                    {
                        if (zombieInRange == true && sleep == false)
                            synchronized(gameProjectiles) 
                            {
                                gameProjectiles.add(constructor.newInstance(RANGE, ROW, COL));
                            }

                    } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException error) 
                    {
                        throw new RuntimeException("Unable to run generation of projectile");
                    }
                }
            };
            shootTimer.scheduleAtFixedRate(shootTask, 0, (int)(ATTACK_SPEED*1000)); // will run indefinitely
        } catch (NoSuchMethodException error)
        {
            throw new RuntimeException("Constructor not found.");
        }
    }

    /**
     * Method for allowing the Plant object to explode and damage Zombie objects around it in the game.
     * @param map the Tile[][] array of the game
     */

    protected void explode(Tile[][] map)
    {
        explodeTimer = new Timer();
        explodeTask = new TimerTask()
        {
            public void run()
            {
                if (zombieInRange == true)
                {
                    for (int i = ROW - 1; i <= ROW + 1; i++) 
                    {
                        if (i < 0 || i >= Config.MAPSIZE_ROW)
                            continue;

                        for (int j = COL - 1; j <= COL + 1; j++) 
                        {
                            if (j < 0 || j >= Config.MAPSIZE_COL)
                                continue;

                            ArrayList<Zombie> zombies = map[i][j].getZombies();
                            synchronized(zombies) 
                            {
                                Iterator<Zombie> iterator = zombies.iterator();
                                while (iterator.hasNext()) 
                                {
                                    iterator.next().takeDamage(DEATH_DAMAGE);
                                }
                            }
                        }
                    }
                    explodeTimer.cancel();
                    explodeTask.cancel();
                    explodeTimer = null;
                    explodeTask = null;
                    health = 0;
                }
            }
        };
        
        explodeTimer.scheduleAtFixedRate(explodeTask, 0, 50);
    }

    /**
     * Method for allowing the Plant object to take a set amount of damage.
     * @param damage the damage to be taken by the Zombie object
     */

    public void takeDamage(int damage)
    {
        health-=damage;
    }

     /**
     * Method for killing the Timers and Timer Tasks used by the Plant object.
     * Used when the Plant object dies.
     */

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
    }
}