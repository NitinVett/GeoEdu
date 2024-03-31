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
public class Gameplay extends Screen {

    private JButton choice1Button;
    private JButton choice2Button;
    private JButton choice3Button;
    private JPanel hintPanel;
    private JPanel flagPanel;

    private JPanel highScorePanel;
    JButton highScores;
    JButton escButton;
    private JLabel countryLabel;
    private JLabel highScoreLabel;

    private Country correctCountry;
    private Country incorrect1;
    private Country incorrect2;
    private String user;
    private GameTesting gameTesting;
    private JToggleButton toggleButton;


    // Three country objects, through them you can pull all the information necessary (flag,mao,hints)
    // When we feed this program, we give it one correct country (this is the country whose data we load )
    // The two incorrect countries (we just use them to pull the names of the country)


    public Gameplay(GameTesting gameTesting, Screen previous, String user, Country correctCountry, Country incorrect1, Country incorrect2) throws IOException {
        super(gameTesting, previous);
        this.gameTesting = gameTesting;
        this.correctCountry = correctCountry;
        this.incorrect1 = incorrect1;
        this.incorrect2 = incorrect2;
        this.user = user;

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

        JLabel hintLabel = new JLabel(correctCountry.getHints().getText());
        hintLabel.setFont(new Font("Monospaced", Font.BOLD, 15));
        hintLabel.setForeground(Color.BLACK); // Set text color
        hintPanel = new JPanel(new BorderLayout());
        hintPanel.setBackground(Color.WHITE); // Set background color
        hintPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        hintPanel.setOpaque(false);
        hintPanel.add(hintLabel, BorderLayout.CENTER);


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

        highScorePanel = new JPanel(new BorderLayout());
        highScorePanel.setOpaque(false);

        highScoreLabel = new JLabel();
        highScoreLabel.setForeground(Color.BLACK);
        highScoreLabel.setFont(new Font("Monospaced", Font.BOLD, 18));

        String highScore = CsvHandler.getHighScore(user);
        if (highScore != null) {
            highScoreLabel.setText("High Score: " + highScore);
        } else {
            highScoreLabel.setText("High Score: N/A");
        }
        highScorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        highScorePanel.add(highScoreLabel, BorderLayout.CENTER);
        this.add(highScorePanel);


        this.add(flagPanel);

        this.add(countryLabel);

        this.add(hintPanel);

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
        double xValue = (width - 450) / 2; // Adjust this value as needed
        double yValue = height * 0.05;
        countryLabel.setBounds((int) xValue, (int) yValue, 450, 450);


        // Positioning hint panel
        int hintPanelWidth = 400;
        xValue = width * 0.03;
        yValue = height * 0.6;
        hintPanel.setBounds((int) xValue, (int) yValue, 400, 200);

        // Positioning flag panel
        if (flagPanel.isVisible()) {
            xValue = width * 0.8;
            yValue = height * 0.649;
            flagPanel.setBounds((int) xValue, (int) (yValue), 100, 60);
        }

        // Positioning choice buttons
        xValue = width / 2 - 65;
        yValue = height * 0.7;
        int gap = (int) (height * 0.03); //4% total

        choice1Button.setBounds((int) xValue, (int) yValue, 130, 50);
        choice2Button.setBounds((int) xValue, (int) (yValue + 50 + (gap)), 130, 50);
        choice3Button.setBounds((int) xValue, (int) (yValue + 100 + (2 * gap)), 130, 50);

        // Positioning high score panel
        xValue = width * 0.7;
        yValue = height * 0.05;
        highScorePanel.setBounds((int) xValue, (int) yValue, 200, 20);

        // Positioning log out
        xValue = width * 0.02;
        yValue = height * 0.03;
        escButton.setBounds((int) xValue, (int) yValue, 50, 50);


        int toggleButtonWidth = 100;
        int toggleButtonHeight = 30;
        double toggleButtonX = width * 0.8; // Adjust as needed
        double toggleButtonY = height * 0.75; // Adjust as needed
        toggleButton.setBounds((int) toggleButtonX, (int) toggleButtonY, toggleButtonWidth, toggleButtonHeight);

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

    private void clickHandling(JButton choiceButton) {
        int highscore = Integer.parseInt(CsvHandler.getHighScore(user));
        if (Objects.equals(choiceButton.getText(), correctCountry.getName())) {
            highscore = highscore + 5;
            CsvHandler.changeHighScore(user, String.valueOf(highscore));
            highScoreLabel.setText("High Score: " + highscore);


            //Returns exceution, this class only deals with three countries, 50 countries
            gameTesting.startNextIteration();
            // Go back to where it was called
        } else {
            highscore = highscore - 5;
            CsvHandler.changeHighScore(user, String.valueOf(highscore));
            highScoreLabel.setText("High Score: " + highscore);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g); // Paints the background
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
