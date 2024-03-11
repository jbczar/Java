package PacMan.GameCode;
import PacMan.GameCode.GameConstants.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class PacMan {
    private int x;
    private int y;
    private BufferedImage imageOfPacMan;

    private Image resizedImageOfPacMan;


    public PacMan() throws IOException {
        this.x = 1;
        this.y = 1;
        imageOfPacMan = ImageIO.read(new File("src/PacMan/Resources/Photos/PAC_MAN_1.png"));
        resizedImageOfPacMan = imageOfPacMan
                .getScaledInstance(AllGameConstants.BLOCK_SIZE,
                                    AllGameConstants.BLOCK_SIZE,
                                    Image.SCALE_DEFAULT);
    }

    //pozyja pacMana
    public int getPacManPositionX() {
        return x;
    }
    public int getPacManPositionY() {
        return y;
    }

    //nowa pozycjaPacmana

    public void setPacManPositionX(int x) {
        this.x = x;
    }
    public void setPacManPositionY(int y) {
        this.y = y;
    }


    public Image getPacManImage() {
        return resizedImageOfPacMan;
    }
    public void movePacMan(char direction) {
        {
            switch (direction) {
                case 'U':
                    if (AllGameConstants.MAP[y - 1][x] == 1) {
                        y = y - 1;
                    }
                    break;

                case 'D':
                    if (Game.MAP[y + 1][x] == 1) {
                        y = y + 1;
                    }
                    break;

                case 'L':
                    if (Game.MAP[y][x - 1] == 1) {
                        x = x - 1;
                    }
                    break;

                case 'R':
                    if (Game.MAP[y][x + 1] == 1) {
                        x = x + 1;
                    }
                    break;

                default:
                    break;
            }
        }
    }
}
