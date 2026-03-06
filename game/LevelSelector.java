package game;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LevelSelector extends JFrame {

    
    private BufferedImage backgroundImage;

    /**
     * It displays a background image with clickable invisible buttons over level tiles.
     * When a level is selected, it launches the corresponding map in the game.
     * A "Back" button allows returning to the main menu.
     * @param mainMenu the main menu gui
     */


    public LevelSelector(JFrame mainMenu) {

        

        setTitle("Select Level");
        setSize(1600,1050);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        

    
         try {
            backgroundImage = ImageIO.read(new File("images/Map Selection.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        backgroundPanel.setLayout(null);
        setContentPane(backgroundPanel);
        
        





        JButton level1 = new JButton("Level 1");
        JButton level2 = new JButton("Level 2");
        JButton level3 = new JButton("Level 3");
        JButton backButton = new JButton("Back");

        level1.setOpaque(false);
        level1.setContentAreaFilled(false);
        level1.setBorderPainted(false);
        level1.setForeground(new Color(0,0,0,0));
        level1.setFocusPainted(false);
        level1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        //level1.addActionListener(e -> System.exit(0));

        level2.setOpaque(false);
        level2.setContentAreaFilled(false);
        level2.setBorderPainted(false);
        level2.setForeground(new Color(0,0,0,0));
        level2.setFocusPainted(false);
        level2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        //level2.addActionListener(e -> System.exit(0));

        level3.setOpaque(false);
        level3.setContentAreaFilled(false);
        level3.setBorderPainted(false);
        level3.setForeground(new Color(0,0,0,0));
        level3.setFocusPainted(false);
        level3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        //level3.addActionListener(e -> System.exit(0));



       
        level1.setBounds(250, 270, 275, 330);
        level2.setBounds(650, 270, 275, 330);
        level3.setBounds(1080, 270, 250, 300);
        backButton.setBounds(690, 800, 200, 60);

        backgroundPanel.add(level1);
        backgroundPanel.add(level2);
        backgroundPanel.add(level3);
        backgroundPanel.add(backButton);


        level1.addActionListener(e -> loadLevel(1));
        level2.addActionListener(e -> loadLevel(2));
        level3.addActionListener(e -> loadLevel(3));

        
        backButton.addActionListener(e -> {
            this.dispose();              
            mainMenu.setVisible(true);         
        });
        

    }

    private void loadLevel(int map) {
        
        dispose(); 
        SwingUtilities.invokeLater(() -> {
        
        Game.run(map); 
    });
        
        
    }
}
