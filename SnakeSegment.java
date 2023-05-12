package org.cis1200.snake;

import java.awt.*;
import java.io.Serializable;

/**
 * Subclass of GameObj
 * Represents one segment of the Snake list
 */
public class SnakeSegment extends GameObj implements Serializable {

    public SnakeSegment(int vX, int vY, int posX, int posY) {
        super(vX, vY, posX, posY, 20, 20, GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(this.getPx(), this.getPy(), this.getWidth(), this.getHeight());
    }
}
