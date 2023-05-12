package org.cis1200.snake;

import java.awt.*;
import java.io.Serializable;

/**
 * A subclass of GameObj
 * The Food class constructs a single food object on the GameCourt.
 * This class handles drawing the food as well as spawning the food
 * in a random location after being eaten by the snake.
 */
public class Food extends GameObj implements Serializable {
    public static final int SIZE = 20; // width/height of Food object
    public static final int INIT_POS_X = 200; // starting x-coordinate
    public static final int INIT_POS_Y = 200; // starting y-coordinate
    public static final int INIT_VEL_X = 0; // initial velocity in x-direction
    public static final int INIT_VEL_Y = 0; // initial velocity in y-direction

    /**
     * Constructor
     */
    public Food(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);
    }

    /**
     * Spawns a new Food object on the GameCourt
     */
    public void spawnFood() {
        boolean foodSpawned = false; // boolean that determines if food was spawned

        while (!foodSpawned) {
            int x = (int) (Math.random() * (getMaxX() - SIZE)); // random x-coordinate
            int y = (int) (Math.random() * (getMaxY() - SIZE)); // random y-coordinate

            // makes sure that the food isn't overlapping with the snake
            boolean overlaps = false;
            for (SnakeSegment segment : Snake.getSnake()) {
                if (x + SIZE > segment.getPx()
                        && y + SIZE > segment.getPy()
                        && segment.getPx() + SIZE > x
                        && segment.getPy() + SIZE > y) {
                    overlaps = true;
                    break;
                }
            }

            if (!overlaps) {
                foodSpawned = true;
                setPx(x);
                setPy(y);
            }
        }
    }

    /**
     * Overrides draw method from GameObj
     */
    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }
}