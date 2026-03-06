package plants;

public abstract class SlowingProjectile extends Projectile
{
    protected float slowFactor;

    /**
     * Constructor for SlowingProjectile class (subclass of Projectile class).
     * @param DAMAGE the damage that would be dealt when hitting a zombie
     * @param RANGE the range the slowing projectile could travel measured in the number of Tiles
     * @param SPEED the speed by which the slowing projectile could move 1 Tile measured in seconds
     * @param ROW the row the slowing projectile is currently in, in the game
     * @param COL the column the slowing projectile is current in, in the game
     */

    public SlowingProjectile(int DAMAGE, int RANGE, float SPEED, int ROW, int COL)
    {
        super(DAMAGE, RANGE, SPEED, ROW, COL);
    }

    /**
     * Method for returning the slow factor of the SlowingProjectile object.
     * @return the slowFactor attribute of the SlowingProjectile object
     */

    public float getSlowFactor()
    {
        return slowFactor;
    }
    
}
