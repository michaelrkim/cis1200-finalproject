package org.cis1200.snake;

// imports necessary libraries for Java swing

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * Game Main class that specifies the frame and widgets of the GUI
 */
public class RunSnake implements Runnable {
    public void run() {
        // File for saving the game
        File savedGame = new File("files/SavedGame.txt");

        // Top-level frame in which game components live.
        final JFrame frame = new JFrame("Snake");
        frame.setLocation(300, 300);

        // Option pane that displays instructions on how to play the game
        Object[] options = { "New Game", "Resume Saved Game", "Cancel" };

        // Store an int representing which button was clicked on the instructions page
        int buttonClicked = JOptionPane.showOptionDialog(
                frame,
                """
                        Welcome to Snake!

                        Objective:
                        Control the snake to eat the food and grow as long as possible without\s
                        running into the walls or itself.

                        Controls:
                        Use the arrow keys to control the direction of the snake.

                        Gameplay:
                        The snake moves continuously in the direction it is facing. When the snake
                        eats the food, it grows longer. The game ends if the snake runs into the\s
                        walls or runs into itself.

                        Press New Game to start a new game or resume a previously saved game.""",
                "Instructions",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null
        );

        // Close the system when the user hits cancel
        if (buttonClicked == 2) {
            System.exit(0);
        }

        // Status panel that displays score
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel();
        status_panel.add(status);

        // Main playing area
        final GameCourt court = new GameCourt(status);
        frame.add(court, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(e -> court.reset());
        control_panel.add(reset);

        // Save & Exit button
        final JButton save = new JButton("Save & Exit");
        save.addActionListener(
                e -> {
                    // clear any old save data
                    try {
                        PrintWriter pw = new PrintWriter(savedGame);
                        pw.print("");
                        pw.close();
                    } catch (FileNotFoundException ex) {
                        throw new RuntimeException(ex);
                    }

                    // save the game
                    court.saveGame(savedGame);
                    // exit the game
                    System.exit(0);
                }
        );
        control_panel.add(save);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();

        // Reload game when user selects to
        if (buttonClicked == 1) {
            court.loadSavedGame(savedGame);
        }
    }
}