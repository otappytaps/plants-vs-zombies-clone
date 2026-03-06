package plants;

public abstract class NightPlant extends Plant
{
    /**
     * Constructor for the NightPlant class (subclass of the Plant class).
     * NightPlant class is just for sorting.
     * @param HEALTH the health points a NightPlant object has
     * @param DEATH_DAMAGE the damage the NightPlant object does when it dies
     * @param RANGE the range where a NightPlant object can attack measured in the number of Tiles
     * @param ATTACK_SPEED the interval that the NightPlant object attacks measured in seconds
     * @param ROW the current row in the Tile map of a NightPlant object
     * @param COL the current column in the Tile map of a NightPlant object
     */

    public NightPlant(int HEALTH, int DEATH_DAMAGE, int RANGE, float ATTACK_SPEED, int ROW, int COL)
    {
        super(HEALTH, DEATH_DAMAGE, RANGE, ATTACK_SPEED, ROW, COL);
    }
}
