import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * RegisterScreen extends the Screen class to provide a user interface for user registration.
 * It includes text fields for entering a username and password, with confirmation, a register button to submit the registration,
 * and an escape button for additional functionality or navigation.
 */
public class RegisterScreen extends Screen {
    /**
     * A text field allowing the user to enter a username. This field is typically accompanied
     * by a label indicating its purpose. The entered username is used as part of the registration process.
     */
    JTextField username;

    /**
     * A password field where the user can enter their chosen password. This field obscures the entered
     * characters for security. The password entered here is to be matched with the one entered in the
     * {@code password_2} field as part of the registration validation process.
     */
    JPasswordField password;

    /**
     * A second password field for confirming the user's chosen password. It serves as a verification step
     * to ensure that the user has correctly entered their intended password without any typographical errors.
     * The contents of this field should match those of the {@code password} field to successfully complete registration.
     */
    JPasswordField password_2;

    /**
     * A button that initiates the registration process. Clicking this button typically triggers
     * validation of the input fields ({@code username}, {@code password}, and {@code password_2})
     * and, if successful, creates a new user account with the entered credentials.
     */
    JButton register;

    /**
     * A button that allows users to cancel the registration process or navigate back
     * to a previous screen. This provides a way to exit the registration screen without completing the process.
     */
    JButton esc;

    /**
     * An image used as the background for the registration screen. This image can enhance
     * the visual appeal of the screen and contribute to the overall theme of the user interface.
     */
    Image plankIMG;


    /**
     * Constructs a RegisterScreen with input fields for registration and buttons for submitting the registration or exiting the screen.
     *
     * @param frame    The FullScreenUI frame that contains this screen.
     * @param previous The previous Screen object to return to when navigating back.
     */
    public RegisterScreen(FullScreenUI frame, Screen previous) {
        super(frame, previous);

        // Initializing components
        esc = new JButton();


        // Username field setup
        username = new JTextField("Enter Username", 16);
        username.setOpaque(false);
        username.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        username.setForeground(new Color(255, 255, 255));
        username.setCaretColor(Color.WHITE);
        username.setFont(loadFont("/Viner.ttf", 24));

        // Add a focus listener to handle placeholder text behavior
        username.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the placeholder text when the username field gains focus
                if (username.getText().equals("Enter Username")) {
                    username.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restore the placeholder text if the username field is left empty
                if (username.getText().isEmpty()) {
                    username.setText("Enter Username");
                }
            }
        });

        password = new JPasswordField(16);

        password.setText("Enter Password");
        password.setEchoChar((char) 0); // Set initial echo char to 0 (no echo)
        password.setOpaque(false);
        password.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK)); // Remove border
        password.setForeground(new Color(255, 255, 255));
        password.setCaretColor(Color.WHITE);

        password.setFont(loadFont("/Viner.ttf", 24));


        password_2 = new JPasswordField(16);
        password_2.setText("Re-enter Password");
        password_2.setEchoChar((char) 0); // Set initial echo char to 0 (no echo)
        password_2.setOpaque(false);
        password_2.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK)); // Remove border
        password_2.setForeground(new Color(255, 255, 255));
        password_2.setCaretColor(Color.WHITE);
        password_2.setFont(loadFont("/Viner.ttf", 24));
        // Add focus listeners to handle placeholder text behavior for password fields
        password.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the placeholder text when the password field gains focus
                if (Arrays.equals(password.getPassword(), "Enter Password".toCharArray())) {
                    password.setText("");
                    password.setEchoChar('*'); // Set echo char to '*' for password entry
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                // Restore the placeholder text if the password field is left empty
                if (password.getPassword().length == 0) {
                    password.setText("Enter Password");
                    password.setEchoChar((char) 0); // Set echo char to 0 (no echo)
                }
            }
        });


        password_2.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                // Clear the placeholder text when the password field gains focus
                if (Arrays.equals(password_2.getPassword(), "Re-enter Password".toCharArray())) {
                    password_2.setText("");
                    password_2.setEchoChar('*'); // Set echo char to '*' for password entry
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Restore the placeholder text if the password field is left empty
                if (password_2.getPassword().length == 0) {
                    password_2.setText("Re-enter Password");
                    password_2.setEchoChar((char) 0); // Set echo char to 0 (no echo)
                }
            }
        });

        // Register button setup
        register = new JButton("Register");
        register.addActionListener(e -> registerButton());

        // Add components to the panel
        this.add(username);
        this.add(password);
        this.add(password_2);
        this.add(register);

        // Esc button setup
        BufferedImage escIcon = null;
        try {
            plankIMG = ImageIO.read(getClass().getResourceAsStream("/plank.png"));
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
     * exit button linkage
     */
    public void exitButton() {
        swapScreens(prev);
    }

    /**
     * Sets the layout of components on the screen based on the screen dimensions.
     */
    public void setComponents() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3;
        Image scaledImage = plankIMG.getScaledInstance(width / 5, height / 12, Image.SCALE_SMOOTH);
        createButtons(register, scaledImage, width / 60);
        username.setBounds(mainButtonX, mainButtonY, width / 5, height / 20);
        password.setBounds(mainButtonX, mainButtonY + height / 10, width / 5, height / 20);
        password_2.setBounds(mainButtonX, mainButtonY + (height / 10) * 2, width / 5, height / 20);
        register.setBounds(mainButtonX + (width / 20), mainButtonY + (height / 10) * 3, width / 10, height / 20);

        esc.setBounds(width / 30, height / 22, 50, 50);
        esc.setBorderPainted(false);
        esc.setContentAreaFilled(false);
        revalidate();
    }

    /**
     * Initiates the registration process when the register button is pressed.
     * Validates the input and displays an error message if the registration criteria are not met.
     */
    public void registerButton() {
        if (password.getText().equals(password_2.getText())) {
            String s = CsvHandler.credentialChecker(username.getText(), password.getText());
            if (!s.equals("ok")) {
                displayErrorMessage(s);
            } else {
                Player player = new Player(username.getText(), password.getText());
                swapScreens(new GameMainMenu(frame, prev, player));

            }

        } else {
            displayErrorMessage("Your passwords do not match");
        }
    }


    /**
     * Custom paint component method to draw additional UI elements, like titles or backgrounds.
     * Calls setComponents() to layout the components.
     *
     * @param g The Graphics object to protect.
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        setComponents();
        drawTitle(g2D);
    }
}
