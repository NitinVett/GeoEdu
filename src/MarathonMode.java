import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * The MarathonMode class represents the gameplay screen for the marathon mode.
 * It extends the GameplayScreen class and includes additional components such as heart icons to represent lives.
 */
public class MarathonMode extends GameplayScreen {

    /**
     * Labels used to display heart icons representing the player's remaining lives or health points.
     * 'thirdHearts' might represent the last heart in a three-heart system, 'secondHearts' the second,
     * and 'firstHeart' the first or initial heart. The ordering suggests a visual representation of health
     * from left to right or another logical sequence.
     */
    private JLabel thirdHearts, secondHearts, firstHeart;

    /**
     * A BufferedImage for a full heart icon, indicating an available life or full health point.
     * This image is used in the game's UI to visually represent the player's current state of health.
     */
    private BufferedImage fullHeartIMG;

    /**
     * A BufferedImage for an empty heart icon, indicating a lost life or depleted health point.
     * This visual cue helps players quickly assess their remaining chances or health status in the game.
     */
    private BufferedImage emptyHeartIMG;

    /**
     * A resized version of the full heart icon, adjusted to fit specific UI layout requirements
     * or design preferences. This ensures that the heart icon is displayed correctly in various screen resolutions.
     */
    private Image resizedFullHeartIMG;

    /**
     * A resized version of the empty heart icon, similarly adjusted for consistency in appearance
     * across different parts of the UI. This adaptation is crucial for maintaining a uniform look and feel.
     */
    private Image resizedEmptyHeartIMG;


    /**
     * Constructs a MarathonMode object with the specified parameters.
     *
     * @param gameTesting    The GameTesting object managing the game.
     * @param previous       The previous screen to return to.
     * @param user           The player user.
     * @param correctCountry The correct country for the current question.
     * @param incorrect1     An incorrect country for the current question.
     * @param incorrect2     An incorrect country for the current question.
     */
    public MarathonMode(GameTesting gameTesting, Screen previous, Player user, Country correctCountry, Country incorrect1, Country incorrect2) {
        super(gameTesting, previous, user, correctCountry, incorrect1, incorrect2);

        try {
            fullHeartIMG = ImageIO.read(getClass().getResourceAsStream("/fullHeart.png"));
            emptyHeartIMG = ImageIO.read(getClass().getResourceAsStream("/emptyHeart.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        thirdHearts = new JLabel();
        secondHearts = new JLabel();
        firstHeart = new JLabel();
    }

    /**
     * Sets the visual components of the screen based on the current number of lives.
     */
    public void setComponents() {
        int width = getWidth();
        int height = getHeight();

        resizedFullHeartIMG = fullHeartIMG.getScaledInstance(width / 20, height / 20, Image.SCALE_SMOOTH);
        resizedEmptyHeartIMG = emptyHeartIMG.getScaledInstance(width / 20, height / 20, Image.SCALE_SMOOTH);

        if (gameTesting.getLives() == 3) {
            thirdHearts.setIcon(new ImageIcon(resizedFullHeartIMG));
            secondHearts.setIcon(new ImageIcon(resizedFullHeartIMG));
            firstHeart.setIcon(new ImageIcon(resizedFullHeartIMG));
        } else if (gameTesting.getLives() == 2) {
            thirdHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
            secondHearts.setIcon(new ImageIcon(resizedFullHeartIMG));
            firstHeart.setIcon(new ImageIcon(resizedFullHeartIMG));
        } else if (gameTesting.getLives() == 1) {
            thirdHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
            secondHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
            firstHeart.setIcon(new ImageIcon(resizedFullHeartIMG));
        } else if (gameTesting.getLives() == 0) {
            thirdHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
            secondHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
            firstHeart.setIcon(new ImageIcon(resizedEmptyHeartIMG));
        }

        thirdHearts.setBounds(width / 6, height / 35, width / 20, height / 20);
        secondHearts.setBounds(width / 10, height / 35, width / 20, height / 20);
        firstHeart.setBounds(width / 30, height / 35, width / 20, height / 20);

        this.add(thirdHearts);
        this.add(secondHearts);
        this.add(firstHeart);
    }

    /**
     * Handles the click event for the choice buttons.
     * Reduces lives if the chosen country is incorrect.
     * Ends the game if all lives are lost.
     *
     * @param choiceButton The button representing the chosen country.
     */
    @Override
    public void clickHandling(JButton choiceButton) {
        super.clickHandling(choiceButton);
        if (!Objects.equals(choiceButton.getText(), correctCountry.getName())) {
            gameTesting.reduceLives();
            if (gameTesting.getLives() == 0) {
                gameTesting.endGame();
            } else {
                gameTesting.saveFile();
            }
        }
    }

    /**
     * Overrides the paintComponent method to update the screen components.
     *
     * @param g The Graphics object to be painted.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setComponents();
    }
}
