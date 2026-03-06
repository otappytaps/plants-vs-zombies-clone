package game;

import plants.*;
import zombies.*;

import java.util.*;

public class Engine 
{
    private final ArrayList<Plant> plants = new ArrayList<>();
    private final ArrayList<Zombie> zombies = new ArrayList<>();
    private final ArrayList<Projectile> projectiles = new ArrayList<>();

    private boolean gameOver = false;
    private final Tile[][] map = new Tile[Config.MAPSIZE_ROW][Config.MAPSIZE_COL];

    private IntRef time = new IntRef(0);
    private Timer tickTimer = new Timer();
    private TimerTask tickTask = new TimerTask()
    {
        public void run()
        {   
            time.setValue(time.getValue() + 1);
        }
    };

    /**
     * Method for putting a delay between executions of a thread.
     * @param millis The delay in milliseconds
     */

    private static void sleep(int millis) 
    {
        try 
        {
            Thread.sleep(millis);
        } 
        catch (InterruptedException e) 
        {
            e.printStackTrace();
        }
    }

    /**
     * Method for returning the map attribute of the Engine.
     */

    public Tile[][] getMap()
    {
        return map;
    }

    /**
     * Method for returning the plants ArrayList attribute of the Engine.
     */

    public ArrayList<Plant> getPlants()
    {
        return plants;
    }

    /**
     * Method for returning the zombies ArrayList attribute of the Engine.
     */

    public ArrayList<Zombie> getZombies()
    {
        return zombies;
    }

    /**
     * Method for returning the projectiles ArrayList attribute of the Engine.
     */

    public ArrayList<Projectile> getProjectiles()
    {
        return projectiles;
    }

    /**
     * Method for returning the gameOver attribute of the Engine
     */

     public boolean getGameOver()
     {
        return gameOver;
     }

    /**
     * Constructor for the Engine object and runs the Engine according to the map given.
     * @param mapNumber The map number of the map the Engine is going to run
     */

    public Engine(int mapNumber)
    {
        tickTimer.scheduleAtFixedRate(tickTask, 0, 1000);
        switch(mapNumber)
        {
            case 1:
            {
                Maps.generateMap1(map, zombies, time);
                break;
            }

            case 2:
            {
                Maps.generateMap2(map, zombies, time);
                break;
            }

            case 3:
            {
                Maps.generateMap3(map, zombies, time);
                break;
            }

            default:
            {
                throw new RuntimeException(new IllegalArgumentException("Invalid map number."));
            }
        }

        Thread gameThread = new Thread(() ->
        {
            while(!gameOver)
            {
                synchronized(map)
                {
                        Logic.updatePlantTargeting(plants, map);
                        Logic.updatePlants(plants, map);
                        Logic.updateZombies(zombies, map);
                        Logic.updateProjectiles(projectiles, map);
                        gameOver = Logic.isGameOver(map);
                }
                sleep(50);
            }
        });
        gameThread.start();
    }

    /**
     * Method for killing the Timers and TimerTasks of the game itself and all Plant, Zombie and Projectile objects in the game.
     */

    public void killTimer()
    {
        tickTimer.cancel();
        tickTask.cancel();
        tickTimer = null;
        tickTask = null;

        for (Plant p : plants)
        {
            p.killTimer();
        }

        for (Zombie z : zombies)
        {
            z.killTimer();
        }

        for (Projectile p : projectiles)
        {
            p.killTimer();
        }
    }
}
