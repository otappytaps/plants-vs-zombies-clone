package game;

import javax.swing.JPanel;
import java.awt.*;

public class BackgroundPanel extends JPanel
{
    private final Image IMAGE;
    
    /**
     * Constructor for the BackgroundPanel object for the GUI.
     * Used for properly displaying images as the background in the JFrame.
     * @param image The image displayed by the BackgroundPanel object
     */

    public BackgroundPanel(Image image)
    {
        IMAGE = image;
    }

    /**
     * paintComponent is overriden to make sure the image is drawn properly.
     */

    @Override
    protected void paintComponent(Graphics graphics) 
    {
        super.paintComponent(graphics);
        graphics.drawImage(IMAGE, 0, 0, getWidth(), getHeight(), this); 
    }
}
