import javax.swing.*;
import java.io.IOException;

public class ScreenTester {
    public static void main(String[] args) throws IOException {
        //new FullScreenUI();
        JFrame main = new JFrame();
        main.setExtendedState(JFrame.MAXIMIZED_BOTH);
        main.setUndecorated(true);
        main.setContentPane(new Marathon(main,"hi"));
        main.setVisible(true);
        main.requestFocus();

    }
}