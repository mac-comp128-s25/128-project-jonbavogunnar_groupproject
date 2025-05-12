package generalManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;
import java.awt.*;

public class GeneralManager {
    private JFrame canvas;
    private JPanel panel;
    private Map<String, Player> players;
    private JTextField searchBar;
    private JScrollPane playerScroll;
    private JTextArea playerLog;
    private List<Player> userTeam;

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

        userTeam = new ArrayList<>();

        playerLog = new JTextArea();
        playerLog.setEditable(false);
        playerLog.setLineWrap(true);
        playerLog.setWrapStyleWord(true);
        playerLog.setFont(new Font("Arial", Font.PLAIN, 30));

        playerScroll = new JScrollPane(playerLog);
        playerScroll.setPreferredSize(new Dimension(300, 300));

        panel.add(Box.createVerticalStrut(20));
        panel.add(welcomeText);
        panel.add(Box.createVerticalStrut(10));
        panel.add(searchBar);
        createButtons();
        panel.add(Box.createVerticalStrut(20));
        panel.add(playerScroll);

        canvas.getContentPane().add(panel);
        canvas.revalidate();
        canvas.repaint();
    }

    public void addToTeam() {
        String name = searchBar.getText();
        Player currPlayer = players.get(searchBar.getText());

        if(name.equals("") || !players.containsKey(name)) {
            JOptionPane.showMessageDialog(canvas, "Invalid player.");
            return;
        }

        if(!userTeam.contains(currPlayer)) {
            userTeam.add(currPlayer);
            playerLog.append(currPlayer + "\n");
        } else {
            JOptionPane.showMessageDialog(canvas, "Player is currently in your team.");
        }
        searchBar.setText("");
    }

    public void removeFromTeam() {
        String name = searchBar.getText();
        Player target = null;

        if(name.equals("") || !players.containsKey(name)) {
            JOptionPane.showMessageDialog(canvas, "Invalid player.");
            return;
        }

        for (Player p : userTeam) {
            if (p.getName().equalsIgnoreCase(name)) {
                target = p;
                break;
            }
        }

        if (target != null) {
            userTeam.remove(target);
            refreshPlayerLog();
            searchBar.setText("");
        } else {
            JOptionPane.showMessageDialog(canvas, "Player not found in your team.");
        }
    }

    private void refreshPlayerLog() {
        playerLog.setText("");
        for (Player p : userTeam) {
            playerLog.append(p.getName() + " - " + p.getPosition() + "\n");
        }
    }

    public void tradingScreen() {
        canvas.getContentPane().removeAll();

        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        

        canvas.getContentPane().add(panel);
        canvas.revalidate();
        canvas.repaint();
    }

    public void createButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton addToTeamButton = new JButton("Add To Team!");
        JButton removeFromTeamButton = new JButton("Remove From Team");
        JButton finalizeButton = new JButton("Finalize!");

        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(addToTeamButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(removeFromTeamButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(finalizeButton);


        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonPanel);
        addToTeamButton.addActionListener(e -> addToTeam());
        removeFromTeamButton.addActionListener(e -> removeFromTeam());
        finalizeButton.addActionListener(e -> {
            if (TeamValidator.isValidTeam(userTeam)) {
                simulateMatchScreen();
            } else {
                JOptionPane.showMessageDialog(canvas, "Invalid Team formation. You must have 1 Goalkeeper, 4 Defenders, 3 Midfielders, and 3 Forwards");
            }
        });
    }

    public void simulateMatchScreen() {
        canvas.getContentPane().removeAll();
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        List<Player> opponentTeam = OpponentTeamGenerator.generateOpponentTeam(players);
        MatchResults results = MatchEngine.matchResults(
                new Team("User", mapFromList(userTeam)),
                new Team("Opponent", mapFromList(opponentTeam))
        );

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.append("Match Result: User " + results.homeGoals + " - " + results.awayGoals + " Opponent\n\n");

        resultArea.append("Your Goals:\n");
        for (String s : results.homeScorers) resultArea.append("• " + s + "\n");

        resultArea.append("\nOpponent Goals:\n");
        for (String s : results.awayScorers) resultArea.append("• " + s + "\n");

        panel.add(new JScrollPane(resultArea));
        canvas.add(panel);
        canvas.revalidate();
        canvas.repaint();
    }

    private Map<String, Player> mapFromList(List<Player> list) {
        Map<String, Player> map = new HashMap<>();
        for (Player p : list) {
            map.put(p.getName(), p);
        }
        return map;
    }

    public static void main(String[] args) {
        new GeneralManager();
    }
}
