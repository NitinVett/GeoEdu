import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HighScoreScreen extends Screen {

    ArrayList<String> users;

    ArrayList<JLabel> playerList;
    int page;
    Screen prev;
    public HighScoreScreen(FullScreenUI frame,int page,Screen previous) {
        super(frame,previous);
        this.prev = previous;
        this.page = page;
        users = CsvHandler.getHighScoreOrder();
        playerList = new ArrayList<>();
        for (int i = this.page*7;i<(this.page+1)*7;i++){
            if(!(i>=users.size())){
                playerList.add(new JLabel(users.get(i)));
            }
        }

    }

    public void setComponents(){
        int width = getWidth();
        int height = getHeight();
        int mainButtonX = width / 2 - width / 10;
        int mainButtonY = height / 4;
        int mainButtonYIncrement = height / 10;
        int mainButtonXIncrement = width / 4;

        JLabel highScore = new JLabel("HIGHSCORE");
        JLabel rank = new JLabel("RANK");
        JLabel username = new JLabel("USERNAME");

        username.setFont(new Font("SansSerif", Font.BOLD, 24));
        highScore.setFont(new Font("SansSerif", Font.BOLD, 24));
        rank.setFont(new Font("SansSerif", Font.BOLD, 24));

        username.setBounds(mainButtonX, mainButtonY - mainButtonYIncrement, width / 5, height / 12);
        highScore.setBounds(mainButtonX + mainButtonXIncrement, mainButtonY - mainButtonYIncrement, width / 5, height / 12);
        rank.setBounds(mainButtonX - mainButtonXIncrement / 2, mainButtonY - mainButtonYIncrement, width / 5, height / 12);

        JButton previous = new JButton("Previous");
        JButton next = new JButton("Next");


        int buttonWidth = width / 12;
        int buttonHeight = height / 15;
        int padding = 20;
        int nextX = width - buttonWidth - padding;
        int previousX = nextX - buttonWidth - padding;

        previous.setBounds(previousX, height - buttonHeight - padding, buttonWidth, buttonHeight);
        next.setBounds(nextX, height - buttonHeight - padding, buttonWidth, buttonHeight);
        next.addActionListener(e -> next());
        previous.addActionListener(e -> previous());
        this.add(rank);
        this.add(highScore);
        this.add(username);
        if(this.page!=0) {
            this.add(previous);
        }
        if((this.page+1)*7<users.size()) {
            this.add(next);
        }

        for(int i = 0; i < playerList.size(); i++){
            highScore = new JLabel(CsvHandler.getHighScore(playerList.get(i).getText()));
            rank = new JLabel(Integer.toString(i + 1 + page*7));
            playerList.get(i).setBounds(mainButtonX, mainButtonY + mainButtonYIncrement * i, width / 5, height / 12);
            highScore.setBounds(mainButtonX + mainButtonXIncrement, mainButtonY + mainButtonYIncrement * i, width / 5, height / 12);
            rank.setBounds(mainButtonX - mainButtonXIncrement / 2, mainButtonY + mainButtonYIncrement * i, width / 5, height / 12);

            playerList.get(i).setFont(new Font("SansSerif", Font.BOLD, 24));
            highScore.setFont(new Font("SansSerif", Font.BOLD, 24));
            rank.setFont(new Font("SansSerif", Font.BOLD, 24));
            highScore.setForeground(Color.BLACK);
            playerList.get(i).setForeground(Color.BLACK);
            this.add(rank);
            this.add(highScore);
            this.add(playerList.get(i));
        }
    }

    public void next(){
        swapScreens(new HighScoreScreen(frame,this.page+1,prev));
    }
    public void previous(){
        swapScreens(new HighScoreScreen(frame,this.page-1,prev));
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        setComponents();
    }
}
