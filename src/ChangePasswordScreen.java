import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Objects;

public class ChangePasswordScreen extends Screen{
    JTextField oldPassword, newPassword, confirmPassword;
    JButton save;
    public ChangePasswordScreen(JFrame frame) {
        super(frame);
        oldPassword = new JTextField("Enter Old Password", 16);
        newPassword = new JTextField("Enter New Password", 16);
        confirmPassword = new JTextField("Confirm Password", 16);

        save = new JButton("SAVE");
        this.add(oldPassword);
        this.add(newPassword);
        this.add(confirmPassword);
        this.add(save);
        save.addActionListener(e -> loginButton());
        setFocusListeners(oldPassword,"Enter Old Password");
        setFocusListeners(newPassword,"Enter New Password");
        setFocusListeners(confirmPassword,"Confirm Password");
        repaint();

    }

    public void setComponents(){
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width/2 - width/10;
        int mainButtonY =  height/3;


        oldPassword.setBounds(mainButtonX ,mainButtonY, width/5, height/20);
        newPassword.setBounds(mainButtonX, mainButtonY+height/10, width/5, height/20);
        confirmPassword.setBounds(mainButtonX,mainButtonY+ (height/10)*2,width/5,height/20);
        save.setBounds(mainButtonX,mainButtonY+ (height/10)*4,width/5,height/20);
        oldPassword.setFont(new Font("SansSerif", Font.PLAIN, 24));
        newPassword.setFont(new Font("SansSerif", Font.PLAIN, 24));
        confirmPassword.setFont(new Font("SansSerif", Font.PLAIN, 24));
        save.setFont(new Font("SansSerif", Font.PLAIN, 24));

        revalidate();


    }



    public void loginButton(){

        String pass = CsvHandler.getPassword(newPassword.getText());
        if(Objects.nonNull(pass) && pass.equals(confirmPassword.getText())) {
            swapScreens(new GameMainMenu(frame));
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
