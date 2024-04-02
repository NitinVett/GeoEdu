import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ExplorationMode extends GameplayScreen {
    //buttons



    public ExplorationMode(GameTesting gameTesting, Screen previous, Player user, Country correctCountry, Country incorrect1, Country incorrect2){
        super(gameTesting, previous, user, correctCountry, incorrect1, incorrect2);

    }
    @Override
    public void showFlag(){
        flagLabel.setVisible(true);
        updateButtonPositions();
        showFlagButton.setEnabled(false);
        flagWasClicked =true;
        gameTesting.saveFile();
    }
    @Override
    public void showHints(){
        hintLabel.setVisible(true);
        updateButtonPositions();
        showHintButton.setEnabled(false);
        hintWasClicked =true;
        gameTesting.saveFile();
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
            scoreUpdateTimer = new Timer(1000, e -> gameTesting.newGame(false));
            scoreUpdateTimer.setRepeats(false);
            scoreUpdateTimer.start();
        }
        else {
            choiceButton.setBackground(Color.RED);
            choiceButton.setEnabled(false);
        }
        gameTesting.saveFile();
        repaint();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}