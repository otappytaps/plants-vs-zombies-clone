package game;

import plants.*;
import zombies.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.Timer;
import java.util.TimerTask;

public class TilePanel extends JPanel
{
    private final Tile TILE;

    private int sunCount;
    private boolean sunflowerPresent;
    private boolean timerCreated = false;

    private Timer sunflowerTimer;
    private TimerTask sunflowerTask;

    private Timer sunflowerUpdateTimer;
    private TimerTask sunflowerUpdateTask;

    /**
     * Constructor for the TilePanel object for the GUI.
     * Also initializes the mouse listener for placing or removing Plant objects 
     * and Timer and TimerTask for the sun generation of sunflowers and updating whether the tile has a sunflower.
     * @param tile The tile represented by the TilePanel object
     */

    public TilePanel(Tile tile) 
    {
        TILE = tile;
        setPreferredSize(new Dimension(55, 55)); 
        setOpaque(false);
        
        if (TILE instanceof PlantTile)
        {
            addMouseListener(new MouseAdapter()
            {
                @Override
                public void mouseClicked(MouseEvent e)
                {
                    if (Game.selectedAction != null)
                    {
                        if (Game.selectedAction.equalsIgnoreCase("shovel"))
                        {
                            Logic.input(Game.selectedAction, (PlantTile)TILE, Game.gameEngine.getPlants(), Game.gameEngine.getProjectiles(), Game.gameEngine.getMap(), Game.mapNumber);
                            Game.selectedAction = null;
                        }
                        else
                        {
                            if (((PlantTile)TILE).getPlant() == null)
                            {
                                Logic.input(Game.selectedAction, (PlantTile)TILE, Game.gameEngine.getPlants(), Game.gameEngine.getProjectiles(), Game.gameEngine.getMap(), Game.mapNumber);
                                Game.selectedAction = null;
                            }
                        }
                    }

                    if (sunCount > 0)
                    {
                        Game.sunCount += sunCount;
                        sunCount = 0;
                    }
                    
                }
            });

            // sunflower check
            sunflowerUpdateTimer = new Timer();
            sunflowerUpdateTask = new TimerTask() 
            {
                public void run()
                {
                    if (((PlantTile)TILE).getPlant() instanceof Sunflower)
                    {
                        if (sunflowerPresent && timerCreated == false)
                        {
                            timerCreated = true;
                            sunflowerTimer = new Timer();
                            sunflowerTask = new TimerTask()
                            {
                                public void run()
                                {
                                    sunCount += 50;
                                }
                            };
                            sunflowerTimer.scheduleAtFixedRate(sunflowerTask, 7000, 24000);
                        }
                        else
                        {
                            sunflowerPresent = true;
                        }
                    }
                    else
                    {
                        sunflowerPresent = false;
                        // timer reset
                        if (sunflowerTimer != null && sunflowerTask != null)
                        {
                            sunflowerTimer.cancel();
                            sunflowerTask.cancel();
                            sunflowerTimer = null;
                            sunflowerTask = null;
                            timerCreated = false;
                        }
                    }
                }
            };
            sunflowerUpdateTimer.scheduleAtFixedRate(sunflowerUpdateTask, 0, 50);
        }
    }

    /**
     * paintComponent is overriden to also draw Plant, Projectile and Zombie graphics
     */

    @Override
    protected void paintComponent(Graphics graphics) 
    {
        super.paintComponent(graphics);

        if (TILE instanceof PlantTile) 
        {
            if (((PlantTile)TILE).getPlant() != null)
            {
                graphics.drawImage(((PlantTile)TILE).getPlant().getSprite(), 5, 8, 40, 40, this);
            }
                
        }
        synchronized(TILE)
        {
            int offset = 0;
            for (Zombie z : TILE.getZombies()) 
            {
                graphics.drawImage(z.getSprite(), 5 + offset, 8, 50, 50, this); 
                offset += 5;
            }
            
            offset = 0;
            for (Projectile p : TILE.getProjectiles())
            {
                graphics.drawImage(p.getSprite(), 19 + offset, 10, 15, 15, this); 
                offset -= 5;
            }
        }

        int offset = 0;
        ImageIcon sunIcon = new ImageIcon("images/Sun.png");
        for (int i = 0; i < sunCount/50; i++)
        {
            graphics.drawImage(sunIcon.getImage(), 12, 10 - offset, 25, 25, this);
            offset += 5;
        }
    }

    /**
     * Method for allowing the TilePanel object to spawn one sun;
     */
    
    public void generateSun()
    {
        sunCount += 50;
    }

    /**
     * Method for killing the Timers and TimerTasks of the TilePanel object
     */

    public void killTimer()
    {
        if (sunflowerTimer != null && sunflowerTask != null)
        {
            sunflowerTimer.cancel();
            sunflowerTask.cancel();
            sunflowerTimer = null;
            sunflowerTask = null;
        }

        if (sunflowerUpdateTimer != null && sunflowerUpdateTask != null)
        {
            sunflowerUpdateTimer.cancel();
            sunflowerUpdateTask.cancel();
            sunflowerUpdateTimer = null;
            sunflowerUpdateTask = null;
        }
    }

}

