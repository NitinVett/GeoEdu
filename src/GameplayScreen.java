import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;



// ACtually drawing on the screen, it receives three countries, one correct and two incorrect, it receives an instance of
// the class that called it, it has action listeners,
public class GameplayScreen extends Screen {

    private JButton choice1Button;
    private JButton choice2Button;
    private JButton choice3Button;
    private JPanel flagPanel;

    JButton escButton;
    private JLabel countryLabel,hintBox,hintLabel;
    private JLabel highScoreLabel;
    int guesses = 0;
    int correctGuesses = 0;
    int accuracyRate = 0;

    public Country correctCountry;
    private Country incorrect1;
    private Country incorrect2;
    private Player user;
    private GameTesting gameTesting;
    private JToggleButton toggleButton;
    private Image hintBoxIMG;



    // Three country objects, through them you can pull all the information necessary (flag,mao,hints)
    // When we feed this program, we give it one correct country (this is the country whose data we load )
    // The two incorrect countries (we just use them to pull the names of the country)


    public GameplayScreen(GameTesting gameTesting, Screen previous, Player player, Country correctCountry, Country incorrect1, Country incorrect2) throws IOException {
        super(gameTesting, previous);
        this.gameTesting = gameTesting;
        this.correctCountry = correctCountry;
        this.incorrect1 = incorrect1;
        this.incorrect2 = incorrect2;
        this.user = player;

        // Create toggle button
        toggleButton = new JToggleButton("Show Flag");
        toggleButton.addActionListener(e -> toggleFlag());

        //Escape button
        escButton = new JButton();
        escButton.addActionListener(e -> logOutButton());
        BufferedImage escIcon = ImageIO.read(new File("src/escape.png"));
        Image resizedEsc = escIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        escButton.setIcon(new ImageIcon(resizedEsc));
        this.add(escButton);


        //hint
        hintLabel = new JLabel(correctCountry.getHints().getText());
        hintLabel.setForeground(Color.BLACK);
        hintLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
        this.add(hintLabel);

        //hint background
        hintBoxIMG = ImageIO.read(new File("hintBox.png"));
        hintBox = new JLabel();
        hintBox.setIcon(new ImageIcon(hintBoxIMG));
        this.add(hintBox);


        JLabel flagLabel = correctCountry.getFlag();
        flagPanel = new JPanel(new BorderLayout());
        flagPanel.setOpaque(false);
        flagPanel.add(flagLabel);
        flagPanel.setVisible(false);
        countryLabel = correctCountry.getCountryMap();

        // This handles randomization once the three countries have been received, otherwie the buttons would always
        // indicate which answer is correct.
        int index;
        String[] countryNames = {correctCountry.getName(), incorrect1.getName(), incorrect2.getName()};
        String[] randomizedNames = new String[3];
        ArrayList<Integer> visitedIndices = new ArrayList<>();

        // Creating choice buttons
        // it puts the country names into an array, 0,1,2
        // it puts the names in another array, but the order of insertion is rando,.
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
        //highScoreLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        this.add(highScoreLabel);
        this.add(flagPanel);
        this.add(countryLabel);
        this.add(choice1Button);
        this.add(choice2Button);
        this.add(choice3Button);
        this.add(toggleButton);
    }

    private void toggleFlag() {
        boolean showFlag = toggleButton.isSelected();
        flagPanel.setVisible(showFlag);
        updateButtonPositions();
        // Update toggle button text
        if (showFlag) {
            toggleButton.setText("Hide Flag");
        } else {
            toggleButton.setText("Show Flag");
        }
    }


    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();
        // Positioning country panel
        countryLabel.setBounds(width/4+width/12,height/6,width/3,height/2);
        //hintBox label
        hintBox.setBounds(width/25,height/3,width/4,height/5);

        // Positioning hint label
        hintLabel.setBounds(width/25,height/3,width/4,height/5);
        // Positioning flag panel
        if (flagPanel.isVisible()) {
            flagPanel.setBounds(width/2+width/4,height/2,width/6,height/6);;
        }
        // Positioning choice buttons
        choice1Button.setBounds(width/3+width/12,height - height/3,width/6,height/12);
        choice2Button.setBounds(width/3+width/12,height - height/4,width/6,height/12);
        choice3Button.setBounds(width/3+width/12,height - height/6,width/6,height/12);
        // Positioning high score panel
        highScoreLabel.setBounds(width/2+width/6,height/25,width/10,height/14);
        // Positioning log out
        escButton.setBounds(width / 30, height/22, width / 31, height / 19);
        //show flag button
        toggleButton.setBounds(width/4+width/2,height - height/4,width/6,height/12);
        revalidate();
    }

    public void logOutButton() {
        frame.dispose();
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

    public void clickHandling(JButton choiceButton) {
        int highscore = user.getHighScore();
        guesses += 1;
        if (Objects.equals(choiceButton.getText(), correctCountry.getName())) {
            correctGuesses += 1;
            highscore = highscore + 5;
            highScoreLabel.setText("High Score: " + highscore);


            //Returns exceution, this class only deals with three countries, 50 countries
            gameTesting.startNextIteration();
            // Go back to where it was called
        } else {
            highscore = highscore - 5;
            highScoreLabel.setText("High Score: " + highscore);
        }
        user.setHighScore(highscore);
    }
    public void endGame(){
        if (guesses == 0) {
            // Handle the case where no guesses were made
            System.out.println("No guesses were made. Accuracy rate is 0%.");
        } else {
            // Calculate the accuracy rate
            float accuracyRate = ((float) correctGuesses / guesses) * 100;
            accuracyRate = (user.getAccuracy()*user.getNumGames()+accuracyRate)/user.getNumGames()+1;
            user.setAccuracy(accuracyRate);
            user.setNumGames(user.getNumGames() + 1);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g); // Paints the background
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}