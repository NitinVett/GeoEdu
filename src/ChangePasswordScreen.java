import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Objects;
/**
 * ChangePasswordScreen represents the screen for changing the user's password.
 */
public class ChangePasswordScreen extends Screen {
    /**
     * text field for oldPassword
     */
    private JTextField oldPassword;
    /**
     * text field for newPassword
     */
    private JTextField newPassword;
    /**
     * text field for confirmPassord
     */
    private JTextField confirmPassword;
    /**
     * JButton for save button
     */
    private JButton save;
    /**
     * Constructs a ChangePasswordScreen object.
     *
     * @param frame    The FullScreenUI frame.
     * @param previous The previous screen.
     * @param user     The current player.
     */
    public ChangePasswordScreen(FullScreenUI frame, Screen previous, Player user) {
        super(frame, previous, user);
        // Initialize components
        oldPassword = new JTextField("Enter Old Password", 16);
        newPassword = new JTextField("Enter New Password", 16);
        confirmPassword = new JTextField("Confirm Password", 16);
        save = new JButton("SAVE");
        // Add components to the screen
        this.add(oldPassword);
        this.add(newPassword);
        this.add(confirmPassword);
        this.add(save);
        // Add action listener to the save button
        save.addActionListener(e -> loginButton());
        // Set focus listeners for text fields
        setFocusListeners(oldPassword, "Enter Old Password");
        setFocusListeners(newPassword, "Enter New Password");
        setFocusListeners(confirmPassword, "Confirm Password");
        // Repaint the screen
        repaint();
    }
    /**
     * Sets the components' positions and sizes.
     */
    public void setComponents() {
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 3;
        // Set bounds for components
        oldPassword.setBounds(mainButtonX, mainButtonY, width / 5, height / 20);
        newPassword.setBounds(mainButtonX, mainButtonY + height / 10, width / 5, height / 20);
        confirmPassword.setBounds(mainButtonX, mainButtonY + (height / 10) * 2, width / 5, height / 20);
        save.setBounds(mainButtonX, mainButtonY + (height / 10) * 4, width / 5, height / 20);
        // Set font for components
        oldPassword.setFont(new Font("SansSerif", Font.PLAIN, 24));
        newPassword.setFont(new Font("SansSerif", Font.PLAIN, 24));
        confirmPassword.setFont(new Font("SansSerif", Font.PLAIN, 24));
        save.setFont(new Font("SansSerif", Font.PLAIN, 24));
        // Revalidate the screen
        revalidate();
    }
    /**
     * Handles the action when the save button is clicked.
     */
    public void loginButton() {
        String oldDatabasePassword = CsvHandler.getPassword(user.getUsername());
        if (oldDatabasePassword.equals(oldPassword.getText()) && newPassword.getText().equals(confirmPassword.getText())) {
            user.setPassword(newPassword.getText());
            displayErrorMessage("Password Changed");
            swapScreens(prev);
        } else {
            displayErrorMessage("Incorrect username/password");
        }
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        setComponents();
        drawTitle(g2D);
    }
}
