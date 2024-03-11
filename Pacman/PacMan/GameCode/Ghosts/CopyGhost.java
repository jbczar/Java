package PacMan.GameCode.Ghosts;

import PacMan.GameCode.GameConstants.AllGameConstants;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class CopyGhost extends Ghost{
    private Threader thread;
    private int randomMovement, lastMove, movesInOneDirection;
    private int chasingMoves, waitingMoves;
    private int movementMoves;

    public CopyGhost() throws IOException {
        chasingMoves = 20;
        waitingMoves = 5;

        movementMoves = 30;
        randomMovement = 0;
        movesInOneDirection = 3;
        lastMove = 1;

        x = AllGameConstants.NUM_OF_X_BLOCKS / 2 - 8;
        y = AllGameConstants.NUM_OF_Y_BLOCKS / 2 + 5;
        paintGhost();
    }

    @Override
    protected void paintGhost() throws IOException {
        imageOfGhost = ImageIO.read(new File("src/PacMan/Resources/Photos/CopyGhost.png"));
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
       //pozycja bazuje na polozeniu innycg duchow
        @Override
        public void run() {
            double currentDistanceToPacMan = calculateDistanceToPacMan(x, y);
            switch (randomMovement) {
                case 0:
                    if (calculateDistanceToPacMan(x + 1, y) < currentDistanceToPacMan
                            && AllGameConstants.MAP[y][x + 1] == 1) {
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
                    break;
                case 1:

                    switch (lastMove) {
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
                        lastMove = getRandomDirection(0, 4);
                        movesInOneDirection = 3;
                    }
                case 2:
                    if (currentDistanceToPacMan < 10 && chasingMoves > 0) {
                        if (calculateDistanceToPacMan(x + 1, y) < currentDistanceToPacMan
                                && AllGameConstants.MAP[y][x + 1] == 1) {
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
                        chasingMoves--;
                    } else {
                        waitingMoves--;
                        if (waitingMoves < 0)
                        {
                            chasingMoves = 20;
                            waitingMoves = 5;
                        }
                    }
                default:
                    break;
            }
            movementMoves--;
            if (movementMoves == 0) {
                randomMovement = getRandomDirection(0, 3);
                movementMoves = 30;
            }
        }
    }
}
