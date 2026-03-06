package game;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class Controller {

    
    private MyFrame view;

    /**
    * It handles user interactions with the main menu, particularly the Play and Exit buttons.
    * When the Play button is clicked, a message is printed.
    * When the Exit button is clicked, the application exits
    * @param view the main menu gui
    */

    public Controller( MyFrame view) {
        
        this.view = view;

        this.view.getPlayButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Play button clicked!");
            }
        });

        this.view.getExitButton().addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Exiting game...");
                System.exit(0);
            }
        });
    }


}
