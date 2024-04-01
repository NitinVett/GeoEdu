import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ExplorationMode extends GameplayScreen {
    //buttons


    //Labels

    protected GameTesting gameTesting;

    //images
    private Image hintBoxIMG;

    private boolean hintWasClicked, flagWasClicked;

    public ExplorationMode(GameTesting gameTesting, Screen previous, Player user, Country correctCountry, Country incorrect1, Country incorrect2){
        super(gameTesting, previous, user, correctCountry, incorrect1, incorrect2);

    }
    @Override
    public void showFlag(){
        flagLabel.setVisible(true);
        updateButtonPositions();
        if (!flagWasClicked) {
            showFlagButton.setEnabled(false);
        }
        flagWasClicked =true;
    }
    @Override
    public void showHints(){
        hintLabel.setVisible(true);
        updateButtonPositions();
        if (!hintWasClicked) {
            showHintButton.setEnabled(false);
        }
        hintWasClicked =true;
    }
    @Override
    public void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        // Positioning country panel
        countryLabel.setBounds(width/4+width/12,height/6,width/3,height/2);
        //hintBox label
        if (hintLabel.isVisible()) {
            hintBackgroundLabel.setBounds(width / 8, height / 2, width / 6, height / 6);
            hintLabel.setBounds(width / 8, height / 2, width / 6, height / 6);
        }
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
        showHintButton.setBounds(width/8,height - height/4,width/6,height/12);
        revalidate();
    }
    public void setChoice1Button() {
        clickHandling(choice1Button);
    }
    public void setChoice2Button() {
        clickHandling(choice2Button);
    }
    public void setChoice3Button() {
        clickHandling(choice3Button);
    }
    @Override
    public void clickHandling(JButton choiceButton) {
        if (Objects.equals(choiceButton.getText(), correctCountry.getName())) {
            choiceButton.setBackground(Color.GREEN);
            scoreUpdateTimer = new Timer(1000, e -> gameTesting.startNextIteration());
            scoreUpdateTimer.setRepeats(false);
            scoreUpdateTimer.start();
        }
        else {
            choiceButton.setBackground(Color.RED);
            choiceButton.setEnabled(false);
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}