package plants;

public abstract class DayPlant extends Plant
{
    /**
     * Constructor for the DayPlant class (subclass of the Plant class).
     * DayPlant class is just for sorting.
     * @param HEALTH the health points a DayPlant object has
     * @param DEATH_DAMAGE the damage the DayPlant object does when it dies
     * @param RANGE the range where a DayPlant object can attack measured in the number of Tiles
     * @param ATTACK_SPEED the interval that the DayPlant object attacks measured in seconds
     * @param ROW the current row in the Tile map of a DayPlant object
     * @param COL the current column in the Tile map of a DayPlant object
     */

    public DayPlant(int HEALTH, int DEATH_DAMAGE, int RANGE, float ATTACK_SPEED, int ROW, int COL)
    {
        super(HEALTH, DEATH_DAMAGE, RANGE, ATTACK_SPEED, ROW, COL);
    }
}
