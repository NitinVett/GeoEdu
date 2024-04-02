import javax.swing.*;
import java.awt.*;

public class StatScreen extends Screen {
    JButton backButton;
    public StatScreen(FullScreenUI frame, Screen previous,Player user) {
        super(frame, previous,user);
        backButton = new JButton("Back to Main Menu");
        backButton.addActionListener(e -> {
            back();
        });

    }

    public void back(){
        swapScreens(new GameMainMenu(frame,null,user));
    }

    public void setComponents(){
        backButton.setBounds(getWidth()/2-(getWidth()/10/2), getHeight()-getHeight()/5, getWidth()/10,getHeight()/10); // Set button's position and size
        add(backButton);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Cast to Graphics2D for advanced drawing features
        Graphics2D g2d = (Graphics2D) g;

        // Set antialiasing for smoother fonts and lines
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw title

        drawTitle(g2d, "STATS");

        int height = getHeight();
        int width = getWidth();


        // Draw stat labels and values
        g2d.setFont(new Font("Arial", Font.BOLD, width/50)); // Set font for labels

        g2d.drawString("High Score:", width/3, height/2 );
        g2d.drawString("Accuracy:", width/3, height/2+ height/10);


        // Draw stat values with some spacing from labels
        g2d.drawString(Integer.toString(user.getHighScore()), width/3  + width/5, height/2 );
        g2d.drawString(user.getAccuracy() + "%", width/3 + width/5, height/2+ height/10);
        setComponents();

    }


}
