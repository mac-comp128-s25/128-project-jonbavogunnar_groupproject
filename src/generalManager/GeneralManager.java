package generalManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import java.awt.*;

public class GeneralManager {
    private JFrame canvas;
    private JPanel buttonGroup;
    private Map<String, Player> players;
    private JTextField searchBar;

    public GeneralManager() {
        try {
            players = PlayerCSVReader.readPlayersFromCSV();
        } catch (Exception e) {
            e.printStackTrace();
        }
        canvas = new JFrame("SoccerGM");
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setLayout(new FlowLayout());
        JButton startButton = new JButton("Play");
        canvas.add(startButton);
        canvas.setSize(1920, 1080);
        canvas.setVisible(true);
        startButton.addActionListener(e -> selectingScreen());
    }

    public void selectingScreen() {
        canvas.getContentPane().removeAll();
        
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        Label welcomeText = new Label("Welcome to the Game! \nSelect your team");
        welcomeText.setFont(new Font("Arial", Font.PLAIN, 20));
        welcomeText.setAlignment(Label.CENTER);

        searchBar = new JTextField();
        AutoSuggestUtils.attachAutoSuggest(searchBar, players);

        panel.add(Box.createVerticalStrut(20));
        panel.add(welcomeText);
        panel.add(Box.createVerticalStrut(10));
        panel.add(searchBar);

        canvas.getContentPane().add(panel);
        canvas.revalidate();
        canvas.repaint();
    }

    public void playerHighlighting(String input) {
        canvas.remove(buttonGroup);
        buttonGroup = new JPanel();
        canvas.add(buttonGroup);
        if (input.length() >= 3){
            int x = 60;
            int y = 50;
            for (String player : players.keySet()) {
                player = player.toLowerCase();
                if (player.contains(input)) {
                    JButton playerButton = new JButton(player);
                    playerButton.setLocation(x, y);
                    y += 30;
                    buttonGroup.add(playerButton);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        new GeneralManager();
    }
}
