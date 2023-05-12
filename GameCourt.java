package org.cis1200.snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.LinkedList;

/**
 * GameCourt
 * This class holds the primary game logic for how different objects interact
 * with one another. Take time to understand how the timer interacts with the
 * different methods and how it repaints the GUI on every tick().
 */
public class GameCourt extends JPanel implements Serializable {

    // the state of the game logic
    private Snake snake; // the Snake, keyboard control
    private Food food; // the Food, doesn't move once spawned
    private boolean playing = false; // whether the game is running
    private final JLabel status; // Current status text, i.e. "Running..."
    private int score; // current score of the player
    private int highScore; // overall high score of all games played
    private boolean brokeHighScore = false;

    // Game constants
    public static final int COURT_WIDTH = 300;
    public static final int COURT_HEIGHT = 300;

    // Update interval for timer, in milliseconds
    public static final int INTERVAL = 100;

    public GameCourt(JLabel status) {
        // creates border around the court area, JComponent method
        setBackground(Color.BLACK);

        // The timer is an object which triggers an action periodically with the
        // given INTERVAL. We register an ActionListener with this timer, whose
        // actionPerformed() method is called each time the timer triggers. We
        // define a helper method called tick() that actually does everything
        // that should be done in a single time step.
        Timer timer = new Timer(INTERVAL, e -> tick());
        timer.start();

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        // This key listener allows the snake to move as long as an arrow key
        // is pressed, by changing the snake's velocity accordingly. (The tick
        // method below actually moves the snake.)
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT && snake.getDirection() != Direction.RIGHT) {
                    snake.setDirection(Direction.LEFT);
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT
                        && snake.getDirection() != Direction.LEFT) {
                    snake.setDirection(Direction.RIGHT);
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN
                        && snake.getDirection() != Direction.UP) {
                    snake.setDirection(Direction.DOWN);
                } else if (e.getKeyCode() == KeyEvent.VK_UP
                        && snake.getDirection() != Direction.DOWN) {
                    snake.setDirection(Direction.UP);
                }
            }
        });

        this.status = status;
    }

    /**
     * (Re-)set the game to its initial state.
     */
    public void reset() {
        // reset Snake object
        Snake.setSnake(new LinkedList<>());
        snake = new Snake(COURT_WIDTH, COURT_HEIGHT);

        // reset Food
        food = new Food(COURT_WIDTH, COURT_HEIGHT);

        // reset playing status
        playing = true;

        // reset score
        score = 0;

        // reset status label
        status.setText("Score: " + score + "\n High Score: " + highScore);

        // Make sure that this component has the keyboard focus
        requestFocusInWindow();
    }

    /**
     * This method is called every time the timer defined in the constructor
     * triggers.
     */
    void tick() {
        if (playing) {
            // advance snake
            snake.move();

            // check for the game end conditions
            if (snake.getHead().hitWall() || snake.hasHitSelf()) {
                playing = false;

                // if a new high score has been reached, add user to leaderboard
                if (brokeHighScore) {
                    status.setText("New high score reached: " + highScore);
                } else {
                    status.setText("Game Over! Your score was: " + score);
                }
            }

            // check to see if snake eats food

            if (snake.getHead().intersects(food)) {
                food.spawnFood();
                snake.grow();
                score++;

                // check to see if score is higher than the current high score
                if (score > highScore) {
                    highScore = score;
                    brokeHighScore = true;
                }

                status.setText("Score: " + score + "\n High Score: " + highScore);
            }

            // update the display
            repaint();
        }
    }

    /**
     * Saves all game state objects to a file that can be reloaded after exiting
     * the game
     */
    public void saveGame(File file) {
        try {
            // creates file output stream to connect file
            FileOutputStream fos = new FileOutputStream(file);
            // creates object output stream to write objects of game state to file
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            // write all necessary game state objects to file
            oos.writeObject(Snake.getSnake());
            oos.writeObject(food);
            oos.writeObject(playing);
            oos.writeObject(score);
            oos.writeObject(highScore);
            oos.writeObject(brokeHighScore);

            // close streams
            oos.close();
            fos.close();
            System.out.println("Save successful");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method retrieves a previously saved game state so the player
     * can continue the game
     */
    public void loadSavedGame(File file) {
        try {
            // creates a file input stream to connect file
            FileInputStream fis = new FileInputStream(file);

            // creates an object input stream to retrieve objects for game state
            ObjectInputStream ois = new ObjectInputStream(fis);

            // retrieve game state objects
            LinkedList<SnakeSegment> snakeList = (LinkedList<SnakeSegment>) ois.readObject();
            Snake.setSnake(snakeList);
            food = (Food) ois.readObject();
            playing = (boolean) ois.readObject();
            score = (int) ois.readObject();
            highScore = (int) ois.readObject();
            brokeHighScore = (boolean) ois.readObject();

            // close streams
            ois.close();
            fis.close();

            // update status
            status.setText("Score: " + score + "\n High Score: " + highScore);
            repaint();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter Methods
     */
    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public int getScore() {
        return score;
    }

    public int getHighScore() {
        return highScore;
    }

    /**
     * Setter Methods
     */
    public void setScore(int score) {
        this.score = score;
    }

    public void setHighScore(int highScore) {
        this.highScore = highScore;
    }

    /**
     * Overridden Methods
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        snake.draw(g);
        food.draw(g);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(COURT_WIDTH, COURT_HEIGHT);
    }
}