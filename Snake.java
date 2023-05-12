package org.cis1200.snake;

import java.awt.*;
import java.io.Serializable;
import java.util.LinkedList;

/**
 * A subclass of GameObj
 * The Snake class constructs a snake that holds a list of all existing
 * segments of the snake. It also handles methods relating to the snake
 * such as growing, moving, direction, and colliding with itself.
 */
public class Snake extends GameObj implements Serializable {
    public static final int SIZE = 20; // width/height of snake segment
    public static final int INIT_POS_X = 20; // starting x-coordinate
    public static final int INIT_POS_Y = 20; // starting y-coordinate
    public static final int INIT_VEL_X = 0; // initial velocity in x-direction
    public static final int INIT_VEL_Y = 0; // initial velocity in y-direction
    private static LinkedList<SnakeSegment> snake = new LinkedList<>(); // segment list
    private Direction direction; // current direction of snake

    /**
     * Constructor for Snake object
     */
    public Snake(int courtWidth, int courtHeight) {
        super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, SIZE, SIZE, courtWidth, courtHeight);

        // Create the head of the snake and add it to the LinkedList
        SnakeSegment head = new SnakeSegment(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y);
        snake.addFirst(head);
    }

    /**
     * Increases the length of the snake by adding a new segment to the end
     * of the LinkedList
     */
    public void grow() {
        SnakeSegment tail = snake.getLast(); // reference for the tail of snake

        // creates a copy of the current tail of the snake
        SnakeSegment newTail = new SnakeSegment(
                tail.getVx(), tail.getVy(), tail.getPx(),
                tail.getPy()
        );

        // Set the location of the new segment based on direction
        newTail.setPx(tail.getPx() - tail.getVx());
        newTail.setPy(tail.getPy() - tail.getVy());

        // add the new tail to the end of the LinkedList
        snake.addLast(newTail);
    }

    /**
     * Checks to see if the snake head intersects with any part of the snake
     */
    public boolean hasHitSelf() {
        // iterates through snake segments excluding the head to check for intersection
        for (int i = 1; i < snake.size(); i++) {
            SnakeSegment segment = snake.get(i); // current segment of iteration

            // if the head intersects the segment, return true
            if (getHead().intersects(segment)) {
                return true;
            }
        }

        return false;
    }

    // **********************************************************************************
    // * GETTERS
    // **********************************************************************************
    public Direction getDirection() {
        return direction;
    }

    public SnakeSegment getHead() {
        return snake.get(0);
    }

    public static LinkedList<SnakeSegment> getSnake() {
        return snake;
    }

    // **************************************************************************
    // * SETTERS
    // **************************************************************************
    public void setDirection(Direction dir) {
        direction = dir;
        switch (dir) {
            case UP -> {
                getHead().setVx(0);
                getHead().setVy(-SIZE);
            }
            case DOWN -> {
                getHead().setVx(0);
                getHead().setVy(SIZE);
            }
            case LEFT -> {
                getHead().setVx(-SIZE);
                getHead().setVy(0);
            }
            case RIGHT -> {
                getHead().setVx(SIZE);
                getHead().setVy(0);
            }
            default -> throw new IllegalStateException("Unexpected value: " + dir);
        }
    }

    public static void setSnake(LinkedList<SnakeSegment> snakeList) {
        snake = snakeList;
    }

    // **********************************************************************************
    // * OVERRIDDEN METHODS
    // **********************************************************************************
    @Override
    public void move() {
        SnakeSegment head = getHead(); // reference for head of snake

        // create a copy of the head of the snake
        SnakeSegment newHead = new SnakeSegment(
                head.getVx(), head.getVy(), head.getVx(),
                head.getVy()
        );

        // set the position of the new head
        newHead.setPx(head.getPx() + head.getVx());
        newHead.setPy(head.getPy() + head.getVy());

        // add the new head to the front of the list and remove the last segment
        snake.addFirst(newHead);
        snake.removeLast();
    }

    @Override
    public void draw(Graphics g) {
        for (SnakeSegment segment : snake) {
            segment.draw(g);
        }
    }
}