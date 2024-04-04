import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The HighScoreScreen class represents the user interface for displaying high scores.
 * It displays a list of players along with their high scores and ranks.
 * Users can navigate through the high score pages using the "Previous" and "Next" buttons.
 */
public class HighScoreScreen extends Screen {

    /**
     * A list of user identifiers or names. This ArrayList is used to store the names
     * or unique identifiers of all players relevant to the current context or screen.
     */
    ArrayList<String> users;

    /**
     * A list of JLabel components, each representing a player in the UI.
     * This allows for dynamic representation of player information on the screen,
     * such as names or statuses, in a visually structured format.
     */
    ArrayList<JLabel> playerList;

    /**
     * Tracks the current page number in a paginated display. This is particularly useful
     * in screens that list players or other content that exceeds the space available for display,
     * necessitating navigation through multiple 'pages' or views.
     */
    int page;

    /**
     * A reference to the previous screen in the navigation hierarchy. This Screen object
     * allows for easy navigation back to the prior state or screen within the application's UI flow.
     */
    Screen prev;

    /**
     * Background image used throughout the current screen or application. This image could serve
     * to enhance the aesthetic appeal or maintain consistency in the visual theme of the UI.
     */
    Image plankIMG;


    /**
     * Constructs a HighScoreScreen object with the specified frame, page number, and previous screen.
     *
     * @param frame    The FullScreenUI frame that contains this screen.
     * @param page     The current page number of high scores.
     * @param previous The previous Screen object to return to when navigating back.
     */
    public HighScoreScreen(FullScreenUI frame, int page, Screen previous) {
        super(frame, previous);
        this.prev = previous;
        this.page = page;
        users = CsvHandler.getHighScoreOrder();
        playerList = new ArrayList<>();
        for (int i = this.page * 7; i < (this.page + 1) * 7; i++) {
            if (!(i >= users.size())) {
                playerList.add(new JLabel(users.get(i)));
            }
        }
        try {
            plankIMG = ImageIO.read(getClass().getResourceAsStream("/plank.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Sets the layout and components of the high score screen.
     */
    public void setComponents() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 4;
        int mainButtonYIncrement = height / 10;
        int mainButtonXIncrement = width / 4;
        JButton previous = new JButton("Previous");
        JButton next = new JButton("Next");
        Image scaledImage = plankIMG.getScaledInstance(width / 5, height / 12, Image.SCALE_SMOOTH);
        createButtons(previous, scaledImage, width / 60);
        createButtons(next, scaledImage, width / 60);

        JLabel highScore = new JLabel("HIGHSCORE");
        JLabel rank = new JLabel("RANK");
        JLabel username = new JLabel("USERNAME");

        // Set fonts for labels
        username.setFont(new Font("SansSerif", Font.BOLD, 24));
        highScore.setFont(new Font("SansSerif", Font.BOLD, 24));
        rank.setFont(new Font("SansSerif", Font.BOLD, 24));

        // Set bounds for labels
        username.setBounds(mainButtonX, mainButtonY - mainButtonYIncrement, width / 5, height / 12);
        highScore.setBounds(mainButtonX + mainButtonXIncrement, mainButtonY - mainButtonYIncrement, width / 5, height / 12);
        rank.setBounds(mainButtonX - mainButtonXIncrement / 2, mainButtonY - mainButtonYIncrement, width / 5, height / 12);

        // Set bounds for navigation buttons
        int buttonWidth = width / 12;
        int buttonHeight = height / 15;
        int padding = 20;
        int nextX = width - buttonWidth - padding;
        int previousX = nextX - buttonWidth - padding;
        previous.setBounds(previousX, height - buttonHeight - padding, buttonWidth, buttonHeight);
        next.setBounds(nextX, height - buttonHeight - padding, buttonWidth, buttonHeight);

        // Add action listeners to navigation buttons
        next.addActionListener(e -> next());
        previous.addActionListener(e -> previous());

        // Add components to the panel
        this.add(rank);
        this.add(highScore);
        this.add(username);
        if (this.page != 0) {
            this.add(previous);
        }
        if ((this.page + 1) * 7 < users.size()) {
            this.add(next);
        }

        // Add player information labels to the panel
        for (int i = 0; i < playerList.size(); i++) {
            highScore = new JLabel(CsvHandler.getHighScore(playerList.get(i).getText()));
            rank = new JLabel(Integer.toString(i + 1 + page * 7));
            playerList.get(i).setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * i, width / 5, height / 12);
            highScore.setBounds(mainButtonX + mainButtonXIncrement, mainButtonY + mainButtonYIncrement * i, width / 5, height / 12);
            rank.setBounds(mainButtonX - mainButtonXIncrement / 2, mainButtonY + mainButtonYIncrement * i, width / 5, height / 12);

            // Set fonts for player information labels
            playerList.get(i).setFont(new Font("SansSerif", Font.BOLD, 24));
            highScore.setFont(new Font("SansSerif", Font.BOLD, 24));
            rank.setFont(new Font("SansSerif", Font.BOLD, 24));

            // Set foreground color for player information labels
            highScore.setForeground(Color.BLACK);
            playerList.get(i).setForeground(Color.BLACK);

            // Add player information labels to the panel
            this.add(rank);
            this.add(highScore);
            this.add(playerList.get(i));
        }
    }

    /**
     * Displays the next page of high scores.
     */
    public void next() {
        swapScreens(new HighScoreScreen(frame, this.page + 1, prev));
    }

    /**
     * Displays the previous page of high scores.
     */
    public void previous() {
        swapScreens(new HighScoreScreen(frame, this.page - 1, prev));
    }

    /**
     * Custom paint component method to draw additional UI elements, like titles or backgrounds.
     *
     * @param g The Graphics object to paint.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        setComponents();
    }
}
