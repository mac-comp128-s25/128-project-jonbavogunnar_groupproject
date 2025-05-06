package generalManager;

import java.util.List;
import java.util.Map;

import javax.swing.*;
import java.awt.*;

public class GeneralManager {
    private JFrame canvas;
    private JPanel panel;
    private Map<String, Player> players;
    private JTextField searchBar;
    private List<Team> teamList;

    public GeneralManager() {
        try {
            players = PlayerCSVReader.readPlayersFromCSV();
        } catch (Exception e) {
            e.printStackTrace();
        }

        canvas = new JFrame("SoccerGM");
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setSize(1920, 1080);
        canvas.setLayout(new FlowLayout());

        JButton startButton = new JButton("Play");
        canvas.add(startButton);

        canvas.setVisible(true);
        startButton.addActionListener(e -> selectingScreen());
    }

    public void selectingScreen() {
        canvas.getContentPane().removeAll();
        
        panel = new JPanel();
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

        createButtons();

        canvas.getContentPane().add(panel);
        canvas.revalidate();
        canvas.repaint();
    }


    public void createButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton addToTeamButton = new JButton("Add To Team!");
        JButton finalizeButton = new JButton("Finalize!");

        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(addToTeamButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(finalizeButton);

        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonPanel);
    }
    
    public static void main(String[] args) {
        new GeneralManager();
    }
}
