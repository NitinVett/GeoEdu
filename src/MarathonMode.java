import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MarathonMode extends GameplayScreen {
    private int numLives;
    private JLabel thirdHearts, secondHearts, firstHeart;
    private BufferedImage fullHeartIMG, emptyHeartIMG;
    private Image resizedFullHeartIMG, resizedEmptyHeartIMG;

    public MarathonMode(GameTesting gameTesting, Screen previous, Player user, Country correctCountry, Country incorrect1, Country incorrect2, int Lives) throws IOException {
        super(gameTesting, previous, user, correctCountry, incorrect1, incorrect2);
        this.numLives = Lives;
        //cal click handling
        fullHeartIMG = ImageIO.read(new File("fullHeart.png"));
        emptyHeartIMG = ImageIO.read(new File("emptyHeart.png"));

        thirdHearts = new JLabel();
        secondHearts = new JLabel();
        firstHeart = new JLabel();
    }

    public void setComponents() {
        int width = getWidth();
        int height = getHeight();
        resizedFullHeartIMG = fullHeartIMG.getScaledInstance(width / 20, height / 20, Image.SCALE_SMOOTH);
        resizedEmptyHeartIMG = emptyHeartIMG.getScaledInstance(width / 20, height / 20, Image.SCALE_SMOOTH);
        if (numLives == 3){
            thirdHearts.setIcon(new ImageIcon(resizedFullHeartIMG));
            secondHearts.setIcon(new ImageIcon(resizedFullHeartIMG));
            firstHeart.setIcon(new ImageIcon(resizedFullHeartIMG));
        } else if (numLives == 2) {
            thirdHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
            secondHearts.setIcon(new ImageIcon(resizedFullHeartIMG));
            firstHeart.setIcon(new ImageIcon(resizedFullHeartIMG));
        } else if (numLives ==1) {
            thirdHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
            secondHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
            firstHeart.setIcon(new ImageIcon(resizedFullHeartIMG));
        } else if (numLives ==0 ) {
            thirdHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
            secondHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
            firstHeart.setIcon(new ImageIcon(resizedEmptyHeartIMG));
        }
//        thirdHearts.setIcon(new ImageIcon(resizedFullHeartIMG));
//        secondHearts.setIcon(new ImageIcon(resizedFullHeartIMG));
//        firstHeart.setIcon(new ImageIcon(resizedFullHeartIMG));
        //resize
        thirdHearts.setBounds(width / 2 + width / 35, height / 8, width / 20, height / 20);
        secondHearts.setBounds(width / 2 - width / 50, height / 8, width / 20, height / 20);
        firstHeart.setBounds(width / 3 + width / 10, height / 8, width / 20, height / 20);
        this.add(thirdHearts);
        this.add(secondHearts);
        this.add(firstHeart);
    }

    //lives reset after correct answer not good
    @Override
    public void clickHandling(JButton choiceButton) {
        super.clickHandling(choiceButton);
        if (!Objects.equals(choiceButton.getText(), correctCountry.getName())) {
            numLives = numLives - 1;
            gameTesting.reduceLives();
            if (numLives == 2) {
                //thirdHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
//                revalidate();
                //repaint();
                System.out.println("2");
            }
            if (numLives == 1) {
                //secondHearts.setIcon(new ImageIcon(resizedEmptyHeartIMG));
                //System.out.println("1");
            }
            //maybe ad a timer
            if (numLives == 0) {
                //firstHeart.setIcon(new ImageIcon(resizedEmptyHeartIMG));
                swapScreens(new StatScreen(frame, this));
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setComponents();
    }
}
