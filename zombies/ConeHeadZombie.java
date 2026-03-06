package zombies;

import javax.swing.ImageIcon;

public class ConeHeadZombie extends Zombie
{
    /**
     * Constructor for the ConeHeadZombie class (subclass of the Zombie class)
     * @param ROW the row where the ConeHeadZombie object would spawn in the game
     * @param COL the column where the ConeHeadZombie object would spawn in the game
     */
    
    public ConeHeadZombie(int ROW, int COL)
    {
        super(4, 203, 10, ROW, COL);
        sprite = new ImageIcon("images/ConeHeadZombie.png");
    }
}
