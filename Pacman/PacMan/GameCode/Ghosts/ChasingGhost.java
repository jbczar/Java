package PacMan.GameCode.Ghosts;

import PacMan.GameCode.GameConstants.AllGameConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ChasingGhost extends Ghost {
    private int chasingMoves;
    private int retreatingMoves;
    private boolean chaseOrRetreat;
    private Threader thread;


    public ChasingGhost() throws IOException {
        chasingMoves = 0;
        retreatingMoves = 0;
        chaseOrRetreat = true;

        x = AllGameConstants.NUM_OF_X_BLOCKS - 2;
        y = AllGameConstants.NUM_OF_Y_BLOCKS - 2;
        paintGhost();
    }

   //ustawianie layoutu
    @Override
    protected void paintGhost() throws IOException {
        imageOfGhost = ImageIO.read(new File("src/PacMan/Resources/Photos/ChasingGhost.png"));
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
       //mechanizm ducha
        @Override
        public void run() {
            double currentDistanceToPacMan= calculateDistanceToPacMan(x, y);
            if (chaseOrRetreat) {
                if (calculateDistanceToPacMan(x + 1, y) < currentDistanceToPacMan && AllGameConstants.MAP[y][x + 1] == 1) {
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
                chasingMoves++;
                if (chasingMoves == 13) {
                    chaseOrRetreat = false;
                    chasingMoves = 0;
                }
            }

            else
            {
                if (calculateDistanceToPacMan(x + 1, y) > currentDistanceToPacMan && AllGameConstants.MAP[y][x + 1] == 1) {
                    x++;
                }
                else if (calculateDistanceToPacMan(x - 1, y) > currentDistanceToPacMan
                        && AllGameConstants.MAP[y][x - 1] == 1) {
                    x--;
                }
                else if (calculateDistanceToPacMan(x, y + 1) > currentDistanceToPacMan
                        && AllGameConstants.MAP[y + 1][x] == 1) {
                    y++;
                }
                else if (calculateDistanceToPacMan(x, y - 1) > currentDistanceToPacMan
                        && AllGameConstants.MAP[y - 1][x] == 1) {
                    y--;
                }
                retreatingMoves++;
                if (retreatingMoves == 1) {
                    chaseOrRetreat = true;
                    retreatingMoves = 0;
                }
            }
        }
    }
}
