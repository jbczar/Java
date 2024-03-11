package PacMan.GameCode;

import PacMan.GameCode.GameConstants.AllGameConstants;
import PacMan.GameCode.GameConstants.GameColors;
import PacMan.GameCode.Ghosts.ChasingGhost;
import PacMan.GameCode.Ghosts.CopyGhost;
import PacMan.GameCode.Ghosts.RandomGhost;
import PacMan.GameCode.Ghosts.WaitingGhost;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;

public class Game extends AllGameConstants implements ActionListener {

    private PacMan pacMan;
    private  ChasingGhost chasingGhost;
    private RandomGhost randomGhost;
    private CopyGhost copyGhost;
    private WaitingGhost waitingGhost;
    private MainPanel mainPanel;
    private JLabel scoreLabel;
    private JPanel scorePanel;

    private JFrame frame;
    private Timer timer;
    private int highestScore, score, sumScore, lives;
    private boolean isRunning;
    private char direction;


    public Game() throws IOException {
        sumScore = 0;
        lives = LIVES;
        FileReader file = new FileReader("src/PacMan/HighestScore.txt");
        BufferedReader buffereReader = new BufferedReader(file);
        String line = buffereReader.readLine();
        highestScore = Integer.parseInt(line);
        file.close();
    }


    private void startGame() throws IOException {
        isRunning = true;
        direction = 'R';
        pacMan = new PacMan();
        chasingGhost = new ChasingGhost();
        randomGhost = new RandomGhost();
        copyGhost = new CopyGhost();
        waitingGhost = new WaitingGhost();
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void setGameplay() {

        isRunning = false;
        scoreLabel = new JLabel();
        /*
        scoreLabel.setText("Score: " + Integer.toString(score)  + "<br/>" +
                "  Your sum score: " + Integer.toString(sumScore) + "\n" +
                "  Highest score: " + Integer.toString(highestScore) + "\n" +
                "   Lives left: " + Integer.toString(lives));
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 10));
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setBackground(GameColors.SCORE_BOARD_COLOR);
        scoreLabel.setOpaque(true);
        //scoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

         */

        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setForeground(Color.black);

        scoreLabel.setText("<html><div style='font-size: 16pt;'>Score: " + Integer.toString(score) +
                "<br/>Your sum score: " + Integer.toString(sumScore) +
                "<br/>Highest score: " + Integer.toString(highestScore) +
                "<br/>Lives left: " + Integer.toString(lives) +
                "</html>");




        scorePanel = new JPanel();
        scorePanel.setPreferredSize(new Dimension(200, 0));
        scorePanel.setBackground(GameColors.SCORE_BOARD_COLOR);
        scorePanel.add(scoreLabel);
        mainPanel = new MainPanel();
        mainPanel.setPreferredSize(DIMENSION);
        mainPanel.setBackground(GameColors.BACKGROUND_COLOR);

        frame = new JFrame();
        frame.setFocusable(true);
        frame.addKeyListener(new MyKeyAdapter());

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    isRunning = !isRunning;
                    if (isRunning) {
                        timer.start();
                    } else {
                        timer.stop();
                    }
                }
            }
        });


        frame.setTitle("Pac-Mac");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(DIMENSION.width + 200, DIMENSION.height + NUM_OF_Y_BLOCKS + 3);
        frame.setLayout(new BorderLayout());
        frame.add(scorePanel, BorderLayout.EAST);
        frame.add(mainPanel, BorderLayout.WEST);
        frame.setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (isRunning) {
            pacMan.movePacMan(direction);

            chasingGhost
                    .move(pacMan.getPacManPositionX(), pacMan.getPacManPositionY());
            waitingGhost
                    .move(pacMan.getPacManPositionX(), pacMan.getPacManPositionY());
            randomGhost
                    .move(pacMan.getPacManPositionX(), pacMan.getPacManPositionY());
            copyGhost
                    .move(pacMan.getPacManPositionX(), pacMan.getPacManPositionY());

            if (LOCATION_0F_POINTS[pacMan.getPacManPositionY()][pacMan.getPacManPositionX()] == 1) {
                score++;
                sumScore++;
                LOCATION_0F_POINTS[pacMan.getPacManPositionY()][pacMan.getPacManPositionX()] = 0;
/*
nie wychodzi tak jakbym chciał
                scoreLabel.setText("Score: " + Integer.toString(score)  + "<br/>" +
                        "  Your sum score: " + Integer.toString(sumScore) + "\n" +
                        "  Highest score: " + Integer.toString(highestScore) + "\n" +
                        "   Lives left: " + Integer.toString(lives));
                scoreLabel.setFont(new Font("Arial", Font.BOLD, 10));
                scoreLabel.setForeground(Color.BLACK);
                scoreLabel.setBackground(GameColors.SCORE_BOARD_COLOR);
                scoreLabel.setOpaque(true);
                //scoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

 */

                scoreLabel.setText("<html><html><div style='font-size: 16pt;'>Score: " + Integer.toString(score) +
                        "<br/>Your sum score: " + Integer.toString(sumScore) +
                        "<br/>Highest score: " + Integer.toString(highestScore) +
                        "<br/>Lives left: " + Integer.toString(lives) +
                        "</html>");
            }
            if (checkIfCaught()) {
                score = 0;
                lives--;
                isRunning = false;
                DELAY = Integer.MAX_VALUE;
                LOCATION_0F_POINTS = Arrays.stream(MAP).map(int[]::clone).toArray(int[][]::new);

                if (lives == 0) {
                    if (sumScore > highestScore) {
                        highestScore = sumScore;
                        FileWriter file;
                        try {
                            file = new FileWriter("src/PacMan/HighestScore.txt");
                            file.write(Integer.toString(sumScore));
                            file.close();
                        } catch (IOException e1) {

                        }
                    }
                }
            }
            mainPanel.repaint();
        }
    }


    private boolean checkIfCaught() {
        if (pacMan.getPacManPositionX()
                == chasingGhost.getGhostPositionX()
                && pacMan.getPacManPositionY()
                == chasingGhost.getGhostPositionY()
                || pacMan.getPacManPositionX()
                == randomGhost.getGhostPositionX()
                && pacMan.getPacManPositionY()
                == randomGhost.getGhostPositionY()
                || pacMan.getPacManPositionX()
                == copyGhost.getGhostPositionX()
                && pacMan.getPacManPositionY()
                == chasingGhost.getGhostPositionY()
                || pacMan.getPacManPositionX()
                == waitingGhost.getGhostPositionX()
                && pacMan.getPacManPositionY()
                == waitingGhost.getGhostPositionY())
        {
            return true;
        }
        return false;
    }

   //start i kierunek ruchu
    private class MyKeyAdapter extends KeyAdapter {

        @Override
        public void keyPressed(KeyEvent e) {
            if (!isRunning && e.getKeyChar() == ' ') {
                try {
                    startGame();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            } else if (isRunning)
            {
                if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyChar() == 'w') {
                    direction = 'U';
                }
                else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyChar() == 's') {
                    direction = 'D';
                }
                else if (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyChar() == 'a') {
                    direction = 'L';
                }
                else if (e.getKeyCode() == KeyEvent.VK_RIGHT || e.getKeyChar() == 'd') {
                    direction = 'R';
                }
            }
        }
    }


    private class MainPanel extends JPanel {

        @Override
        public void paint(Graphics graphics) {
            super.paintComponent(graphics);

            BufferedImage layoutImage;
            try {
                layoutImage = ImageIO.read(new File("src/PacMan/Resources/Photos/pacman_layout.png"));
                Image newImage = layoutImage.getScaledInstance(DIMENSION.width, DIMENSION.height, Image.SCALE_DEFAULT);
                graphics.drawImage(newImage, 0, 0, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!isRunning) {
                graphics.setFont(new Font("Dialog", Font.BOLD, 35));
                graphics.setColor(Color.ORANGE);

                if (lives == 0) {

                    graphics.drawString("Your score is : " + Integer.toString(sumScore),
                            SCREEN_WIDTH / 2 - 250,
                            SCREEN_HEIGHT / 2 - 100);

                    graphics.drawString("TO PLAY AGAIN PRESS SPACE",
                            SCREEN_WIDTH / 2 - 250,
                            SCREEN_HEIGHT / 2);

                    lives = 3;
                    sumScore = 0;
                    score = 0;


                } else {
                    graphics.drawString("PRESS SPACE TO START NEW ROUND",
                            SCREEN_WIDTH / 2 - 350,
                            SCREEN_HEIGHT / 3);
                    graphics.drawString("to freeze the game press enter",
                            SCREEN_WIDTH / 2 - 250,
                            SCREEN_HEIGHT / 2);
                }
            }
            else {
                graphics.setColor(Color.ORANGE);

                for (int i = 0; i < LOCATION_0F_POINTS.length; i++) {
                    for (int j = 0; j < LOCATION_0F_POINTS[i].length; j++) {
                        if (LOCATION_0F_POINTS[i][j] == 1) {
                            graphics.fillOval(
                                    BLOCK_SIZE * j + 8,
                                    BLOCK_SIZE * i + 8,
                                    BLOCK_SIZE / 2,
                                    BLOCK_SIZE / 2);
                        }
                    }
                }

                graphics.drawImage(pacMan.getPacManImage(),
                        pacMan.getPacManPositionX() * BLOCK_SIZE,
                        pacMan.getPacManPositionY() * BLOCK_SIZE,
                        this);

                graphics.drawImage(chasingGhost.getGhostImage(),
                        chasingGhost.getGhostPositionX() * BLOCK_SIZE,
                        chasingGhost.getGhostPositionY() * BLOCK_SIZE,
                        this);

                graphics.drawImage(randomGhost.getGhostImage(),
                        randomGhost.getGhostPositionX() * BLOCK_SIZE,
                        randomGhost.getGhostPositionY() * BLOCK_SIZE,
                        this);

                graphics.drawImage(copyGhost.getGhostImage(),
                        copyGhost.getGhostPositionX() * BLOCK_SIZE,
                        copyGhost.getGhostPositionY() * BLOCK_SIZE,
                        this);

                graphics.drawImage(waitingGhost.getGhostImage(),
                        waitingGhost.getGhostPositionX() * BLOCK_SIZE,
                        waitingGhost.getGhostPositionY() * BLOCK_SIZE,
                        this);

            }
        }
    }
}
