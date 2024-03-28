
import javax.swing.*;
import java.io.IOException;

// changed, it is now actually fullscreenUI
//
public class FullScreenUI extends JFrame {
    SettingScreen settings;
    public FullScreenUI() throws IOException {
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setContentPane(new Marathon(this,null,null));
        this.setVisible(true);
        this.requestFocus();
        //settings = new SettingScreen(this,null);

    }

    public JPanel getSettings(Screen prev){
        settings.setPrev(prev);
        return settings;
    }



    public static void main(String[] args) throws IOException {
        new FullScreenUI();

    }
}
