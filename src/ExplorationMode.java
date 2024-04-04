import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * The ExplorationMode class extends GameplayScreen to provide a specific gameplay experience
 * focused on exploring and learning about different countries. It includes mechanisms to display
 * flags, hints, and manage user interactions with multiple-choice questions.
 */
public class ExplorationMode extends GameplayScreen {

    /**
     * JButton for exit button
     */
    private JButton exitButton;
    /**
     * Constructs an ExplorationMode object with the necessary gameplay elements and initial setup.
     *
     * @param gameTesting    The main game testing object that controls the flow of the game.
     * @param previous       The previous screen from which the user navigated to ExplorationMode.
     * @param user           The current player object containing user data.
     * @param correctCountry The correct country option for the current question.
     * @param incorrect1     The first incorrect country option.
     * @param incorrect2     The second incorrect country option.
     */
    public ExplorationMode(GameTesting gameTesting, Screen previous, Player user, Country correctCountry, Country incorrect1, Country incorrect2) {
        super(gameTesting, previous, user, correctCountry, incorrect1, incorrect2);

    }

    /**
     * Displays the country's flag to the user as part of the exploration mode. Overrides
     * the showFlag method from the parent class to include additional logic specific to this mode.
     */
    @Override
    public void showFlag() {
        flagLabel.setVisible(true);
        updateButtonPositions();
        showFlagButton.setEnabled(false);
        flagWasClicked = true;
        gameTesting.saveFile();
    }

    /**
     * Displays hints related to the correct country option. This method enhances user
     * engagement by providing clues and supports the educational aspect of the gameplay.
     */
    @Override
    public void showHints() {
        hintLabel.setVisible(true);
        updateButtonPositions();
        showHintButton.setEnabled(false);
        hintWasClicked = true;
        gameTesting.saveFile();
    }

    /**
     * Handles click events for choice 1 button.
     */
    public void setChoice1Button() {
        clickHandling(choice1Button);
    }

    /**
     * Handles click events for choice 2 button.
     */
    public void setChoice2Button() {
        clickHandling(choice2Button);
    }

    /**
     * Handles click events for choice 3 button.
     */
    public void setChoice3Button() {
        clickHandling(choice3Button);
    }

    // Method comments for setChoice1Button, setChoice2Button, and setChoice3Button
    // can follow a similar pattern, noting that they setup click handlers for each choice.

    /**
     * Handles the logic for when a user selects a choice. It checks whether the choice is correct
     * and updates the UI to reflect the result. Correct choices proceed to a new game, while incorrect
     * choices disable the button and mark it as wrong.
     *
     * @param choiceButton The JButton object representing the user-selected choice.
     */
    @Override
    public void clickHandling(JButton choiceButton) {
        if (Objects.equals(choiceButton.getText(), correctCountry.getName())) {
            choiceButton.setBackground(Color.GREEN);
            scoreUpdateTimer = new Timer(1000, e -> gameTesting.newGame(false));
            scoreUpdateTimer.setRepeats(false);
            scoreUpdateTimer.start();
        } else {
            choiceButton.setBackground(Color.RED);
            choiceButton.setEnabled(false);
        }
        gameTesting.saveFile();
        repaint();
    }

    /**
     * Custom painting method for the ExplorationMode screen. It is responsible for drawing the exit
     * button and positioning it on the screen, in addition to invoking the superclass's painting logic.
     *
     * @param g The Graphics object used for drawing operations.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        exitButton = new JButton("Exit Exploration");
        Image image = new ImageIcon(getClass().getResource("/plank.png")).getImage();
        createButtons(exitButton,image,getWidth()/100);
        exitButton.addActionListener(e -> exitExploration()); // Placeholder for the actual exit logic

        // Set button size and position
        exitButton.setSize(getWidth()/10, getHeight()/20);

        // Calculate position for left middle alignment
        int xPosition = this.getWidth()/20; // 10 pixels from the left edge, adjust as needed
        int yPosition = (this.getHeight() - exitButton.getHeight()) / 8 ; // Middle of the component

        exitButton.setLocation(xPosition, yPosition);


        // Add the exit button to the panel
        add(exitButton);
    }

    /**
     * Defines the action taken when the user decides to exit exploration mode. This typically involves
     * navigating back to the main game menu or a different screen within the application.
     */
    public void exitExploration(){
        swapScreens(new GameMainMenu(frame,this,user));
    }
}
