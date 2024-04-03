import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;

/**
 * The FullScreenUI class represents the main frame of the application, which is displayed in full screen mode.
 */
public class FullScreenUI extends JFrame {
    SettingScreen settings;

    /**
     * Constructs a FullScreenUI object.
     * @throws IOException If an I/O error occurs.
     */
    public FullScreenUI() throws IOException {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setContentPane(new MainMenu(this));
        this.setVisible(true);
        this.requestFocus();
        settings = new SettingScreen(this, null, null);
        addWindowFocusListener(new WindowFocusListener() {
            @Override
            public void windowGainedFocus(WindowEvent e) {
                // Request focus for your panel when the window gains focus
                getContentPane().requestFocus();
            }
            @Override
            public void windowLostFocus(WindowEvent e) {
                // Handle window losing focus if necessary
            }
        });
    }

    /**
     * Gets the settings screen.
     * @param prev The previous Screen object.
     * @param user The Player object.
     * @return The settings screen.
     */
    public Screen getSettings(Screen prev, Player user) {
        settings.setPrev(prev);
        settings.setUser(user);
        return settings;
    }

    /**
     * The main method to start the application.
     * @param args Command line arguments.
     * @throws IOException If an I/O error occurs.
     */
    public static void main(String[] args) throws IOException {
        new FullScreenUI();
    }
}
