import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/// make this the parent class for game mode
// one class that  contians common elements, choice 1.
// Make a custom high score panel class so that i can add trophy graphic
// Proof of concept
public class Marathon extends Screen {
    JButton newGame;
    JButton continue_;
    JButton tutorial;
    private JLabel hintLabel;

    // basic structure, indivual classes will do timer and lives,
    // text needs to be dynamic, which is country name, so i want to have a randomizer. first find the list of country
    // text file, 0...pakistan, 1...china.... then its going to create an array of how many country objects. then
    // that selects a random country, removes it from the array, gets the map and hints and the flag, then its puts that
    // name as a choice button and then it reads any two other country but doesnt remove them, just gets the name.
    private JButton choice1Button;
    private JButton choice2Button;
    private JButton choice3Button;
    private JPanel hintPanel;
    private JPanel highScorePanel;
    JButton highScores;
    JButton logout;
    int desiredWidth = 400;
    int desiredHeight = 400;
    private JLabel countryLabel;
    private JLabel highScoreLabel;
    private Image resizedImage;

    public Marathon(FullScreenUI frame,Screen previous, Player username) throws IOException {
        super(frame,previous);
        newGame = new JButton();
        continue_ = new JButton();
        tutorial = new JButton();
        highScores = new JButton();
        logout = new JButton();

        BufferedImage backgroundImage = ImageIO.read(new File("Country Data/Maps/Peru.png"));

//        int desiredWidth = 350; // Set your desired width here
//        int desiredHeight = 350; // Set your desired height here
        Image resizedImage = backgroundImage.getScaledInstance(desiredWidth, desiredHeight, Image.SCALE_SMOOTH);
        countryLabel = new JLabel(new ImageIcon(resizedImage));


        logout.addActionListener(e -> logOutButton());
        logout.setText("Kill");

        hintPanel = new JPanel(new BorderLayout());
        //hintPanel.setBackground(Color.WHITE); // Set background color
        hintPanel.setOpaque(false);
        //hintPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        // Create hint label and load text from file
        // load from a text file.
        Country china = new Country("China",0);
        hintLabel = new JLabel(china.getHints().getText());
        hintLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        hintLabel.setForeground(Color.BLACK); // Set text color

        hintPanel.add(hintLabel, BorderLayout.CENTER);

        // Creating choice buttons
        choice1Button = new JButton("Choice 1");
        choice2Button = new JButton("Choice 2");
        choice3Button = new JButton("Choice 3");

        highScorePanel = new JPanel(new BorderLayout());
        highScorePanel.setOpaque(false);

        highScoreLabel = new JLabel();
        highScoreLabel.setForeground(Color.RED);
        highScoreLabel.setFont(new Font("Monospaced", Font.BOLD, 18));
        String userName = "ahafeez7";
        String highScore = CsvHandler.getHighScore(userName);
        if (highScore != null) {
            highScoreLabel.setText("High Score: " + highScore);
        } else {
            highScoreLabel.setText("High Score: N/A");
        }
        highScorePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        highScorePanel.add(highScoreLabel, BorderLayout.CENTER);
        this.add(highScorePanel);

        this.add(countryLabel);
        this.add(logout);
        this.add(hintPanel);

        this.add(choice1Button);
        this.add(choice2Button);
        this.add(choice3Button);
    }

    private String loadHintText() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        File file = new File("Country Data/Hints/China.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        bufferedReader.close();
        return stringBuilder.toString();
    }

    private void updateButtonPositions() {
        int width = getWidth();
        int height = getHeight();

        double gapPercentage = 0.05; // Adjust this value as needed
        int gap = (int) (height * gapPercentage);

        // Centering the image label to the top
        int imageLabelWidth = desiredWidth;
        int imageLabelHeight = desiredHeight;
        int imageLabelX = (width - imageLabelWidth) / 2;
        int imageLabelY = gap;

        countryLabel.setBounds(imageLabelX, imageLabelY, imageLabelWidth, imageLabelHeight);


        // Positioning hint panel
        int hintPanelWidth = 400;
        int hintPanelHeight = 100;
        int hintPanelX = (width - hintPanelWidth) / 2;
        int hintPanelY = imageLabelY + imageLabelHeight + 20; // Adjust the gap as needed
        hintPanel.setBounds(hintPanelX, hintPanelY, hintPanelWidth, hintPanelHeight);

        gapPercentage = 0.04; // Adjust this value as needed
        gap = (int) (height * gapPercentage); //4% total
        double gapPercentagex = 0.7; // Adjust this value as needed
        int gapx = (int) (height * gapPercentagex);
        // Positioning choice buttons
        int choiceButtonWidth = 130;
        int choiceButtonHeight = 50;
        int choiceButtonY = gapx; // Adjust the gap as needed
        int choiceButtonX = (width/2)-65;
        choice1Button.setBounds(choiceButtonX, choiceButtonY, choiceButtonWidth, choiceButtonHeight);
        choice2Button.setBounds(choiceButtonX , choiceButtonY+(2*gap), choiceButtonWidth, choiceButtonHeight);
        choice3Button.setBounds(choiceButtonX , choiceButtonY+(4*gap), choiceButtonWidth, choiceButtonHeight);
        highScorePanel.setBounds(getWidth() - 250, 30, 200, 20);
        logout.setBounds(width/4, height/4, (width / 10) - 30, height / 12);
        revalidate();

    }

    public void newGameButton() {

        swapScreens(new LoginScreen(frame,this));
    }

    public void continue_Button() {
        swapScreens(new RegisterScreen(frame,this));
    }

    public void highScoreButton() {
        swapScreens(new RegisterScreen(frame,this));
    }

    public void tutorialButton() {
        TutorialScreen tutorialScreen = new TutorialScreen(frame, this);
        frame.addKeyListener(tutorialScreen);
        swapScreens(tutorialScreen);
    }

    public void logOutButton() {
        frame.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("xx");
        super.paintComponent(g); // Paints the background
//        Graphics2D g2D = (Graphics2D) g;
//        drawTitle(g2D);
        updateButtonPositions(); // Consider calling this elsewhere if it causes issues
    }
}
