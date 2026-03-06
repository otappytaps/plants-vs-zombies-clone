package game;

import plants.*;

public class PlantTile extends Tile
{
    private Plant plant;

    /**
     * Constructor for Lawn class (a subclass of Tile) with no Plant object.
     */

    public PlantTile(int row, int col) 
    {
        super(row, col);
        plant = null;
    }

    /**
     * Constructor for Lawn class (a subclass of Tile) with a given Plant object.
     * @param plant
     */

    public PlantTile(Plant plant, int row, int col)
    {
        this(row, col);
        this.plant = plant;
    }

    /**
     * Method for returning the Plant that is placed in the Lawn Tile.
     * @return the plant attribute of the Lawn object
     */

    public Plant getPlant()
    {
        return plant;
    }

    /**
     * Method for modifying the Plant that is placed in the Lawn Tile.
     * @param plant the new plant attribute of the Lawn object
     */

    public void setPlant(Plant plant)
    {
        this.plant = plant;
    }

}
