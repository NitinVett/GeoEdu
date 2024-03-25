import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class Screen extends JPanel {
    JFrame frame;
    public Screen(JFrame frame){
        this.frame = frame;
        //set the background for our screens here
        //this.setBackground();
        JButton settings = new JButton();

        //settings image here
        //settings.setIcon();

        //setting button location here
        //settings.setBounds();


        this.setVisible(true);
        settings.addActionListener(e -> settingsButton());
        this.setLayout(null);
        this.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        frame.setVisible(true);
        frame.revalidate();
        frame.repaint();

    }

    public void swapScreens(JPanel panel){
        frame.setContentPane(panel);
        frame.setVisible(true);
        frame.requestFocus();
    }

    //add functionality for setting button
    public void settingsButton() {

    }

    public void drawTitle(Graphics2D g){
        g.setFont(new Font("SansSerif", Font.BOLD, 48));

        FontMetrics fm = g.getFontMetrics();
        int titleWidth = fm.stringWidth("GEOCRAFT");
        int xPosition = getWidth()/2 - titleWidth/2;
        int yPosition = getHeight()/10;

        g.drawString("GEOCRAFT", xPosition, yPosition);
    }

    public void setFocusListeners(JTextField textField,String placeholder){
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                }
            }
        });
    }




}
