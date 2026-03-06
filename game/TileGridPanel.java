package game;

import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class TileGridPanel extends JPanel
{

    private Timer sunTimer;
    private TimerTask sunTask;
    private TilePanel[][] tilePanels;

    /**
     * Constructor for the TileGridPanel object for the GUI.
     * Used to properly display the TilePanel objects in a grid.
     * @param gameEngine the Engine that is running the game
     */

    public TileGridPanel(Engine gameEngine)
    {
        Tile[][] map = gameEngine.getMap();
        tilePanels = new TilePanel[Config.MAPSIZE_ROW][Config.MAPSIZE_COL];

        setLayout(new GridLayout(Config.MAPSIZE_ROW, Config.MAPSIZE_COL, 2, 9));
        setOpaque(false);

        for (int i = 0; i < Config.MAPSIZE_ROW; i++) 
        {
            for (int j = 0; j < Config.MAPSIZE_COL; j++) 
            {
                TilePanel tilePanel = new TilePanel(map[i][j]);
                add(tilePanel);
                tilePanels[i][j] = tilePanel;
            }
        }
    }

    /**
     * Method for allowing a random TilePanel of the TileGridPanel to generate 1 sun every 10 seconds
     */

    public void naturalSunGeneration()
    {
        sunTimer = new Timer();
        sunTask = new TimerTask()
        {
            public void run()
            {
                Random rand = new Random();
                int row = rand.nextInt(Config.MAPSIZE_ROW);
                int col = rand.nextInt(Config.LAWNSIZE_COL - 1) + 1;
                tilePanels[row][col].generateSun();
            }
        };
        sunTimer.scheduleAtFixedRate(sunTask, 10000, 10000);
    }

    /**
     * Method for killing the Timers and TimerTasks of the TileGridPanel object and its TilePanels.
     */

    public void killTimer()
    {
        if (sunTimer != null && sunTask != null)
        {
            sunTimer.cancel();
            sunTask.cancel();
            sunTimer = null;
            sunTask = null;
        }

        for (int i = 0; i < Config.MAPSIZE_ROW; i++) 
        {
            for (int j = 0; j < Config.MAPSIZE_COL; j++) 
            {
                tilePanels[i][j].killTimer();
            }
        }
    }
}
