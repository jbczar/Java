package PacMan.GameCode.Ghosts;

import PacMan.GameCode.GameConstants.AllGameConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class RandomGhost extends Ghost{

    private int movesInOneDirection;
    private int latestMove;
    private Threader thread;


    public RandomGhost() throws IOException {
        latestMove = 0;
        movesInOneDirection = 3;
        x = AllGameConstants.NUM_OF_X_BLOCKS / 2 + 7;
        y = AllGameConstants.NUM_OF_Y_BLOCKS / 2 - 11;
        paintGhost();
    }

    @Override
    protected void paintGhost() throws IOException {
        imageOfGhost = ImageIO.read(new File("src/PacMan/Resources/Photos/RandomGhost.png"));
        resizedGhostImage = imageOfGhost
                .getScaledInstance(AllGameConstants.BLOCK_SIZE,
                                    AllGameConstants.BLOCK_SIZE,
                                        Image.SCALE_DEFAULT);
    }

    @Override
    public void move(int pacManPositionX, int pacManPositionY) {
        this.pacManPositionX = pacManPositionX;
        this.pacManPositionY = pacManPositionY;

        thread = new Threader();
        thread.start();
    }

    private class Threader extends Thread {

        @Override
        public void run() {
            switch (latestMove) {
                case 0:
                    if (AllGameConstants.MAP[y][x + 1] == 1) {
                        x++;
                    }
                    break;
                case 1:
                    if (AllGameConstants.MAP[y + 1][x] == 1) {
                        y++;
                    }
                    break;
                case 2:
                    if (AllGameConstants.MAP[y][x - 1] == 1) {
                        x--;
                    }
                    break;
                case 3:
                    if (AllGameConstants.MAP[y - 1][x] == 1) {
                        y--;
                    }
                    break;
                default:
                    break;
            }
            movesInOneDirection--;
            if (movesInOneDirection == 0) {

                latestMove = getRandomDirection(0, 4);
                movesInOneDirection = 3;
            }
        }
    }
}
