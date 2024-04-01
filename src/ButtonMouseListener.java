//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//public class ButtonMouseListener extends MouseAdapter {
//    private JButton button;
//
//    ButtonMouseListener(JButton button) {
//        this.button = button;
//    }
//
//    @Override
//    public void mouseEntered(MouseEvent e) {
//        button.setForeground(Color.YELLOW);
//        setCustomCursor();// Change text color on mouse enter
//    }
//
//    @Override
//    public void mouseExited(MouseEvent e) {
//        button.setForeground(Color.WHITE);
//        setDefaultCursor();// Change text color back on mouse exit
//    }
//}