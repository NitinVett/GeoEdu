import javax.swing.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.io.IOException;

// changed, it is now actually fullscreenUI
//
public class FullScreenUI extends JFrame {
    SettingScreen settings;

    public FullScreenUI() throws IOException {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setContentPane(new GamemodeSelectorScreen(this,null));
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
    public Screen getSettings(Screen prev, Player user) {

        settings.setPrev(prev);
        settings.setUser(user);
        return settings;
    }


    public static void main(String[] args) throws IOException {
        new FullScreenUI();

    }
}
