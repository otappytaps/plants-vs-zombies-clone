package game;

import plants.*;
import zombies.*;

import java.util.ArrayList;

public class Tile 
{
    protected ArrayList<Zombie> zombies;
    protected ArrayList<Projectile> projectiles;
    
    private final int ROW;
    private final int COL;

    /**
     * Constructor for Tile class.
     */

    public Tile(int row, int col)
    {
        projectiles = new ArrayList<>();
        zombies = new ArrayList<>();
        ROW = row;
        COL = col;
    }

    /**
     * Method for returning the Zombie ArrayList of a Tile object.
     * @return the zombies attribute of the Tile object
     */

    public ArrayList<Zombie> getZombies()
    {
        return zombies;
    }

    /**
     * Method for returning the Projectile ArrayList of a Tile object.
     * @return the projectiles attribute of the Tile object
     */

    public ArrayList<Projectile> getProjectiles()
    {
        return projectiles;
    }

    /**
     * Method for returning the row position of a Tile object.
     * @return the ROW attribute of the Tile object
     */

    public int getRow()
    {
        return ROW;
    }

    /**
     * Method for returning the column position of a Tile object.
     * @return the COL object of the Tile object
     */

    public int getCol()
    {
        return COL;
    }

    
}
