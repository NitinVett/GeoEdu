import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

/**
 * The GameplayScreen class is designed to manage and display the gameplay interface
 * where players interact with the game. It presents information about different countries
 * and allows the player to make choices through buttons. The class supports showing hints,
 * displaying the country flag, and updating the player's score based on their choices.
 */
public class GameplayScreen extends Screen {

    /** Timer for scheduling score updates and transitions between questions. */
    public Timer scoreUpdateTimer;

    /** Button for the first choice in the multiple-choice question. */
    JButton choice1Button;

    /** Button for the second choice in the multiple-choice question. */
    JButton choice2Button;

    /** Button for the third choice in the multiple-choice question. */
    JButton choice3Button;

    /** Button that, when clicked, reveals the flag associated with the correct country. */
    JButton showFlagButton;

    /** Button that, when clicked, provides hints to help identify the correct country. */
    JButton showHintButton;

    /** Label displaying the country information or question related to the current game round. */
    JLabel countryLabel;

    /** Label used as a background for hint text, enhancing UI aesthetics. */
    JLabel hintBackgroundLabel;

    /** Label for displaying hints about the correct country, aimed at assisting the player. */
    JLabel hintLabel;

    /** Label showing the player's current high score. */
    JLabel highScoreLabel;

    /** Label used to display the flag of the correct country upon player request. */
    JLabel flagLabel;

    /** The amount of points added to the player's score for a correct answer. */
    int highScoreWinAmount = 5;

    /** The amount of points deducted from the player's score for an incorrect answer. */
    int highScoreLossAmount = 5;

    /** Delay in milliseconds before executing certain actions, like updating the score or showing the next question. */
    int delay = 5000;

    /** Image used for button backgrounds or other decorative elements within the game. */
    Image plankIMG;

    /** The country that is the correct answer for the current question. */
    public Country correctCountry;

    /** The first incorrect country option for the current question. */
    public Country incorrect1;

    /** The second incorrect country option for the current question. */
    public Country incorrect2;

    /** The player object, containing information such as the player's name and score. */
    Player user;

    /** The game controller handling game logic, transitions between screens, and saving game progress. */
    public GameTesting gameTesting;

    /** Background image for displaying hints, usually a stylized box or area where hint text appears. */
    private Image hintBackgroundIMG;

    /** A secondary timer used for UI updates, like resetting the high score label color after a change. */
    private Timer timer;

    /** The current score of the player, updated throughout the game based on correct or incorrect answers. */
    public int highscore;

    /** Flag indicating whether the flag of the correct country has been revealed to the player. */
    boolean flagWasClicked;

    /** Flag indicating whether hints for identifying the correct country have been shown to the player. */
    boolean hintWasClicked;


    /**
     * Constructs a GameplayScreen with specified game elements and initial settings.
     *
     * @param gameTesting The main controller for the game logic and progression.
     * @param previous The screen that was displayed before transitioning to the GameplayScreen.
     * @param player The player object representing the current game user.
     * @param correctCountry The country that is the correct choice for the current game question.
     * @param incorrect1 The first incorrect country choice.
     * @param incorrect2 The second incorrect country choice.
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
     * Shows the flag of the correct country and updates the player's high score based on this action.
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
     * Displays hints related to the correct country and adjusts the player's high score accordingly.
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
     * Disables the choice buttons to prevent further actions after a choice has been made.
     */
    public void disableChoiceButtons() {
        choice1Button.setEnabled(false);
        choice2Button.setEnabled(false);
        choice3Button.setEnabled(false);
        showHintButton.setEnabled(false);
        showFlagButton.setEnabled(false);
    }

    /**
     * Updates the positions and sizes of UI components based on the screen size.
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
     * Handles the logic for when a choice button is selected by the player.
     *
     * @param choiceButton The JButton representing the selected choice.
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

    /**
     * Custom painting method for the GameplayScreen. Calls the superclass's paintComponent to ensure
     * background rendering and updates component positions dynamically.
     *
     * @param g The Graphics object used for drawing operations.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Paints the background
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
