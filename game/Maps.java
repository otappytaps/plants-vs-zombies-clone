package game;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

import zombies.*;

public class Maps 
{
    private static Timer spawnTimer;
    private static TimerTask spawnTask;

    /**
     * Method for spawning a BaseZombie in the game.
     * @param zombies the zombies ArrayList of the game
     */

    private static void spawnBaseZombie(ArrayList<Zombie> zombies) 
    {
        synchronized(zombies)
        {
            int lane = (int) (Math.random() * Config.MAPSIZE_ROW);
            int col = Config.ZOMBIESPAWN_COL;
            BaseZombie z = new BaseZombie(lane, col);
            zombies.add(z);
        }
    }

    /**
     * Method for spawning a ConeHeadZombie in the game.
     * @param zombies the zombies ArrayList of the game
     */

    private static void spawnConeHeadZombie(ArrayList<Zombie> zombies) 
    {
        synchronized(zombies)
        {
            int lane = (int) (Math.random() * Config.MAPSIZE_ROW);
            int col = Config.ZOMBIESPAWN_COL;
            ConeHeadZombie z = new ConeHeadZombie(lane, col);
            zombies.add(z);
        }
    }

    /**
     * Method for spawning a BucketHeadZombie in the game.
     * @param zombies the zombies ArrayList of the game
     */

    private static void spawnBucketHeadZombie(ArrayList<Zombie> zombies) 
    {
        synchronized(zombies)
        {
            int lane = (int) (Math.random() * Config.MAPSIZE_ROW);
            int col = Config.ZOMBIESPAWN_COL;
            BucketHeadZombie z = new BucketHeadZombie(lane, col);
            zombies.add(z);
        }
    }

    /**
     * Method for spawning a FlagZombie in the game.
     * @param zombies the zombies ArrayList of the game
     */

    private static void spawnFlagZombie(ArrayList<Zombie> zombies) 
    {
        synchronized(zombies)
        {
            int lane = (int) (Math.random() * Config.MAPSIZE_ROW);
            int col = Config.ZOMBIESPAWN_COL;
            FlagZombie z = new FlagZombie(lane, col);
            zombies.add(z);
        }
    }

    /**
     * Method for initializing Map1 of the game.
     * @param map the Tile[][] array of the game
     */

    public static void generateMap1(Tile[][] map, ArrayList<Zombie> zombies, IntRef time)
    {
        // initializes lawn
        for (int i = 0; i < Config.MAPSIZE_ROW; i++) 
        {
            for (int j = 1; j <= Config.MAPSIZE_COL - Config.ZOMBIESPAWN_WIDTH; j++) 
            {
                map[i][j] = new PlantTile(i, j);
            }
        }

        // initializes tiles that are outside the map where Zombies spawn
        for (int i = 0; i < Config.MAPSIZE_ROW; i++) 
        {
            for (int j = Config.MAPSIZE_COL - Config.ZOMBIESPAWN_WIDTH +1; j < Config.MAPSIZE_COL; j++) 
            {
                map[i][j] = new Tile(i, j);
            }
        }

        // initializes the game over tiles
        for (int i = 0; i < Config.MAPSIZE_ROW; i++) 
        {
            map[i][0] = new Tile(i, 0);
        }

        // start zombie generation
        spawnTimer = new Timer();
        spawnTask = new TimerTask()
        {
            public void run()
            {
                int currentTime = time.getValue();
                if (currentTime > 180) 
                {
                    this.cancel();
                    spawnTimer.cancel();
                    spawnTimer = null;
                    spawnTask = null;
                    return;
                }
                synchronized (zombies) 
                {
                    if (currentTime >= 30 && currentTime <= 80 && currentTime % 10 == 0) 
                    {
                        spawnBaseZombie(zombies);
                    } 
                    else if (currentTime >= 81 && currentTime <= 140 && currentTime % 5 == 0) 
                    {
                        Random random = new Random();
                        if (random.nextBoolean())
                            spawnBaseZombie(zombies);
                        else
                            spawnConeHeadZombie(zombies);
                    } 
                    else if (currentTime >= 141 && currentTime <= 170 && currentTime % 3 == 0) 
                    {
                        Random random = new Random();
                        int randomNumber = random.nextInt(3);

                        switch (randomNumber)
                        {
                            case 0:
                            {
                                spawnBucketHeadZombie(zombies);
                                break;
                            }
                            case 1:
                            {
                                spawnConeHeadZombie(zombies);
                                break;
                            }

                            case 2:
                            {
                                spawnBaseZombie(zombies);
                                break;
                            }
                        }
                        spawnBaseZombie(zombies);
                    }
                    else if (currentTime == 171) // wave
                    {
                        spawnFlagZombie(zombies);
                        spawnBucketHeadZombie(zombies);
                        spawnBucketHeadZombie(zombies);

                        spawnConeHeadZombie(zombies);
                        spawnConeHeadZombie(zombies);
                        spawnConeHeadZombie(zombies);
                        spawnConeHeadZombie(zombies);

                        spawnBaseZombie(zombies);
                        spawnBaseZombie(zombies);
                        spawnBaseZombie(zombies);
                        spawnBaseZombie(zombies);
                        spawnBaseZombie(zombies);
                    }
                    else if (currentTime > 171 && currentTime % 3 == 0) 
                    {
                        Random random = new Random();
                        if (random.nextBoolean())
                            spawnBucketHeadZombie(zombies);
                        else
                            spawnConeHeadZombie(zombies);
                        spawnBaseZombie(zombies);
                        spawnBaseZombie(zombies);
                    }
                }
            }
        };

        spawnTimer.scheduleAtFixedRate(spawnTask, 0, 1000);
    }

    /**
     * Method for initializing Map2 of the game.
     * @param map the Tile[][] array of the game
     */

    public static void generateMap2(Tile[][] map, ArrayList<Zombie> zombies, IntRef time)
    {
        // initializes lawn
        for (int i = 0; i < Config.MAPSIZE_ROW; i++) 
        {
            for (int j = 1; j <= Config.MAPSIZE_COL - Config.ZOMBIESPAWN_WIDTH; j++) 
            {
                map[i][j] = new PlantTile(i, j);
            }
        }

        // initializes tiles that are outside the map where Zombies spawn
        for (int i = 0; i < Config.MAPSIZE_ROW; i++) 
        {
            for (int j = Config.MAPSIZE_COL - Config.ZOMBIESPAWN_WIDTH + 1; j < Config.MAPSIZE_COL; j++) 
            {
                map[i][j] = new Tile(i, j);
            }
        }

        // initializes the game over tiles
        for (int i = 0; i < Config.MAPSIZE_ROW; i++) 
        {
            map[i][0] = new Tile(i, 0);
        }

        // start zombie generation
        spawnTimer = new Timer();
        spawnTask = new TimerTask()
        {
            public void run()
            {
                int currentTime = time.getValue();
                if (currentTime > 180) 
                {
                    this.cancel();
                    spawnTimer.cancel();
                    spawnTimer = null;
                    spawnTask = null;
                    return;
                }
                synchronized (zombies) 
                {
                    if (currentTime >= 30 && currentTime <= 80 && currentTime % 10 == 0) 
                    {
                        spawnConeHeadZombie(zombies);
                    } 
                    else if (currentTime >= 81 && currentTime <= 140 && currentTime % 5 == 0) 
                    {
                        Random random = new Random();
                        if (random.nextBoolean())
                            spawnBucketHeadZombie(zombies);
                        else
                            spawnConeHeadZombie(zombies);
                        spawnBaseZombie(zombies);
                        spawnBaseZombie(zombies);
                        spawnBaseZombie(zombies);
                    } 
                    else if (currentTime >= 141 && currentTime <= 170 && currentTime % 3 == 0) 
                    {
                        Random random = new Random();
                        int randomNumber = random.nextInt(3);

                        switch (randomNumber)
                        {
                            case 0:
                            {
                                spawnBucketHeadZombie(zombies);
                                break;
                            }
                            case 1:
                            {
                                spawnConeHeadZombie(zombies);
                                break;
                            }

                            case 2:
                            {
                                spawnBaseZombie(zombies);
                                break;
                            }
                        }
                        spawnConeHeadZombie(zombies);
                        spawnConeHeadZombie(zombies);
                        spawnConeHeadZombie(zombies);
                        spawnBucketHeadZombie(zombies);
                    }
                    else if (currentTime == 171) // wave
                    {
                        spawnFlagZombie(zombies);
                        spawnBucketHeadZombie(zombies);
                        spawnBucketHeadZombie(zombies);
                        spawnBucketHeadZombie(zombies);
                        
                        spawnConeHeadZombie(zombies);
                        spawnConeHeadZombie(zombies);
                        spawnConeHeadZombie(zombies);

                        spawnBaseZombie(zombies);
                        spawnBaseZombie(zombies);
                        spawnBaseZombie(zombies);
                        spawnBaseZombie(zombies);
                        spawnBaseZombie(zombies);
                    }
                    else if (currentTime > 171 && currentTime % 3 == 0) 
                    {
                        spawnBucketHeadZombie(zombies);
                        spawnConeHeadZombie(zombies);
                    }
                }
            }
        };
        spawnTimer.scheduleAtFixedRate(spawnTask, 0, 1000);
    }

    /**
     * Method for initializing Map3 of the game.
     * @param map the Tile[][] array of the game
     */


    public static void generateMap3(Tile[][] map, ArrayList<Zombie> zombies, IntRef time)
    {
        // initializes lawn
        for (int i = 0; i < Config.MAPSIZE_ROW; i++) 
        {
            for (int j = 1; j <= Config.MAPSIZE_COL - Config.ZOMBIESPAWN_WIDTH; j++) 
            {
                map[i][j] = new PlantTile(i, j);
            }
        }

        // initializes tiles that are outside the map where Zombies spawn
        for (int i = 0; i < Config.MAPSIZE_ROW; i++) 
        {
            for (int j = Config.MAPSIZE_COL - Config.ZOMBIESPAWN_WIDTH + 1; j < Config.MAPSIZE_COL; j++) 
            {
                map[i][j] = new Tile(i, j);
            }
        }

        // initializes the game over tiles
        for (int i = 0; i < Config.MAPSIZE_ROW; i++) 
        {
            map[i][0] = new Tile(i, 0);
        }

        // start zombie generation
        spawnTimer = new Timer();
        spawnTask = new TimerTask()
        {
            public void run()
            {
                int currentTime = time.getValue();
                if (currentTime > 180) 
                {
                    this.cancel();
                    spawnTimer.cancel();
                    spawnTimer = null;
                    spawnTask = null;
                    return;
                }
                synchronized (zombies) 
                {
                    if (currentTime >= 30 && currentTime <= 80 && currentTime % 10 == 0) 
                    {
                        Random random = new Random();
                        if (random.nextBoolean())
                            spawnBaseZombie(zombies);
                        else
                            spawnConeHeadZombie(zombies);
                        spawnBaseZombie(zombies);
                    } 
                    else if (currentTime >= 81 && currentTime <= 140 && currentTime % 5 == 0) 
                    {
                        Random random = new Random();
                        if (random.nextBoolean())
                            spawnBaseZombie(zombies);
                        else
                            spawnBucketHeadZombie(zombies);
                        spawnBucketHeadZombie(zombies);
                        spawnConeHeadZombie(zombies);
                    } 
                    else if (currentTime >= 141 && currentTime <= 170 && currentTime % 3 == 0) 
                    {
                        spawnBucketHeadZombie(zombies);
                    }
                    else if (currentTime == 171) // wave
                    {
                        spawnFlagZombie(zombies);
                        spawnBucketHeadZombie(zombies);

                        spawnConeHeadZombie(zombies);
                        spawnConeHeadZombie(zombies);
                        spawnConeHeadZombie(zombies);

                        spawnBaseZombie(zombies);
                        spawnBaseZombie(zombies);
                        spawnBaseZombie(zombies);
                        spawnBaseZombie(zombies);
                    }
                    else if (currentTime > 171 && currentTime % 3 == 0) 
                    {
                        spawnBucketHeadZombie(zombies);
                        spawnBucketHeadZombie(zombies);
                    }
                }
            }
        };
        
        spawnTimer.scheduleAtFixedRate(spawnTask, 0, 1000);
    }
}
