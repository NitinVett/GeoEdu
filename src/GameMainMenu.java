import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GameMainMenu extends Screen {
    JButton newGameButton;
    JButton continueButton;
    JButton tutorialButton;
    Image image;
    JButton highScoresButton;
    JButton logoutButton;
    String imagePath= "resources/plank.png";

    public GameMainMenu(FullScreenUI frame,Screen previous,Player user) {
        super(frame,previous,user);
        //custom buttons
        newGameButton = new JButton("New Game");
        continueButton = new JButton("Continue");
        tutorialButton = new JButton("Tutorial");
        highScoresButton = new JButton("High Scores");
        logoutButton = new JButton("Log Out");
        //listeners
        newGameButton.addActionListener(e -> newGameButton());
        newGameButton.setText("New Game");
        continueButton.addActionListener(e -> continue_Button());
        continueButton.setText("Continue");
        logoutButton.addActionListener(e -> logOutButton());
        logoutButton.setText("Log Out");
        highScoresButton.addActionListener(e -> highScoreButton());
        highScoresButton.setText("High Scores");
        tutorialButton.addActionListener(e -> tutorialButton());
        tutorialButton.setText("Tutorial");
        try {
            image = ImageIO.read(new File("resources/plank.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.add(newGameButton);
        this.add(continueButton);
        this.add(tutorialButton);
        this.add(highScoresButton);
        this.add(logoutButton);
    }
    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3;
        int mainButtonYIncrement = height / 10;
        Image scaledImage = image.getScaledInstance(width/5, height/12, Image.SCALE_SMOOTH);
        createButtons(newGameButton, scaledImage,width/60);
        createButtons(continueButton, scaledImage,width/60);
        createButtons(tutorialButton, scaledImage,width/60);
        createButtons(highScoresButton, scaledImage,width/60);
        createButtons(logoutButton, scaledImage,width/60);
        newGameButton.setBounds(mainButtonX, mainButtonY, width / 5, height / 12);
        continueButton.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement, width / 5, height / 12);
        tutorialButton.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 2, width / 5, height / 12);
        highScoresButton.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 3, width / 5, height / 12);
        logoutButton.setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * 4, width / 5, height / 12);
        revalidate();
    }
    public void newGameButton() {
        swapScreens(new GamemodeSelectorScreen(frame,this,user));
    }
    //Load game state
    public void continue_Button() {
        String type = user.getGameData().split(";")[1].split(":")[1];
        String mode = user.getGameData().split(";")[2].split(":")[1];
        String continent = user.getGameData().split(";")[3].split(":")[1];


        GameTesting game = new GameTesting(frame,user,mode,continent,type);
        game.loadFile(user.getGameData());
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
        super.paintComponent(g); // Paints the background
        drawTitle((Graphics2D) g);
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
