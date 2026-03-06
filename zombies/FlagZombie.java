package zombies;

import javax.swing.ImageIcon;

public class FlagZombie extends Zombie
{
    /**
     * Constructor for the FlagZombie class (subclass of the Zombie class)
     * @param ROW the row where the FlagZombie object would spawn in the game
     * @param COL the column where the FlagZombie object would spawn in the game
     */
    
    public FlagZombie(int ROW, int COL)
    {
        super(3.7f, 70, 10, ROW, COL);
        sprite = new ImageIcon("images/FlagZombie.png");
    }
}
