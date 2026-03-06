package game;

import javax.swing.*;

import plants.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class Game 
{
    public static String selectedAction = null;
    public static int mapNumber;
    public static Engine gameEngine;
    public static int sunCount = 150;

    private static Timer gameOverTimer;
    private static TimerTask gameOverTask;

    private static Timer winTimer;
    private static TimerTask winTask;
    private static int seconds = 0;

    private static SystemMusic mapMusic;
    private static SystemMusic winSound;
    private static SystemMusic loseSound;

    /**
     * Method for running the actual game.
     * Initializes GUI and Game engine.
     * @param mapNumber the map number to be loaded in the game
     */

    public static void run(int mapNumber)
    {
        gameEngine = new Engine(mapNumber);
        Game.mapNumber = mapNumber;

        JFrame frame = new JFrame("Plants VS Zombies");
        // all png images must follow 1000 x 429 px
        frame.setSize(1000, 429);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        ImageIcon mapImage;

        // switch case for loading the proper map background
        switch (mapNumber)
        {
            case 1: 
            {
                mapImage = new ImageIcon("images/Map1.png");
                break;
            }

            case 2:
            {
                mapImage = new ImageIcon("images/Map2.png");
                break;
            }

            case 3:
            {
                mapImage = new ImageIcon("images/Map3.png");
                break;
            }

            default:
            {
                throw new RuntimeException(new IllegalArgumentException("Invalid map number."));
            }
        }


        if (MyFrame.menuAudio != null) {
            MyFrame.menuAudio.stop();
        }

        try {
            if (mapMusic != null) mapMusic.stop();
            switch (mapNumber) {
                case 1:
                    mapMusic = new SystemMusic("audio/DayGame.wav");
                    break;
                case 2:
                    mapMusic = new SystemMusic("audio/NightGame.wav");
                    break;
                case 3:
                    mapMusic = new SystemMusic("audio/3rdGame.wav");
                    break;
            }
            mapMusic.loop();
        } catch (Exception e) {
            System.out.println("Error loading map music: " + e.getMessage());
        }

        try {
            if (winSound != null && loseSound != null)
            {
                    winSound.stop(); loseSound.stop();
            }
            switch (mapNumber) {
                case 1:
                    winSound = new SystemMusic("audio/GenericWin.wav");
                    loseSound = new SystemMusic("audio/GenericLose.wav");
                    break;
                case 2:
                    winSound = new SystemMusic("audio/GenericWin.wav");
                    loseSound = new SystemMusic("audio/GenericLose.wav");
                    break;
                case 3:
                    winSound = new SystemMusic("audio/3rdWin.wav");
                    loseSound = new SystemMusic("audio/3rdLose.wav");
                    break;
            }
            
        } catch (Exception e) {
            System.out.println("Error loading map music: " + e.getMessage());
        }


        BackgroundPanel bgPanel = new BackgroundPanel(mapImage.getImage());

        // dashboard (for placing plants) GUI

        JPanel dashboard = new JPanel();
        dashboard.setLayout(new FlowLayout());
        dashboard.setBackground(new Color( 137, 81, 41));

        ImageIcon rawIcon = new ImageIcon("images/Sunflower.png");
        Image resizedImage = rawIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(resizedImage);
        JButton sunflowerBtn = new JButton("50", resizedIcon);
        sunflowerBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        sunflowerBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        sunflowerBtn.setPreferredSize(new Dimension(130, 70));
        sunflowerBtn.setBackground(new Color(197, 137, 95));
        sunflowerBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        sunflowerBtn.addActionListener(e -> 
        {
                if (sunCount >= 50)
                {
                    selectedAction = "sunflower";
                    sunCount -= 50;
                    sunflowerBtn.setEnabled(false);
                    sunflowerBtn.setText("Cooldown...");
                    Timer cooldownTimer = new Timer();
                    TimerTask cooldownTask = new TimerTask() 
                    {
                        public void run()
                        {
                            sunflowerBtn.setEnabled(true);
                            sunflowerBtn.setText("50");
                            cooldownTimer.cancel();
                        }
                    };
                    cooldownTimer.schedule(cooldownTask, (int)(Sunflower.getRegenerateRate()*1000));
                }
            }
        );


        rawIcon = new ImageIcon("images/Peashooter.png");
        resizedImage = rawIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        resizedIcon = new ImageIcon(resizedImage);
        JButton peashooterBtn = new JButton("100", resizedIcon);
        peashooterBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        peashooterBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        peashooterBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        peashooterBtn.setPreferredSize(new Dimension(130, 70));
        peashooterBtn.setBackground(new Color(197, 131, 95));
        peashooterBtn.addActionListener(e -> 
        {
                if (sunCount >= 100)
                {
                    selectedAction = "peashooter";
                    sunCount -= 100;
                    peashooterBtn.setEnabled(false);
                    peashooterBtn.setText("Cooldown...");
                    Timer cooldownTimer = new Timer();
                    TimerTask cooldownTask = new TimerTask() 
                    {
                        public void run()
                        {
                            peashooterBtn.setEnabled(true);
                            peashooterBtn.setText("100");
                            cooldownTimer.cancel();
                        }
                    };
                    cooldownTimer.schedule(cooldownTask, (int)(Peashooter.getRegenerateRate()*1000));
                }
            }
        );

        rawIcon = new ImageIcon("images/SnowPeashooter.png");
        resizedImage = rawIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        resizedIcon = new ImageIcon(resizedImage);
        JButton snowpeashooterBtn = new JButton("125", resizedIcon);
        snowpeashooterBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        snowpeashooterBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        snowpeashooterBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        snowpeashooterBtn.setPreferredSize(new Dimension(130, 70));
        snowpeashooterBtn.setBackground(new Color(197, 131, 95));
        snowpeashooterBtn.addActionListener(e -> 
        {
                if (sunCount >= 125)
                {
                    selectedAction = "snowpeashooter";
                    sunCount -= 125;
                    snowpeashooterBtn.setEnabled(false);
                    snowpeashooterBtn.setText("Cooldown...");
                    Timer cooldownTimer = new Timer();
                    TimerTask cooldownTask = new TimerTask() 
                    {
                        public void run()
                        {
                            snowpeashooterBtn.setEnabled(true);
                            snowpeashooterBtn.setText("125");
                            cooldownTimer.cancel();
                        }
                    };
                    cooldownTimer.schedule(cooldownTask, (int)(SnowPeashooter.getRegenerateRate()*1000));
                }
            }
        );

        rawIcon = new ImageIcon("images/Wallnut.png");
        resizedImage = rawIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        resizedIcon = new ImageIcon(resizedImage);
        JButton wallnutBtn = new JButton("50", resizedIcon);
        wallnutBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        wallnutBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        wallnutBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        wallnutBtn.setPreferredSize(new Dimension(130, 70));
        wallnutBtn.setBackground(new Color(197, 131, 95));
        wallnutBtn.addActionListener(e -> 
        {
                if (sunCount >= 50)
                {
                    selectedAction = "wallnut";
                    sunCount -= 50;
                    wallnutBtn.setEnabled(false);
                    wallnutBtn.setText("Cooldown...");
                    Timer cooldownTimer = new Timer();
                    TimerTask cooldownTask = new TimerTask() 
                    {
                        public void run()
                        {
                            wallnutBtn.setEnabled(true);
                            wallnutBtn.setText("50");
                            cooldownTimer.cancel();
                        }
                    };
                    cooldownTimer.schedule(cooldownTask, (int)(Wallnut.getRegenerateRate()*1000));
                }
            }
        );

        rawIcon = new ImageIcon("images/PotatoMine.png");
        resizedImage = rawIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        resizedIcon = new ImageIcon(resizedImage);
        JButton potatomineBtn = new JButton("25", resizedIcon);
        potatomineBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        potatomineBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        potatomineBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        potatomineBtn.setPreferredSize(new Dimension(130, 70));
        potatomineBtn.setBackground(new Color(197, 131, 95));
        potatomineBtn.addActionListener(e -> 
        {
            if (sunCount >= 25)
            {
                selectedAction = "potatomine";
                sunCount -= 25;
                potatomineBtn.setEnabled(false);
                potatomineBtn.setText("Cooldown...");
                Timer cooldownTimer = new Timer();
                TimerTask cooldownTask = new TimerTask() 
                {
                    public void run()
                    {
                        potatomineBtn.setEnabled(true);
                        potatomineBtn.setText("25");
                        cooldownTimer.cancel();
                    }
                };
                cooldownTimer.schedule(cooldownTask, (int)(PotatoMine.getRegenerateRate()*1000));
            }
        }
        );

        rawIcon = new ImageIcon("images/CherryBomb.png");
        resizedImage = rawIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        resizedIcon = new ImageIcon(resizedImage);
        JButton cherrybombBtn = new JButton("150", resizedIcon);
        cherrybombBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        cherrybombBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        cherrybombBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cherrybombBtn.setPreferredSize(new Dimension(130, 70));
        cherrybombBtn.setBackground(new Color(197, 131, 95));
        cherrybombBtn.addActionListener(e -> 
        {
                if (sunCount >= 150)
                {
                    selectedAction = "cherrybomb";
                    sunCount -= 150;
                    cherrybombBtn.setEnabled(false);
                    cherrybombBtn.setText("Cooldown...");
                    Timer cooldownTimer = new Timer();
                    TimerTask cooldownTask = new TimerTask() 
                    {
                        public void run()
                        {
                            cherrybombBtn.setEnabled(true);
                            cherrybombBtn.setText("150");
                            cooldownTimer.cancel();
                        }
                    };
                    cooldownTimer.schedule(cooldownTask, (int)(CherryBomb.getRegenerateRate()*1000));
                }
            }
        );
        
        rawIcon = new ImageIcon("images/Puffshroom.png");
        resizedImage = rawIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        resizedIcon = new ImageIcon(resizedImage);
        JButton puffshroomBtn = new JButton("0", resizedIcon);
        puffshroomBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        puffshroomBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        puffshroomBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        puffshroomBtn.setPreferredSize(new Dimension(130, 70));
        puffshroomBtn.setBackground(new Color(197, 131, 95));
        puffshroomBtn.addActionListener(e -> 
        {
                if (sunCount >= 0)
                {
                    selectedAction = "puffshroom";
                    puffshroomBtn.setEnabled(false);
                    puffshroomBtn.setText("Cooldown...");
                    Timer cooldownTimer = new Timer();
                    TimerTask cooldownTask = new TimerTask() 
                    {
                        public void run()
                        {
                            puffshroomBtn.setEnabled(true);
                            puffshroomBtn.setText("0");
                            cooldownTimer.cancel();
                        }
                    };
                    cooldownTimer.schedule(cooldownTask, (int)(Puffshroom.getRegenerateRate()*1000));
                }
            }
        );

        rawIcon = new ImageIcon("images/Fumeshroom.png");
        resizedImage = rawIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        resizedIcon = new ImageIcon(resizedImage);
        JButton fumeshroomBtn = new JButton("75", resizedIcon);
        fumeshroomBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        fumeshroomBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        fumeshroomBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        fumeshroomBtn.setPreferredSize(new Dimension(130, 70));
        fumeshroomBtn.setBackground(new Color(197, 131, 95));
        fumeshroomBtn.addActionListener(e -> 
        {
                if (sunCount >= 75)
                {
                    selectedAction = "fumeshroom";
                    sunCount -= 75;
                    fumeshroomBtn.setEnabled(false);
                    fumeshroomBtn.setText("Cooldown...");
                    Timer cooldownTimer = new Timer();
                    TimerTask cooldownTask = new TimerTask() 
                    {
                        public void run()
                        {
                            fumeshroomBtn.setEnabled(true);
                            fumeshroomBtn.setText("75");
                            cooldownTimer.cancel();
                        }
                    };
                    cooldownTimer.schedule(cooldownTask, (int)(Fumeshroom.getRegenerateRate()*1000));
                }
            }
        );

        rawIcon = new ImageIcon("images/Shovel.png");
        resizedImage = rawIcon.getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        resizedIcon = new ImageIcon(resizedImage);
        JButton shovelBtn = new JButton("Remove Plant", resizedIcon);
        shovelBtn.setHorizontalTextPosition(SwingConstants.CENTER);
        shovelBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        shovelBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        shovelBtn.setPreferredSize(new Dimension(130, 70));
        shovelBtn.setBackground(new Color(197, 131, 95));
        shovelBtn.addActionListener(e -> selectedAction = "shovel");

        dashboard.add(sunflowerBtn);
        dashboard.add(peashooterBtn);
        dashboard.add(snowpeashooterBtn);
        dashboard.add(wallnutBtn);
        dashboard.add(potatomineBtn);
        dashboard.add(cherrybombBtn);
        dashboard.add(puffshroomBtn);
        dashboard.add(fumeshroomBtn);
        dashboard.add(shovelBtn);

        dashboard.setBounds(711, 0, 275, 429); 
        bgPanel.add(dashboard);

        // sunCounter GUI
        JPanel sunCounterPanel = new JPanel();
        sunCounterPanel.setLayout(new BorderLayout());
        sunCounterPanel.setBackground(new Color( 137, 81, 41));
        sunCounterPanel.setBounds(0, 0, 150, 50);
        ImageIcon rawSunIcon = new ImageIcon("images/Sun.png");
        Image resizedSunIcon = rawSunIcon.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon sunIcon = new ImageIcon(resizedSunIcon);
        
        JLabel sunCountLabel = new JLabel(" " + sunCount, sunIcon, JLabel.LEFT);
        sunCountLabel.setFont(UIManager.getFont("Button.font"));
        sunCountLabel.setForeground(Color.YELLOW);

        sunCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        sunCountLabel.setVerticalAlignment(SwingConstants.CENTER);

        sunCounterPanel.add(sunCountLabel, BorderLayout.CENTER);
        bgPanel.add(sunCounterPanel);
        
        Timer sunCounterTimer = new Timer();
        TimerTask sunCounterTask = new TimerTask()
        {
            public void run()
            {
                sunCountLabel.setText("" + sunCount);
            }
        };
        sunCounterTimer.scheduleAtFixedRate(sunCounterTask, 0, 50);


        // tile grid GUI
        bgPanel.setLayout(null);
        TileGridPanel gridPanel = new TileGridPanel(gameEngine);
        int tileW = 55;
        int tileH = 55;
        int gridRows = Config.MAPSIZE_ROW;
        int gridCols = Config.MAPSIZE_COL;
        int hgap = 2;
        int vgap = 9;
        int gridX = 120;
        int gridY = 60;
        int gridWidth = (tileW * gridCols) + (hgap * (gridCols - 1));
        int gridHeight = (tileH * gridRows) + (vgap * (gridRows - 1));
        gridPanel.setBounds(gridX, gridY, gridWidth, gridHeight);
        bgPanel.add(gridPanel);
        frame.setContentPane(bgPanel);
        frame.setVisible(true);

        Timer tileRepaintTimer = new Timer();
        TimerTask tileRepaintTask = new TimerTask() 
        {

            public void run()
            {
                gridPanel.repaint();
            }
            
        };
        tileRepaintTimer.scheduleAtFixedRate(tileRepaintTask, 0, 50);

        // natural sun generation if the map is not night time 
        if (mapNumber != 2) // map 2 is the only night map in this game
        {
            gridPanel.naturalSunGeneration();
        }

        // game over check
        gameOverTimer = new Timer();
        gameOverTask = new TimerTask() 
        {
            public void run()
            {
                if (gameEngine.getGameOver())
                {
                    this.cancel();
                    gameOverTimer.cancel();
                    gameOverTask = null;
                    gameOverTimer = null;

                    winTimer.cancel();
                    winTask.cancel();
                    winTask = null;
                    winTimer = null;

                    gameEngine.killTimer();
                    gridPanel.killTimer();

                    frame.dispose();

                    mapMusic.stop();
                    loseSound.play();

                    // loser frame
                    JFrame loseFrame = new JFrame("Plants vs Zombies");
                    loseFrame.setSize(1000, 563);
                    loseFrame.setResizable(false);
                    loseFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    loseFrame.setLocationRelativeTo(null);
                    ImageIcon winImage = new ImageIcon("images/Loser.png");
                    BackgroundPanel winBgPanel = new BackgroundPanel(winImage.getImage());
                    loseFrame.setContentPane(winBgPanel);
                    loseFrame.setLayout(null);
                    JButton exitBtn = new JButton();
                    exitBtn.setBounds(20, 453, 134, 50); 
                    exitBtn.setOpaque(false);
                    exitBtn.setContentAreaFilled(false);
                    exitBtn.setBorderPainted(false);
                    exitBtn.setForeground(new Color(0,0,0, 0));
                    exitBtn.setFocusPainted(false);
                    exitBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                    exitBtn.addActionListener(e -> 
                    {
                        loseFrame.dispose();
                        System.exit(0);

                    }
                    );

                    JButton mainmenuBtn = new JButton();
                    mainmenuBtn.setBounds(826, 453, 134, 50); 
                    mainmenuBtn.setOpaque(false);
                    mainmenuBtn.setContentAreaFilled(false);
                    mainmenuBtn.setBorderPainted(false);
                    mainmenuBtn.setForeground(new Color(0,0,0, 0));
                    mainmenuBtn.setFocusPainted(false);
                    mainmenuBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                    mainmenuBtn.addActionListener(e -> 
                    {
                        loseFrame.dispose();
                        Game.sunCount = 150;
                        javax.swing.SwingUtilities.invokeLater(() -> {
                        MyFrame view = new MyFrame();
                        new Controller(view); // hook up the controller
                    });
                    }

                    );

                    loseFrame.add(exitBtn);
                    loseFrame.add(mainmenuBtn);
                    loseFrame.setVisible(true);
                }
            }
        };
        gameOverTimer.scheduleAtFixedRate(gameOverTask, 0, 50);

        // winner check
        winTimer = new Timer();
        winTask = new TimerTask()
        {
            public void run()
            {
                winTimer.cancel();
                winTask = null;
                winTimer = null;

                gameOverTimer.cancel();
                gameOverTask.cancel();
                gameOverTask = null;
                gameOverTimer = null;

                gameEngine.killTimer();
                gridPanel.killTimer();

                frame.dispose();

                mapMusic.stop();
                winSound.play();

                // winner frame
                JFrame winFrame = new JFrame("Plants vs Zombies");
                winFrame.setSize(1000, 563);
                winFrame.setResizable(false);
                winFrame.setLocationRelativeTo(null);
                winFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                ImageIcon winImage = new ImageIcon("images/Winner.png");
                BackgroundPanel winBgPanel = new BackgroundPanel(winImage.getImage());
                winFrame.setContentPane(winBgPanel);
                winFrame.setLayout(null);
                JButton exitBtn = new JButton();
                exitBtn.setBounds(20, 453, 134, 50); 
                exitBtn.setOpaque(false);
                exitBtn.setContentAreaFilled(false);
                exitBtn.setBorderPainted(false);
                exitBtn.setForeground(new Color(0,0,0, 0));
                exitBtn.setFocusPainted(false);
                exitBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                exitBtn.addActionListener(e -> 
                {
                    winFrame.dispose();
                    System.exit(0);

                }
                );

                JButton mainmenuBtn = new JButton();
                mainmenuBtn.setBounds(826, 453, 134, 50); 
                mainmenuBtn.setOpaque(false);
                mainmenuBtn.setContentAreaFilled(false);
                mainmenuBtn.setBorderPainted(false);
                mainmenuBtn.setForeground(new Color(0,0,0, 0));
                mainmenuBtn.setFocusPainted(false);
                mainmenuBtn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                mainmenuBtn.addActionListener(e -> 
                {
                    winFrame.dispose();
                    Game.sunCount = 150;
                    javax.swing.SwingUtilities.invokeLater(() -> {
                        MyFrame view = new MyFrame();
                        new Controller(view); // hook up the controller
                    });
                }
                );
                winFrame.add(exitBtn);
                winFrame.add(mainmenuBtn);
                winFrame.setVisible(true);  
            }
        };
        winTimer.schedule(winTask, 260000); // 260 seconds

    }
}
