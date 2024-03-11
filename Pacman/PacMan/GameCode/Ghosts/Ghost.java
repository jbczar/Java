package PacMan.GameCode.Ghosts;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

public abstract class Ghost {

    protected BufferedImage imageOfGhost;
    protected Image resizedGhostImage;
    protected int x,y;
    protected int pacManPositionX;
    protected  int pacManPositionY;


    protected abstract void paintGhost() throws IOException;


    protected abstract void move(int pacManPositionX, int pacManPositionY);


    public int getGhostPositionX() {
        return x;
    }
    public int getGhostPositionY() {
        return y;
    }


    public void setGhostPositionX(int x) {
        this.x = x;
    }
    public void setGhostPositionY(int y) {
        this.y = y;
    }


    public Image getGhostImage() {
        return resizedGhostImage;
    }


    protected double calculateDistanceToPacMan(int posX, int posY) {
        return Math.sqrt(Math.pow((posX - pacManPositionX), 2) + Math.pow((posY - pacManPositionY), 2));
    }

    protected int getRandomDirection(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min) + min;
    }
}