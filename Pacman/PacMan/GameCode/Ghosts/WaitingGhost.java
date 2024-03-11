package PacMan.GameCode.Ghosts;

import PacMan.GameCode.GameConstants.AllGameConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class WaitingGhost extends Ghost{

    private int chaseingMoves, waitingMoves;
    private Threader thread;
    public WaitingGhost() throws IOException {
        chaseingMoves = 20;
        waitingMoves = 5;

        x = AllGameConstants.NUM_OF_X_BLOCKS / 2 + 1;
        y = AllGameConstants.NUM_OF_Y_BLOCKS / 2 - 5;
        paintGhost();
    }

    @Override
    protected void paintGhost() throws IOException {
        imageOfGhost = ImageIO.read(new File("src/PacMan/Resources/Photos/WaitingGhost.png"));
        resizedGhostImage = imageOfGhost.
                getScaledInstance(AllGameConstants.BLOCK_SIZE,
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
            double currentDistanceToPacMan = calculateDistanceToPacMan(x, y);
            if (currentDistanceToPacMan < 10 && chaseingMoves > 0) {
                if (calculateDistanceToPacMan(x + 1, y) < currentDistanceToPacMan && AllGameConstants.MAP[y][x + 1] == 1)
                {
                    x++;
                }
                else if (calculateDistanceToPacMan(x - 1, y) < currentDistanceToPacMan
                        && AllGameConstants.MAP[y][x - 1] == 1) {
                    x--;
                }
                else if (calculateDistanceToPacMan(x, y + 1) < currentDistanceToPacMan
                        && AllGameConstants.MAP[y + 1][x] == 1) {
                    y++;
                }
                else if (calculateDistanceToPacMan(x, y - 1) < currentDistanceToPacMan
                        && AllGameConstants.MAP[y - 1][x] == 1) {
                    y--;
                }
                chaseingMoves--;
            } else {
                waitingMoves--;

                if (waitingMoves < 0)
                {
                    chaseingMoves = 20;
                    waitingMoves = 5;
                }
            }
        }
    }
}
