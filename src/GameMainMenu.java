import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class GameMainMenu extends Screen {
    JButton newGame;
    JButton continue_;
    JButton tutorial;

    JButton highScores;
    JButton logout;

    public GameMainMenu(FullScreenUI frame,Screen previous,Player user) {
        super(frame,previous,user);
        newGame = createCustomButton("New Game");
        continue_ = createCustomButton("Continue");
        tutorial = createCustomButton("Tutorial");
        highScores = createCustomButton("High Scores");
        logout = createCustomButton("Log Out");


        newGame.addActionListener(e -> newGameButton());
        newGame.setText("New Game");

        continue_.addActionListener(e -> continue_Button());

        continue_.setText("Continue");

        logout.addActionListener(e -> logOutButton());
        logout.setText("Log Out");

        highScores.addActionListener(e -> highScoreButton());
        highScores.setText("High Scores");

        tutorial.addActionListener(e -> tutorialButton());
        tutorial.setText("Tutorial");
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("resources/plank.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image scaledImage = image.getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        highScores.setIcon(new ImageIcon(scaledImage));
        logout.setIcon(new ImageIcon(scaledImage));
        highScores.setFont(loadFont("resources/Viner.ttf", 15));
        logout.setFont(loadFont("resources/Viner.ttf", 15));

        this.add(newGame);
        this.add(continue_);
        this.add(tutorial);
        this.add(highScores);
        this.add(logout);
    }
    private JButton createCustomButton(String text) {
        JButton button = new JButton(text);
        button.setContentAreaFilled(false);
        button.setFont(loadFont("resources/Viner.ttf", 28));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.CENTER);
        setButtonBackground(button, "resources/plank.png");
        return button;
    }
    private void setButtonBackground(JButton button, String imagePath) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            Image scaledImage = image.getScaledInstance(200, 100, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3;
        int mainButtonYIncrement = height / 10;
        newGame.setBounds(mainButtonX, mainButtonY, width / 5, height / 12);
        continue_.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement, width / 5, height / 12);
        tutorial.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 2, width / 5, height / 12);
        highScores.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 3, (width / 10) - 30, height / 12);
        logout.setBounds(mainButtonX + (width / 10) + 30, mainButtonY + (mainButtonYIncrement * 3), (width / 10) - 30, height / 12);
        revalidate();

    }

    public void newGameButton() {
        swapScreens(new GamemodeSelectorScreen(frame,this));
    }

    //Load game state
    public void continue_Button() {
        swapScreens(new RegisterScreen(frame,this));
    }

    public void highScoreButton() {
        swapScreens(new HighScoreScreen(frame,0,this));
    }

    public void tutorialButton() {
        TutorialScreen tutorialScreen = new TutorialScreen(frame, this, user);
        frame.addKeyListener(tutorialScreen);
        swapScreens(tutorialScreen);
    }

    public void logOutButton() {
        frame.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("xx");
        super.paintComponent(g); // Paints the background
//        Graphics2D g2D = (Graphics2D) g;
//        drawTitle(g2D);
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
