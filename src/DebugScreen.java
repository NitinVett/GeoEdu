import javax.swing.*;
import java.awt.*;

/**
 * A specialized screen for debugging purposes within the application. It offers a login interface,
 * allowing access to debugging functionalities upon successful password entry. This screen is part
 * of a larger GUI framework, presumably for a game or an application that requires debug access.
 */
public class DebugScreen extends Screen {
    /**
     * text field for password
     */
    JTextField password;
    /**
     * JButton for login button
     */
    JButton login;

    /**
     * Constructs a DebugScreen instance with UI components for debug login.
     *
     * @param frame    The main frame of the application, expected to be an instance of FullScreenUI
     *                 or a similar custom frame class that contains the application's screen management logic.
     * @param previous The screen that was displayed before the DebugScreen, used for navigation purposes.
     */
    public DebugScreen(FullScreenUI frame, Screen previous) {
        super(frame, previous);
        password = new JTextField("Enter Password", 16);
        login = new JButton("LOGIN");
        this.add(password);
        this.add(login);
        login.addActionListener(e -> loginButton());
        setFocusListeners(password, "Enter Password");
        repaint();
    }

    /**
     * Sets up the layout and positions the components on the screen. This method adjusts
     * the positions and sizes of the password field and login button based on the current
     * dimensions of the screen.
     */
    public void setComponents() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3;

        password.setBounds(mainButtonX, mainButtonY + height / 10, width / 5, height / 20);
        login.setBounds(mainButtonX, mainButtonY + (height / 10) * 2, width / 5, height / 20);
        password.setFont(new Font("SansSerif", Font.PLAIN, 24));
        login.setFont(new Font("SansSerif", Font.PLAIN, 24));
        revalidate();
    }

    /**
     * Executes the logic associated with clicking the login button. It checks the entered
     * password against a predefined value and either proceeds to the next screen upon success
     * or displays an error message.
     */
    public void loginButton() {
        String pass = "1029384756";
        user = new Player("Debugger","1029384756");
        if (pass.equals(password.getText())) {
            swapScreens(new GameMainMenu(frame, this, user));
        } else {
            displayErrorMessage("Wrong Password");
        }
    }

    /**
     * Custom painting method for DebugScreen. This method is called by the Swing framework
     * to render the screen. It calls setComponents to ensure components are correctly positioned
     * before painting.
     *
     * @param g The Graphics object used for drawing operations.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        setComponents();
        drawTitle(g2D);
    }
}
