import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The GameplayScreen class represents the screen where gameplay occurs, displaying country information and buttons for player interaction.
 */
public class GameplayScreen extends Screen {
    //buttons
    public Timer scoreUpdateTimer;
    JButton choice1Button;
    JButton choice2Button;
    JButton choice3Button;
    JButton showFlagButton;
    JButton showHintButton;

    JLabel countryLabel, hintBackgroundLabel, hintLabel, highScoreLabel, flagLabel;
    int highScoreWinAmount = 5;
    int highScoreLossAmount = 5;
    int delay = 5000;

    Image plankIMG;

    public Country correctCountry;
    public Country incorrect1;
    public Country incorrect2;
    Player user;
    public GameTesting gameTesting;
    private Image hintBackgroundIMG;
    private Timer timer;
    public int highscore;
    boolean flagWasClicked;
    boolean hintWasClicked;

    /**
     * Constructs a GameplayScreen object with the specified parameters.
     * @param gameTesting The GameTesting object.
     * @param previous The previous Screen object.
     * @param player The Player object.
     * @param correctCountry The correct Country object.
     * @param incorrect1 The first incorrect Country object.
     * @param incorrect2 The second incorrect Country object.
     */
    public GameplayScreen(GameTesting gameTesting, Screen previous, Player player, Country correctCountry, Country incorrect1, Country incorrect2) {
        super(gameTesting.frame, previous, player);

        this.gameTesting = gameTesting;
        this.correctCountry = correctCountry;
        this.incorrect1 = incorrect1;
        this.incorrect2 = incorrect2;
        this.user = player;
        this.flagWasClicked = false;
        this.hintWasClicked = false;

        // Create buttons
        showFlagButton = new JButton("Show Flag");
        showFlagButton.addActionListener(e -> showFlag());
        showHintButton = new JButton("Show Hints");
        showHintButton.addActionListener(e -> showHints());

        //hintLabel
        hintLabel = new JLabel(correctCountry.getHints().getText());
        hintLabel.setForeground(Color.BLACK);
        hintLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
        hintLabel.setVisible(false);

        //hint background
        try {
            hintBackgroundIMG = ImageIO.read(getClass().getResourceAsStream("/hintBox.png"));
            plankIMG = ImageIO.read(getClass().getResourceAsStream("/plank.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        hintBackgroundLabel = new JLabel();
        hintBackgroundLabel.setIcon(new ImageIcon(hintBackgroundIMG));

        flagLabel = correctCountry.getFlag();
        flagLabel.setVisible(false);

        countryLabel = correctCountry.getCountryMap();

        // This handles randomization once the three countries have been received, otherwise the buttons would always indicate which answer is correct.
        int index;
        String[] countryNames = {correctCountry.getName(), incorrect1.getName(), incorrect2.getName()};
        String[] randomizedNames = new String[3];
        ArrayList<Integer> visitedIndices = new ArrayList<>();

        // Creating choice buttons
        // it puts the country names into an array, 0,1,2
        // it puts the names in another array, but the order of insertion is random.
        for (int i = 0; i < 3; i++) {
            index = ThreadLocalRandom.current().nextInt(0, 3);
            while (visitedIndices.contains(index)) {
                index = ThreadLocalRandom.current().nextInt(0, 3);
            }
            randomizedNames[i] = countryNames[index];
            visitedIndices.add(index);
        }

        choice1Button = new JButton(randomizedNames[0]);
        choice2Button = new JButton(randomizedNames[1]);
        choice3Button = new JButton(randomizedNames[2]);
        choice1Button.addActionListener(e -> setChoice1Button());
        choice2Button.addActionListener(e -> setChoice2Button());
        choice3Button.addActionListener(e -> setChoice3Button());

        //highScore
        highScoreLabel = new JLabel();
        highScoreLabel.setForeground(Color.BLACK);
        highScoreLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        int highScore = player.getHighScore();
        highScoreLabel.setText("High Score: " + highScore);

        add(highScoreLabel);
        add(flagLabel);
        add(hintLabel);
        add(hintBackgroundLabel);
        add(countryLabel);
        add(choice1Button);
        add(choice2Button);
        add(choice3Button);
        add(showFlagButton);
        add(showHintButton);
    }

    /**
     * Displays the flag of the correct country and updates the high score accordingly.
     */
    public void showFlag() {
        highscore = user.getHighScore();
        flagLabel.setVisible(true);

        // Update toggle button text
        if (!flagWasClicked) {
            highscore = highscore - 2;
            highScoreLabel.setForeground(Color.RED);
            highScoreLabel.setText("High Score: " + highscore + "  -" + 2);
            setTimer();
            user.setHighScore(highscore);
        }
        showFlagButton.setEnabled(false);
        flagWasClicked = true;
        gameTesting.saveFile();
    }

    /**
     * Displays hints about the correct country and updates the high score accordingly.
     */
    public void showHints() {
        highscore = user.getHighScore();
        hintLabel.setVisible(true);

        // Update toggle button text
        if (!hintWasClicked) {
            highscore = highscore - 2;
            highScoreLabel.setForeground(Color.BLACK);
            highScoreLabel.setText("High Score: " + highscore + "  -" + 2);
            setTimer();
            user.setHighScore(highscore);
        }
        showHintButton.setEnabled(false);
        hintWasClicked = true;
        gameTesting.saveFile();
    }

    /**
     * Disables choice buttons to prevent further interaction during game resolution.
     */
    public void disableChoiceButtons() {
        choice1Button.setEnabled(false);
        choice2Button.setEnabled(false);
        choice3Button.setEnabled(false);
        showHintButton.setEnabled(false);
        showFlagButton.setEnabled(false);
    }

    /**
     * Updates the positions and sizes of components based on the current screen size.
     */
    public void updateButtonPositions() {

        int width = getWidth();
        int height = getHeight();
        Image scaledImage = plankIMG.getScaledInstance(width/5, height/12, Image.SCALE_SMOOTH);
        createButtons(choice1Button,scaledImage,width/80);
        createButtons(choice2Button,scaledImage,width/80);
        createButtons(choice3Button,scaledImage,width/80);
        createButtons(showHintButton,scaledImage,width/80);
        createButtons(showFlagButton,scaledImage,width/80);
        // Positioning country
        countryLabel.setBounds(width/4+width/12,height/6,width/3,height/2);
        //hintBox label
        // Positioning hint label
        // Positioning flag
        if (flagLabel.isVisible()) {
            flagLabel.setBounds(width/2+width/4,height/2,width/6,height/6);;
        }
        if (hintLabel.isVisible()) {
            hintBackgroundLabel.setBounds(width/8,height/2,width/6,height/6);
            hintLabel.setBounds(width/8,height/2,width/6,height/6);;
        }
        // Positioning choice buttons
        choice1Button.setBounds(width/3+width/12,height - height/3,width/6,height/12);
        choice2Button.setBounds(width/3+width/12,height - height/4,width/6,height/12);
        choice3Button.setBounds(width/3+width/12,height - height/6,width/6,height/12);
        // Positioning high score
        highScoreLabel.setBounds(width/4+width/2,height/25,width,height/14);
        //show flag button
        showFlagButton.setBounds(width/4+width/2,height - height/4,width/6,height/12);
        //show hint button
        showHintButton.setBounds(width/8,height - height/4,width/6,height/12);
        revalidate();
    }

    /**
     * Saves the state of the GameplayScreen to a StringBuilder.
     * @param sb The StringBuilder object to append the state to.
     */
    public void saveVars(StringBuilder sb) {
        sb.append(";showFlag:").append(flagWasClicked).append(";showHint:").append(hintWasClicked);
    }

    /**
     * Handles the action when the first choice button is clicked.
     */
    public void setChoice1Button() {
        clickHandling(choice1Button);
    }

    /**
     * Handles the action when the second choice button is clicked.
     */
    public void setChoice2Button() {
        clickHandling(choice2Button);
    }

    /**
     * Handles the action when the third choice button is clicked.
     */
    public void setChoice3Button() {
        clickHandling(choice3Button);
    }

    /**
     * Handles the action when any choice button is clicked.
     * @param choiceButton The choice button that was clicked.
     */
    public void clickHandling(JButton choiceButton) {
        highscore = user.getHighScore();
        if (Objects.equals(choiceButton.getText(), correctCountry.getName())) {
            gameTesting.setCorrectGuesses(gameTesting.getCorrectGuesses()+1);
            choiceButton.setBackground(Color.GREEN);
            highscore = highscore + highScoreWinAmount;
            highScoreLabel.setForeground(Color.green);
            highScoreLabel.setText("High Score: " + highscore + "  +" + highScoreWinAmount);
            user.setHighScore(highscore);
            disableChoiceButtons();
            scoreUpdateTimer = new Timer(1000, e -> gameTesting.newGame(false    ));
            scoreUpdateTimer.setRepeats(false);
            scoreUpdateTimer.start();
        } else {
            choiceButton.setBackground(Color.RED);
            highscore = highscore - highScoreLossAmount;
            highScoreLabel.setForeground(Color.red);
            highScoreLabel.setText("High Score: " + highscore + "  -" + highScoreLossAmount);
            choiceButton.setEnabled(false);
            setTimer();
            user.setHighScore(highscore);

        }

        gameTesting.setNumGuesses(gameTesting.getNumGuesses()+1);

        gameTesting.saveFile();
    }

    /**
     * Sets a timer to revert the high score label back to normal after a delay.
     */
    public void setTimer() {
        delay = 2000;
        timer = new Timer(delay, e -> {
            highScoreLabel.setForeground(Color.BLACK);
            highScoreLabel.setText("High Score: " + highscore);
            revalidate();
            repaint();
        });
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Paints the background
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
