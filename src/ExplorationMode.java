import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * ExplorationMode represents a subclass of GameplayScreen specifically designed for exploration mode.
 */
public class ExplorationMode extends GameplayScreen {

    private JButton exitButton;
    /**
     * Constructs an ExplorationMode object.
     * @param gameTesting The GameTesting object.
     * @param previous The previous Screen object.
     * @param user The Player object.
     * @param correctCountry The correct Country object.
     * @param incorrect1 The first incorrect Country object.
     * @param incorrect2 The second incorrect Country object.
     */
    public ExplorationMode(GameTesting gameTesting, Screen previous, Player user, Country correctCountry, Country incorrect1, Country incorrect2) {
        super(gameTesting, previous, user, correctCountry, incorrect1, incorrect2);

    }

    /**
     * Displays the flag.
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
     * Displays the hints.
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

    /**
     * Handles the click event for a choice button.
     * @param choiceButton The JButton object representing the choice button.
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
     * Paints the component.
     * @param g The Graphics object.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        exitButton = new JButton("Exit Exploration");
        createButtons(exitButton,new ImageIcon("resources/plank.png").getImage(),getWidth()/100);
        exitButton.addActionListener(e -> exitExploration()); // Placeholder for the actual exit logic

        // Set button size and position
        exitButton.setSize(200, 50); // You might need to adjust the size based on your UI design

        // Calculate position for left middle alignment
        int xPosition = this.getWidth()/10; // 10 pixels from the left edge, adjust as needed
        int yPosition = (this.getHeight() - exitButton.getHeight()) / 4 ; // Middle of the component

        exitButton.setLocation(xPosition, yPosition);


        // Add the exit button to the panel
        add(exitButton);
    }
    public void exitExploration(){
        swapScreens(new GameMainMenu(frame,this,user));
    }
}
