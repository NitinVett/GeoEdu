import javax.swing.*;
import java.awt.*;

public class GameMainMenu extends Screen {
    JButton newGame;
    JButton continue_;
    JButton tutorial;

    JButton highScores;
    JButton logout;

    public GameMainMenu(FullScreenUI frame,Screen previous,Player user) {
        super(frame,previous,user);
        newGame = new JButton();
        continue_ = new JButton();
        tutorial = new JButton();
        highScores = new JButton();
        logout = new JButton();


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


        this.add(newGame);
        this.add(continue_);
        this.add(tutorial);
        this.add(highScores);
        this.add(logout);
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
