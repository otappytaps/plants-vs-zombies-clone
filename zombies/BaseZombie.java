package zombies;

import javax.swing.ImageIcon;

public class BaseZombie extends Zombie
{
    /**
     * Constructor for the BaseZombie class (subclass of the Zombie class)
     * @param ROW the row where the Basezombie object would spawn in the game
     * @param COL the column where the BaseZombie object would spawn in the game
     */
    
    public BaseZombie(int ROW, int COL)
    {
        super(4, 70, 10, ROW, COL);
        sprite = new ImageIcon("images/BaseZombie.png");
    }
}
