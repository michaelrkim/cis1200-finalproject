package org.cis1200.snake;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    /**
     * Tests for the SnakeSegment CLass
     */
    @Test
    public void testSnakeSegmentsHaveSameSize() {
        SnakeSegment seg1 = new SnakeSegment(0, 0, 20, 20);
        SnakeSegment seg2 = new SnakeSegment(0, 0, 40, 40);

        assertEquals(seg1.getWidth(), seg2.getWidth());
        assertEquals(seg1.getHeight(), seg2.getHeight());
    }

    /**
     * Tests for the Snake Class
     */
    @Test
    public void testSnakeCreatesHeadAtInitPos() {
        int initX = 20;
        int initY = 20;

        Snake newSnake = new Snake(300, 300);
        SnakeSegment head = newSnake.getHead();

        assertEquals(initX, head.getPx());
        assertEquals(initY, head.getPy());
    }

    @Test
    public void testSnakeInitListLength() {
        int length = 1;
        Snake.setSnake(new LinkedList<>());
        Snake snake = new Snake(300, 300);
        LinkedList<SnakeSegment> snakeList = Snake.getSnake();
        System.out.println(Snake.getSnake().toString());
        assertEquals(length, snakeList.size());
    }

    @Test
    public void testSnakeAddsSegInCorrectPos() {
        Snake.setSnake(new LinkedList<>());
        Snake snake = new Snake(300, 300);
        snake.setDirection(Direction.DOWN);
        snake.move();
        snake.grow();

        int tailX = 20;
        int tailY = 20;
        LinkedList<SnakeSegment> snakeList = Snake.getSnake();
        assertEquals(tailX, snakeList.getLast().getPx());
        assertEquals(tailY, snakeList.getLast().getPy());

        // Snake head position properly updated
        assertEquals(20, snake.getHead().getPx());
        assertEquals(40, snake.getHead().getPy());
    }



    @Test
    public void testSnakeHasHitSelf() {
        Snake.setSnake(new LinkedList<>());
        Snake snake = new Snake(300, 300);
        snake.setDirection(Direction.DOWN);
        for (int i = 0; i < 4; i++) {
            snake.move();
        }

        for (int i = 0; i < 4; i++) {
            snake.grow();
        }

        snake.setDirection(Direction.RIGHT);
        snake.move();

        snake.setDirection(Direction.UP);
        snake.move();

        snake.setDirection(Direction.LEFT);
        snake.move();

        assertTrue(snake.hasHitSelf());
    }

    @Test
    public void testSnakeSetDirectionChangesVelocity() {
        Snake.setSnake(new LinkedList<>());
        Snake snake = new Snake(300, 300);

        snake.setDirection(Direction.UP);
        assertEquals(0, snake.getHead().getVx());
        assertEquals(-20, snake.getHead().getVy());

        snake.setDirection(Direction.DOWN);
        assertEquals(0, snake.getHead().getVx());
        assertEquals(20, snake.getHead().getVy());

        snake.setDirection(Direction.LEFT);
        assertEquals(-20, snake.getHead().getVx());
        assertEquals(0, snake.getHead().getVy());

        snake.setDirection(Direction.RIGHT);
        assertEquals(20, snake.getHead().getVx());
        assertEquals(0, snake.getHead().getVy());
    }

    @Test
    public void testSnakeMove() {
        Snake.setSnake(new LinkedList<>());
        Snake snake = new Snake(300, 300);
        snake.setDirection(Direction.DOWN);
        snake.move();

        assertEquals(20, snake.getHead().getPx());
        assertEquals(40, snake.getHead().getPy());
    }

    /**
     * Tests for Food Class
     */
    @Test
    public void testFoodCreatesFoodAtInitPos() {
        int initX = 200;
        int initY = 200;
        Food food = new Food(300, 300);

        assertEquals(initX, food.getPx());
        assertEquals(initY, food.getPy());
    }

    @Test
    public void testFoodSpawnAvoidsSnake() {
        Food food = new Food(300, 300);
        Snake.setSnake(new LinkedList<>());
        Snake snake = new Snake(300, 300);

        for (int i = 0; i < 10; i++) {
            food.spawnFood();
            assertNotEquals(snake.getPx(), food.getPx());
            assertNotEquals(snake.getPy(), food.getPy());
        }
    }

    /**
     * Tests for GameCourt Class
     */
    @Test
    public void testGameCourtResetEmptiesSnake() {
        GameCourt gc = new GameCourt(new JLabel("reset snake"));
        Snake.setSnake(new LinkedList<>());
        Snake snake = new Snake(300, 300);
        snake.setDirection(Direction.DOWN);
        snake.grow();
        snake.grow();
        assertEquals(3, Snake.getSnake().size());

        gc.reset();
        assertEquals(1, Snake.getSnake().size());
        assertEquals(20, snake.getPx());
        assertEquals(20, snake.getPy());
    }

    @Test
    public void testGameCourtResetRespawnsFood() {
        GameCourt gc = new GameCourt(new JLabel("reset food"));
        gc.reset();

        assertEquals(200, gc.getFood().getPx());
        assertEquals(200, gc.getFood().getPy());
    }

    @Test
    public void testGameCourtResetRemovesScore() {
        GameCourt gc = new GameCourt(new JLabel("reset score"));
        gc.setScore(20);
        gc.reset();

        assertEquals(0, gc.getScore());
    }

    @Test
    public void testGameCourtResetKeepsHighScore() {
        GameCourt gc = new GameCourt(new JLabel("keep high score"));
        gc.setHighScore(50);
        gc.reset();

        assertEquals(50, gc.getHighScore());
    }

}
