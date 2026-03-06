package game;

import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


    /**
     * Represents the main menu GUI window of the game.
     * Also manages background music playback and navigation to the level selection screen. 
     */

public class MyFrame extends JFrame {

    private JButton playButton, exitButton, audioButton;
    private  JButton creditsButton;
    private BufferedImage backgroundImage;
    private boolean isMuted = false;
    protected static SystemMusic menuAudio;

    /**
     * Constructs the main menu frame, loading the background image,
     * initializing all buttons, and setting up music playback.
     */
    
        MyFrame(){

        setTitle("Game Menu");
        setSize(1600,1050);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            backgroundImage = ImageIO.read(new File("images/Menu_E.png"));
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
        backgroundPanel.setLayout(new GridBagLayout()); 

        //music part testing

        try {
            menuAudio = new SystemMusic("audio/MenuMusic.wav");
            menuAudio.loop();
        } catch (Exception e) {
             System.out.println("Error: " + e.getMessage());
        }


        playButton = new JButton("Play");
        exitButton = new JButton("Quit");
        audioButton = new JButton("🔊");
        creditsButton = new JButton("Credits");

        exitButton.setOpaque(false);
        exitButton.setContentAreaFilled(false);
        exitButton.setBorderPainted(false);
        exitButton.setForeground(new Color(0,0,0,0));
        exitButton.setFocusPainted(false);
        exitButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        exitButton.addActionListener(e -> System.exit(0));

        playButton.setPreferredSize(new java.awt.Dimension(550, 70));
        playButton.setOpaque(false);
        playButton.setContentAreaFilled(false);
        playButton.setBorderPainted(false);
        playButton.setForeground(new Color(0,0,0,0));
        playButton.setFocusPainted(false);
        playButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        audioButton.setPreferredSize(new java.awt.Dimension(120, 60));
        audioButton.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 50));
        audioButton.setOpaque(false);
        audioButton.setContentAreaFilled(false);
        audioButton.setBorderPainted(false);
        audioButton.setForeground(Color.BLACK);
        audioButton.setFocusPainted(false);
        audioButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        creditsButton.setText("");
        creditsButton.setPreferredSize(new java.awt.Dimension(200, 60));
        creditsButton.setOpaque(false);
        creditsButton.setContentAreaFilled(false);
        creditsButton.setBorderPainted(false);
        creditsButton.setForeground(Color.BLACK);
        creditsButton.setFocusPainted(false);
        creditsButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        creditsButton.addActionListener(e -> {
            
            JOptionPane.showMessageDialog(this, "Game by Adrian Co & Justin Go", "Credits", JOptionPane.INFORMATION_MESSAGE);
        });

        audioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isMuted = !isMuted;
                if (isMuted) {
                    audioButton.setText("🔇");
                    menuAudio.stop();
                } else {
                    audioButton.setText("🔊");
                    menuAudio.loop();
                }
            }
        });


        playButton.addActionListener(e -> {
            this.setVisible(false); 
            new LevelSelector(this).setVisible(true);
        });



        backgroundPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        

        //PLAY BUTTON
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.NORTHEAST;
        gbc.insets = new Insets(190, 0, 20, 40); 
        backgroundPanel.add(playButton, gbc);

        //SPACER
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        backgroundPanel.add(new JPanel() {{ setOpaque(false); }}, gbc); 

        //EXIT BUTTON
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.weighty = 0.0;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.SOUTHEAST;
        gbc.insets = new Insets(20, 0, 120, 90); 
        backgroundPanel.add(exitButton, gbc);

        //MUTE BUTTON
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(20, 50, 35, 0); 
        backgroundPanel.add(audioButton, gbc);
        
        //CREDITS BUTTON
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(150, 100, 80, 0); 
        backgroundPanel.add(creditsButton, gbc);

        setContentPane(backgroundPanel);
        setVisible(true);

        
    }

    public JButton getPlayButton() {
        return playButton;
    }

    public JButton getExitButton() {
        return exitButton;
    }

}
