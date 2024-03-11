package PacMan.GameCode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class GameMainMenu {
    private Game game;
    private JFrame frame;
    private JMenuBar menuBar;
    private JMenuItem newGameItem;
    private JMenuItem highScoreItem;
    private JMenuItem endGameItem;
    private JButton startGameButton;
    private JButton highScoreButton;
    private JButton endGameButton;

    public void start() throws InterruptedException, IOException {
        game = new Game();

        JFrame frame = new JFrame("Pac-Man");
        JMenuBar menuBar = new JMenuBar();


        JMenu gameMenu = new JMenu("Game");
        gameMenu.setForeground(Color.WHITE);
        gameMenu.setFont(new Font("Arial", Font.BOLD, 14));


        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem highScoreItem = new JMenuItem("High Score");
        JMenuItem endGameItem = new JMenuItem("End Game");
        newGameItem.setForeground(Color.WHITE);
        highScoreItem.setForeground(Color.WHITE);
        endGameItem.setForeground(Color.WHITE);
        newGameItem.setBackground(new Color(65, 105, 225));
        highScoreItem.setBackground(new Color(65, 105, 225));
        endGameItem.setBackground(new Color(65, 105, 225));
        newGameItem.setFont(new Font("Arial", Font.BOLD, 14));
        highScoreItem.setFont(new Font("Arial", Font.BOLD, 14));
        endGameItem.setFont(new Font("Arial", Font.BOLD, 14));


        JButton startGameButton = new JButton("Start Game");
        startGameButton.setPreferredSize(new Dimension(200, 50));
        startGameButton.setBackground(new Color(65, 105, 225));
        startGameButton.setForeground(Color.WHITE);
        startGameButton.setFocusPainted(false);
        startGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        startGameButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                game.setGameplay();
                //System.out.println("Koniec Gry");
            }
        });


        JButton highScoreButton = new JButton("High Score");
        highScoreButton.setPreferredSize(new Dimension(200, 50));
        highScoreButton.setBackground(new Color(65, 105, 225));
        highScoreButton.setForeground(Color.WHITE);
        highScoreButton.setFocusPainted(false);
        highScoreButton.setFont(new Font("Arial", Font.BOLD, 18));
        highScoreButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // TODO: Display highest score
            }
        });

        endGameButton = new JButton("End Game");
        endGameButton.setPreferredSize(new Dimension(200, 50));
        endGameButton.setBackground(new Color(65, 105, 225));
        endGameButton.setForeground(Color.WHITE);
        endGameButton.setFocusPainted(false);
        endGameButton.setFont(new Font("Arial", Font.BOLD, 18));
        endGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Terminate the game and close the program
                System.exit(0);
            }
        });

/*
        JLabel pacmanLabel = new JLabel("PAC-MAN");
        pacmanLabel.setForeground(new Color(255, 255, 0));
        pacmanLabel.setFont(new Font("Arial", Font.BOLD, 60));
        pacmanLabel.setHorizontalAlignment(SwingConstants.CENTER);
        pacmanLabel.setVerticalAlignment(SwingConstants.CENTER);

 */
        ImageIcon logoIcon = new ImageIcon("src/PacMan/Resources/Photos/START_LOGO.png");
        Image resizedLogoImage = logoIcon.getImage().getScaledInstance(600, 300, Image.SCALE_SMOOTH);
        ImageIcon resizedLogoIcon = new ImageIcon(resizedLogoImage);

        JLabel logoLabel = new JLabel(resizedLogoIcon);



        // Create the main panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(0, 0, 0));

        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(new Color(0, 0, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        topPanel.add(logoLabel, gbc);
/*
        // Create the top panel
        JPanel topPanel = new JPanel(new GridBagLayout());
        topPanel.setBackground(new Color(0, 0, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 0, 20, 0);
        topPanel.add(pacmanLabel, gbc);

 */


        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 30));
        centerPanel.setBackground(new Color(0, 0, 0));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 50));
        centerPanel.add(startGameButton);
        centerPanel.add(highScoreButton);
        centerPanel.add(endGameButton);


        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);


        gameMenu.add(newGameItem);
        gameMenu.add(highScoreItem);
        gameMenu.add(endGameItem);

        // Add the Game menu to the menu bar
        menuBar.add(gameMenu);

        // Add the menu bar to the frame
        frame.setJMenuBar(menuBar);

        // Add the main panel to the frame
        frame.getContentPane().add(mainPanel);

        // Set the frame properties
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
