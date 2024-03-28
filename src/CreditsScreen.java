import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Objects;

public class CreditsScreen extends Screen{
    JTextField password, username;
    JButton login;
    public CreditsScreen(FullScreenUI frame,JPanel previous) {
        super(frame,previous);
        password = new JTextField("Enter Password", 16);
        username = new JTextField("Enter Username", 16);
        login = new JButton("LOGIN");
        this.add(password);
        this.add(username);
        this.add(login);
        login.addActionListener(e -> loginButton());
        setFocusListeners(password,"Enter Password");
        setFocusListeners(username,"Enter Username");
        repaint();

    }

    public void setComponents(){
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width/2 - width/10;
        int mainButtonY =  height/3;

        username.setBounds(mainButtonX, mainButtonY, width/5, height/20);
        password.setBounds(mainButtonX,mainButtonY + height/10,width/5,height/20);
        login.setBounds(mainButtonX,mainButtonY + (height/10)*2,width/5,height/20);
        username.setFont(new Font("SansSerif", Font.PLAIN, 24));
        password.setFont(new Font("SansSerif", Font.PLAIN, 24));
        revalidate();


    }



    public void loginButton(){

        String pass = CsvHandler.getPassword(username.getText());
        if(Objects.nonNull(pass) && pass.equals(password.getText())) {
            swapScreens(new GameMainMenu(frame,this));
        }else {
            displayErrorMessage("Incorrect username/password");
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2D = (Graphics2D) g;
        setComponents();
        drawTitle(g2D);


    }
}
