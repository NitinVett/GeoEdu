import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MarathonMode extends GameplayScreen {

    private JLabel thirdHearts, secondHearts, firstHeart;
    private BufferedImage fullHeartIMG, emptyHeartIMG;
    private Image resizedFullHeartIMG, resizedEmptyHeartIMG;

    public MarathonMode(GameTesting gameTesting, Screen previous, Player user, Country correctCountry, Country incorrect1, Country incorrect2){
        super(gameTesting, previous, user, correctCountry, incorrect1, incorrect2);

        //cal click handling
        try {
            fullHeartIMG = ImageIO.read(new File("fullHeart.png"));
            emptyHeartIMG = ImageIO.read(new File("emptyHeart.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        thirdHearts = new JLabel();
        secondHearts = new JLabel();
        firstHeart = new JLabel();
    }

    public void setComponents() {
        int width = getWidth();
        int height = getHeight();

        resizedFullHeartIMG = fullHeartIMG.getScaledInstance(width / 20, height / 20, Image.SCALE_SMOOTH);
        resizedEmptyHeartIMG = emptyHeartIMG.getScaledInstance(width / 20, height / 20, Image.SCALE_SMOOTH);
        if (gameTesting.getLives() == 3){
            thirdHearts.setIcon(new ImageIcon(resizedFullHeartIMG));
            secondHearts.setIcon(new ImageIcon(resizedFullHeartIMG));
            firstHeart.setIcon(new ImageIcon(resizedFullHeartIMG));
        } else if (gameTesting.getLives() == 2) {
            thirdHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
            secondHearts.setIcon(new ImageIcon(resizedFullHeartIMG));
            firstHeart.setIcon(new ImageIcon(resizedFullHeartIMG));
        } else if (gameTesting.getLives() ==1) {
            thirdHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
            secondHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
            firstHeart.setIcon(new ImageIcon(resizedFullHeartIMG));
        } else if (gameTesting.getLives() ==0 ) {
            thirdHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
            secondHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
            firstHeart.setIcon(new ImageIcon(resizedEmptyHeartIMG));
        }
        thirdHearts.setBounds(width /6, height / 35, width / 20, height / 20);
        secondHearts.setBounds(width / 10, height / 35, width / 20, height / 20);
        firstHeart.setBounds(width / 30, height / 35, width / 20, height / 20);
        this.add(thirdHearts);
        this.add(secondHearts);
        this.add(firstHeart);
    }

    //lives reset after correct answer not good
    @Override
    public void clickHandling(JButton choiceButton) {
        super.clickHandling(choiceButton);
        if (!Objects.equals(choiceButton.getText(), correctCountry.getName())) {
            gameTesting.reduceLives();
            if (gameTesting.getLives() == 0) {
                swapScreens(new StatScreen(frame, this,user));
            }
        }
        gameTesting.saveFile();
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setComponents();
    }
}
