import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ExplorationMode extends GameplayScreen {
    //buttons
    private JButton choice1Button;
    private JButton choice2Button;
    private JButton choice3Button;
    private JButton showFlagButton;

    //Labels
    private JLabel countryLabel, hintBoxLabel,hintLabel,highScoreLabel, flagLabel;

    public Country correctCountry;
    private Country incorrect1;
    private Country incorrect2;
    private Player user;
    protected GameTesting gameTesting;

    //images
    private Image hintBoxIMG;

    public ExplorationMode(GameTesting gameTesting, Screen previous, Player user, Country correctCountry, Country incorrect1, Country incorrect2) throws IOException {
        super(gameTesting, previous, user, correctCountry, incorrect1, incorrect2);

    }

    @Override
    public void showFlag(){
        flagLabel.setVisible(true);
        updateButtonPositions();
    }
    @Override
    public void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        // Positioning country panel
        countryLabel.setBounds(width/4+width/12,height/6,width/3,height/2);
        //hintBox label
        hintBoxLabel.setBounds(width/25,height/2+height/5,width/2,height/5);
        // Positioning hint label
        hintLabel.setBounds(width/25,height/2+height/5,width/3,height/5);
        // Positioning flag panel
        if (flagLabel.isVisible()) {
            flagLabel.setBounds(width/2+width/4,height/2,width/6,height/6);;
        }
        // Positioning choice buttons
        choice1Button.setBounds(width/3+width/12,height - height/3,width/6,height/12);
        choice2Button.setBounds(width/3+width/12,height - height/4,width/6,height/12);
        choice3Button.setBounds(width/3+width/12,height - height/6,width/6,height/12);
        // Positioning high score panel
        //show flag button
        showFlagButton.setBounds(width/4+width/2,height - height/4,width/6,height/12);
        revalidate();
    }
    @Override
    public void clickHandling(JButton choiceButton) {
        if (Objects.equals(choiceButton.getText(), correctCountry.getName())) {
            gameTesting.startNextIteration();
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
