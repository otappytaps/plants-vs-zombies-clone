package game;

import plants.*;
import zombies.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class Logic 
{
    // override
    private Logic(){}

    /**
     * Method that checks the Tile map of the game for any zombies that got through which would result in Game Over.
     * @param tiles the Tile array of the game
     * @return true if it is Game Over, false if it is not
     */

    public static boolean isGameOver(Tile[][] map)
    {
        for (int i = 0; i < 5; i++)
        {
            if (!(map[i][Config.GAMEOVER_COL].getZombies().isEmpty()))
                return true;
        }
        return false;
    }

    /**
     * Method that updates Plant objects that implements AttackPlant if there is a Zombie to shoot.
     * @param plants the Plant ArrayList of the game
     * @param tiles the Tile array of the game
     */

    public static void updatePlantTargeting(ArrayList<Plant> plants, Tile[][] map) 
    {
        Plant plant;
        synchronized(plants)
        {
            Iterator<Plant> iterator = plants.iterator();
            while (iterator.hasNext())
            {
                plant = iterator.next();
                if (plant instanceof AttackPlant)
                {
                    // for resetting
                    plant.setZombieInRange(false);
                    for (int i = plant.getCol(); i <= plant.getCol() + plant.getRange(); i++)
                    {
                        if (i == Config.DESPAWN_COL)
                            break;
                        if (map[plant.getRow()][i].zombies.isEmpty() == false)
                        {
                            plant.setZombieInRange(true);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Method that simulates movement and spawning of a Projectile object across the game map.
     * Already integrated as a helper method in the updateProjectiles() method.
     * @param projectiles the Projectile ArrayList of the game
     * @param projectile the Projectile being moved
     * @param tiles the Tile[][] array of the game
     */

    private static void moveProjectile(Projectile projectile, Tile[][] map)
    {
        projectile.moveTimer = new Timer();
        projectile.moveTask = new TimerTask() 
        {
            public void run()
            {
                // check if it is at DESPAWN_COL or beyond the range
                if (projectile.getCurrentCol() > (projectile.getCol() + projectile.getRange()) - 1
                    || projectile.getCurrentCol() == Config.DESPAWN_COL) 
                {
                    projectile.killTimer();
                    return;
                }
                // check if it is inactive
                if (projectile.getActive() == false)
                {
                    projectile.setActive(true);
                    map[projectile.getRow()][projectile.getCurrentCol()].projectiles.add(projectile);
                }
                // actual movement
                if (map[projectile.getRow()][projectile.getCurrentCol()].zombies.isEmpty() && projectile.getCurrentCol() != Config.DESPAWN_COL)
                {
                    synchronized(map[projectile.getRow()][projectile.getCurrentCol() + 1]) 
                    {
                        map[projectile.getRow()][projectile.getCurrentCol() + 1].projectiles.add(projectile);
                    }
                    synchronized(map[projectile.getRow()][projectile.getCurrentCol()]) 
                    {
                        map[projectile.getRow()][projectile.getCurrentCol()].projectiles.remove(projectile);
                    }
                    projectile.setCurrentCol(projectile.getCurrentCol() + 1);
                }
            }
        };
        projectile.moveTimer.scheduleAtFixedRate(projectile.moveTask, 0, (int)(projectile.getSpeed()*1000));
    }

    /**
     * Method that updates all Projectiles to spawn, despawn, move and deal damage to zombies in the game.
     * @param projectiles the Projectile ArrayList of the game
     * @param tiles the Tile[][] array of the game
     */

    public static void updateProjectiles(ArrayList<Projectile> projectiles, Tile[][] map) 
    {
        synchronized(projectiles)
        {
            Iterator<Projectile> iterator = projectiles.iterator();
            Projectile projectile;
            Zombie zombie;

            // if projectile is inactive, initialize movement
            while (iterator.hasNext())
            {
                projectile = iterator.next();
                if (projectile.getActive() == false)
                    moveProjectile(projectile, map);
            }

            iterator = projectiles.iterator();
            // if projectile is in the DESPAWN_COL or is already at max range, despawn projectile
            while (iterator.hasNext())
            {
                projectile = iterator.next();
                if (projectile.getCurrentCol() == Config.DESPAWN_COL)
                {
                    synchronized(map[projectile.getRow()][projectile.getCurrentCol()]) 
                    {
                        map[projectile.getRow()][projectile.getCurrentCol()].projectiles.remove(projectile);
                    }
                }
                else if (projectile.getCurrentCol() > (projectile.getCol() + projectile.getRange()) - 1)
                {
                    synchronized(map[projectile.getRow()][projectile.getCurrentCol()]) 
                    {
                        map[projectile.getRow()][projectile.getCurrentCol()].projectiles.remove(projectile);
                    }
                    iterator.remove();
                }
            }

            iterator = projectiles.iterator();
            // if projectile and zombie is in the same tile, damage the zombie and then despawn the projectile
            while(iterator.hasNext())
            {
                projectile = iterator.next();
                if (!(map[projectile.getRow()][projectile.getCurrentCol()].zombies.isEmpty()))
                {
                        zombie = map[projectile.getRow()][projectile.getCurrentCol()].zombies.get(0);
                        zombie.takeDamage(projectile.getDamage());

                        // slows the zombie if the projectile is a slowing projectile
                        if (projectile instanceof SlowingProjectile)
                        {
                            if (zombie.getIsSlowed() == false)
                            {
                                slowZombie(zombie, map, ((SlowingProjectile)projectile).getSlowFactor());
                            }
                        }
                        iterator.remove();
                        synchronized(map[projectile.getRow()][projectile.getCurrentCol()]) 
                        {
                            map[projectile.getRow()][projectile.getCurrentCol()].projectiles.remove(projectile);
                            projectile.killTimer();
                        }
                }
            }
        }
   
    }

    /**
     * Method used for killing Plant objects.
     * @param plants the Plant ArrayList of the game
     * @param plantTile the PlantTile that the Plant object is currently on
     */

    public static void destroyPlant(PlantTile plantTile) 
    {  

        Plant plant = plantTile.getPlant();
        if (plant != null)
        {
            plant.killTimer();
            plantTile.setPlant(null);
        }
    }

    /**
     * Method used to update Plants by killing them when their health falls 0 or below.
     * @param plants the Plant ArrayList of the game
     * @param map the Tile[][] array of the game
     */

    public static void updatePlants(ArrayList<Plant> plants, Tile[][] map)
    {
        synchronized(plants)
        {
            Iterator<Plant> iterator = plants.iterator();
            while(iterator.hasNext())
            {
                Plant plant = iterator.next();
                if (plant.getHealth() <= 0)
                {
                    destroyPlant((PlantTile)map[plant.getRow()][plant.getCol()]);
                    iterator.remove();
                }
            }
        }
    }

    /**
     * Method that simulates movement and spawning of a Zombie object across the game Tiles
     * @param zombie the Zombie object being moved
     * @param tiles the Tile[][] array of the game
     */

    private static void moveZombie(Zombie zombie, Tile[][] map)
    {
        zombie.moveTimer = new Timer();
        zombie.moveTask = new TimerTask()
        {
            public void run()
            {
                if (zombie.getActive() == false)
                {
                    zombie.setActive(true);
                    map[zombie.getRow()][zombie.getCurrentCol()].zombies.add(zombie);
                }

                if (zombie.getIsEating() == false)
                {
                    if (map[zombie.getRow()][zombie.getCurrentCol()] instanceof PlantTile)
                    {
                        if (((PlantTile)map[zombie.getRow()][zombie.getCurrentCol()]).getPlant()!= null)
                        {
                            zombie.eatPlant(((PlantTile)map[zombie.getRow()][zombie.getCurrentCol()]).getPlant());
                        }
                        else
                        {
                            synchronized(map[zombie.getRow()][zombie.getCurrentCol()]) 
                            {
                                map[zombie.getRow()][zombie.getCurrentCol()].zombies.remove(zombie);
                            }
                            synchronized(map[zombie.getRow()][zombie.getCurrentCol() - 1])
                            {
                                map[zombie.getRow()][zombie.getCurrentCol() - 1].zombies.add(zombie);
                            }
                            zombie.setCurrentCol(zombie.getCurrentCol() - 1);
                        }
                    }
                    else if (zombie.getCurrentCol() != 0) 
                    {
                        synchronized(map[zombie.getRow()][zombie.getCurrentCol()]) 
                        {
                            map[zombie.getRow()][zombie.getCurrentCol()].zombies.remove(zombie);
                        }
                        synchronized(map[zombie.getRow()][zombie.getCurrentCol() - 1])
                        {
                            map[zombie.getRow()][zombie.getCurrentCol() - 1].zombies.add(zombie);
                        }
                        zombie.setCurrentCol(zombie.getCurrentCol() - 1);
                    }
                }
            }
        };
        zombie.moveTimer.scheduleAtFixedRate(zombie.moveTask, 0, (int)(zombie.getSpeed()*1000));
    }

    /**
     * Method for updating all the Zombie objects in the game by moving them and killing them if their health goes 0 or below.
     * @param zombies the Zombie ArrayList of the game
     * @param tiles the Tile[][] array of the game
     */

    public static void updateZombies(ArrayList<Zombie> zombies, Tile[][] map)
    {
        synchronized(zombies)
        {
            Zombie zombie;
            Iterator<Zombie> iterator = zombies.iterator();

            while(iterator.hasNext())
            {
                zombie = iterator.next();
                if (zombie.getActive() == false)
                    moveZombie(zombie, map);
            }

            iterator = zombies.iterator();

            while(iterator.hasNext())
            {
                zombie = iterator.next();
                if (zombie.getHealth() <= 0)
                {
                    zombie.killTimer();
                    synchronized(map[zombie.getRow()][zombie.getCurrentCol()]) 
                    {
                        map[zombie.getRow()][zombie.getCurrentCol()].zombies.remove(zombie);
                    }
                    iterator.remove();
                }
            }
        }
  
    }

    /**
     * Method for resetting the movement of the Zombie object according to the given speed.
     * @param zombie the Zombie object to reset movement
     * @param map the Tile[][] array of the game
     * @param speed the new speed of the Zombie object
     */

    // used for slows
    public static void changeZombieMoveTimer(Zombie zombie, Tile[][] map, float speed)
    {
        synchronized(map)
        {
            if (zombie.moveTimer != null && zombie.moveTask != null)
            {
                zombie.moveTimer.cancel();
                zombie.moveTask.cancel();
                zombie.moveTimer = null;
                zombie.moveTask = null;
            }

            zombie.moveTimer = new Timer();
            zombie.moveTask = new TimerTask() 
            {
                public void run()
                {
                    if (zombie.getActive() == false)
                    {
                        zombie.setActive(true);
                        map[zombie.getRow()][zombie.getCurrentCol()].zombies.add(zombie);
                    }

                    if (zombie.getIsEating() == false)
                    {
                        if (map[zombie.getRow()][zombie.getCurrentCol()] instanceof PlantTile)
                        {
                            if (((PlantTile)map[zombie.getRow()][zombie.getCurrentCol()]).getPlant()!= null)
                            {
                                zombie.eatPlant(((PlantTile)map[zombie.getRow()][zombie.getCurrentCol()]).getPlant());
                            }
                            else
                            {
                                synchronized(map[zombie.getRow()][zombie.getCurrentCol()]) {
                                    map[zombie.getRow()][zombie.getCurrentCol()].zombies.remove(zombie);
                                }
                                synchronized(map[zombie.getRow()][zombie.getCurrentCol() - 1]) 
                                {
                                    map[zombie.getRow()][zombie.getCurrentCol() - 1].zombies.add(zombie);
                                }
                                zombie.setCurrentCol(zombie.getCurrentCol() - 1);
                            }
                        }
                        else if (zombie.getCurrentCol() != 0) 
                        {
                            synchronized(map[zombie.getRow()][zombie.getCurrentCol()]) {
                                map[zombie.getRow()][zombie.getCurrentCol()].zombies.remove(zombie);
                            }
                            synchronized(map[zombie.getRow()][zombie.getCurrentCol() - 1]) 
                            {
                                map[zombie.getRow()][zombie.getCurrentCol() - 1].zombies.add(zombie);
                            }
                            zombie.setCurrentCol(zombie.getCurrentCol() - 1);
                        }
                    }  
                }
            };
            zombie.moveTimer.scheduleAtFixedRate(zombie.moveTask, (int)(speed*1000), (int)(speed*1000));
        }
 
    }

    /**
     * Method for slowing down the movement of a Zombie object.
     * @param zombie the Zombie object to be slowed
     * @param map the Tile[][] array of the game
     * @param slowFactor the multiplier of the slow applied to the Zombie object
     */

    public static void slowZombie(Zombie zombie, Tile[][] map, float slowFactor)
    {
        if (zombie.getIsSlowed() == false)
        {
            zombie.setIsSlowed(true);
            changeZombieMoveTimer(zombie, map, slowFactor*zombie.getSpeed());
            zombie.slowTimer = new Timer();

            // after a certain period of time, unslow the zombie
            zombie.slowTask = new TimerTask()
            {
                public void run()
                {
                    zombie.setIsSlowed(false);
                    changeZombieMoveTimer(zombie, map, zombie.getSpeed());
                    zombie.slowTimer.cancel();
                    zombie.slowTask.cancel();
                    zombie.slowTimer = null;
                    zombie.slowTask = null;
                }
            };
            zombie.slowTimer.schedule(zombie.slowTask, 10000);
        }
    }

    /**
     * Method for placing and removing Plant objects in the game.
     * @param action the name of the Plant to be placed or "shovel" if a Plant object were to be removed
     * @param tile the tile where the Plant object will be placed
     */

    public static void input(String action, PlantTile tile, ArrayList<Plant> gamePlants, ArrayList<Projectile> gameProjectiles, Tile[][] map, int mapNumber)
    {
        if (tile.getPlant() == null)
        {
            if (action.equalsIgnoreCase("sunflower"))
            {
                Sunflower p = new Sunflower(tile.getRow(), tile.getCol());
                tile.setPlant(p);
                synchronized(gamePlants)
                {
                    gamePlants.add(p);
                }
            }
            else if (action.equalsIgnoreCase("peashooter"))
            {
                Peashooter p = new Peashooter(tile.getRow(), tile.getCol(), gameProjectiles);
                tile.setPlant(p);
                synchronized(gamePlants)
                {
                    gamePlants.add(p);
                }
            }
            else if (action.equalsIgnoreCase("snowpeashooter"))
            {
                SnowPeashooter p = new SnowPeashooter(tile.getRow(), tile.getCol(), gameProjectiles);
                tile.setPlant(p);
                synchronized(gamePlants)
                {
                    gamePlants.add(p);
                } 
            }
            else if (action.equalsIgnoreCase("wallnut"))
            {
                Wallnut p = new Wallnut(tile.getRow(), tile.getCol());
                tile.setPlant(p);
                synchronized(gamePlants)
                {
                    gamePlants.add(p);
                }
            }
            else if (action.equalsIgnoreCase("potatomine"))
            {
                PotatoMine p = new PotatoMine(tile.getRow(), tile.getCol(), map);
                tile.setPlant(p);
                synchronized(gamePlants)
                {
                    gamePlants.add(p);
                }
            }
            else if (action.equalsIgnoreCase("cherrybomb"))
            {
                CherryBomb p = new CherryBomb(tile.getRow(), tile.getCol(), map);
                tile.setPlant(p);
                synchronized(gamePlants)
                {
                    gamePlants.add(p);
                }
            }
            else if (action.equalsIgnoreCase("fumeshroom"))
            {
                Fumeshroom p = new Fumeshroom(tile.getRow(), tile.getCol(), gameProjectiles);
                if (mapNumber == 1 || mapNumber == 3)
                    p.setSleep(true);
                tile.setPlant(p);
                synchronized(gamePlants)
                {
                    gamePlants.add(p);
                }
            }
            else if (action.equalsIgnoreCase("puffshroom"))
            {
                Puffshroom p = new Puffshroom(tile.getRow(), tile.getCol(), gameProjectiles);
                if (mapNumber == 1 || mapNumber == 3)
                    p.setSleep(true);
                tile.setPlant(p);
                synchronized(gamePlants)
                {
                    gamePlants.add(p);
                }
            }
        }
        else
        {
            if (action.equalsIgnoreCase("shovel"))
            {
                synchronized(gamePlants)
                {
                    gamePlants.remove(tile.getPlant());
                }
                destroyPlant(tile);
            }
        }
    }
}
