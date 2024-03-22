import javax.swing.*;

public class MyFrame extends JFrame{

    MyPanel panel;

    MyFrame(){

        panel = new MyPanel();
        //this.setPreferredSize(new Dimension(1000, 500));
        this.setUndecorated(true); // Remove window decorations (title bar, borders)
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Set JFrame to fullscreen
        this.setResizable(false); // Disable resizing
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.add(panel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}