import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * The LoginScreen class represents the user interface for logging into the application.
 * It includes text fields for entering a username and password, a login button to submit the credentials,
 * and an escape button for additional functionality or navigation.
 */
public class LoginScreen extends Screen {
    /**
     * A text field for the user to enter their username. This field typically accompanies
     * a label indicating its purpose to the user.
     */
    JTextField username;

    /**
     * A password field that obscures the user's input, enhancing security by preventing
     * password visibility. This field is for entering a password associated with the user account.
     */
    JPasswordField password;

    /**
     * A button that initiates the login process. Clicking this button typically triggers
     * validation of the user's credentials against stored data and, if successful, proceeds
     * to the next relevant screen or application section.
     */
    JButton loginButton;

    /**
     * An escape or back button that allows users to cancel the login process or navigate back
     * to a previous screen. This provides an exit path from the login screen without completing the login.
     */
    JButton esc;

    /**
     * An image used for background or decorative purposes on the login screen. This might be a logo,
     * a thematic illustration, or any other visual element intended to enhance the interface's appearance.
     */
    private Image image;


    /**
     * Constructs a LoginScreen with input fields for username and password, and buttons for login and escape.
     *
     * @param frame    The FullScreenUI frame that contains this screen.
     * @param previous The previous Screen object to return to when navigating back.
     */
    public LoginScreen(FullScreenUI frame, Screen previous) {
        super(frame, previous);
        esc = new JButton();
        loginButton = new JButton("Login");

        // Username field setup
        username = new JTextField("Enter Username", 16);
        username.setOpaque(false);
        username.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        username.setForeground(new Color(255, 255, 255));
        username.setCaretColor(Color.WHITE);
        username.setFont(loadFont("/pirate.ttf", 24));

        // Add a focus listener to handle placeholder text behavior
        username.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (username.getText().equals("Enter Username")) {
                    username.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (username.getText().isEmpty()) {
                    username.setText("Enter Username");
                }
            }
        });

        // Password field setup
        password = new JPasswordField(16);
        password.setText("Enter Password");
        password.setEchoChar((char) 0); // Set initial echo char to 0 (no echo)
        password.setOpaque(false);
        password.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        password.setForeground(new Color(255, 255, 255));
        password.setCaretColor(Color.WHITE);
        password.setFont(loadFont("/pirate.ttf", 24));

        // Add a focus listener to handle placeholder text behavior for the password field
        password.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (Arrays.equals(password.getPassword(), "Enter Password".toCharArray())) {
                    password.setText("");
                    password.setEchoChar('*'); // Set echo char to '*' for password entry
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (password.getPassword().length == 0) {
                    password.setText("Enter Password");
                    password.setEchoChar((char) 0); // Set echo char to 0 (no echo)
                }
            }
        });

        // Add components to the panel
        this.add(password);
        this.add(username);
        this.add(loginButton);

        // Esc button setup
        BufferedImage escIcon = null;
        try {


            image = ImageIO.read(getClass().getResourceAsStream("/plank.png"));
            escIcon = ImageIO.read(getClass().getResourceAsStream("/escape.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Image resizedEsc = escIcon.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
        esc.setIcon(new ImageIcon(resizedEsc));
        esc.addActionListener(e -> exitButton());
        this.add(esc);
        repaint();
    }

    /**
     * Sets the layout of components on the screen based on the screen dimensions.
     */
    public void setComponents() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        Image scaledImage = image.getScaledInstance(width/5, height/12, Image.SCALE_SMOOTH);
        username.setBounds(mainButtonX, height / 3, width / 5, height / 20);
        password.setBounds(mainButtonX, height / 3 + height / 10, width / 5, height / 20);
        createButtons(loginButton, scaledImage, width / 60);
        loginButton.setText("Login");
        loginButton.addActionListener(e -> loginButton());
        loginButton.setBounds(mainButtonX + (width / 20), height / 3 + (height / 10) * 2, width / 10, height / 20);
        username.setFont(loadFont("/Viner.ttf", 24));
        password.setFont(loadFont("/Viner.ttf", 24));
        esc.setBounds(width / 30, height / 22, 50, 50);
        esc.setBorderPainted(false);
        esc.setContentAreaFilled(false);
        revalidate();
    }

    /**
     * Initiates the login process when the login button is pressed.
     * Validates the input and displays an error message if the credentials are incorrect.
     */
    public void loginButton() {
        String enteredUsername = username.getText();
        char[] enteredPassword = password.getPassword();
        String enteredPasswordString = new String(enteredPassword);
        String storedPassword = CsvHandler.getPassword(enteredUsername);
        if (Objects.nonNull(storedPassword) && storedPassword.equals(enteredPasswordString)) {
            Player user = new Player(enteredUsername, enteredPasswordString);
            swapScreens(new GameMainMenu(frame, this, user));
        } else {
            displayErrorMessage("You have entered an incorrect username or password, please try again or register an account");
        }
        Arrays.fill(enteredPassword, '0');
        password.setText("Enter Password");
        password.setEchoChar((char) 0);
    }

    /**
     * Handles the action when the escape button is pressed, navigating back to the previous screen.
     */
    public void exitButton() {
        swapScreens(prev);
    }

    /**
     * Custom paint component method to draw additional UI elements, like titles or backgrounds.
     *
     * @param g The Graphics object to paint.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        setComponents();
        drawTitle(g2D);
    }
}
