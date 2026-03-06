package zombies;

import javax.swing.ImageIcon;

public class BucketHeadZombie extends Zombie
{

    /**
     * Constructor for the BucketHeadZombie class (subclass of the Zombie class)
     * @param ROW the row where the BucketHeadZombie object would spawn in the game
     * @param COL the column where the BucketHeadZombie object would spawn in the game
     */
    
    public BucketHeadZombie(int ROW, int COL)
    {
        super(4, 483, 10, ROW, COL);
        sprite = new ImageIcon("images/BucketHeadZombie.png");
    }
}
