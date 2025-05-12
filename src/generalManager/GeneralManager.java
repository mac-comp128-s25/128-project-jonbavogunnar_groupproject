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
    private List<Team> leagueTeams;
    private List<List<String[]>> fixtures;
    private int currentWeek = 0;
    private LeagueTable leagueTable = new LeagueTable();
    private Team userTeamObject;

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

        if (checkForInvalidPlayer(name)) return;

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

        if (checkForInvalidPlayer(name)) return;

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
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JTextArea roster = new JTextArea();
        roster.setEditable(false);
        roster.setFont(new Font("Arial", Font.PLAIN, 30));
        roster.append("All League Players:\n\n");

        for (Team t : leagueTeams) {
            roster.append(t.getName() + ":\n");
            for (Player p : t.getPlayers().values()) {
                roster.append("  " + p.getName() + " (" + p.getPosition() +
                        ", Off: " + p.getOffFifaRating() +
                        ", Def: " + p.getDefFifaRating() + ")\n");
            }
            roster.append("\n");
        }

        JScrollPane scroll = new JScrollPane(roster);
        scroll.setPreferredSize(new Dimension(800, 300));
        panel.add(scroll);

        panel.add(Box.createVerticalStrut(20));
        panel.add(new JLabel("Enter your player name:"));
        JTextField userPlayerField = new JTextField();
        panel.add(userPlayerField);

        panel.add(Box.createVerticalStrut(20));
        panel.add(new JLabel("Enter opponent's player name:"));
        JTextField oppPlayerField = new JTextField();
        panel.add(oppPlayerField);
        
        panel.add(Box.createVerticalStrut(20));
        panel.add(new JLabel("Select opponent team:"));
        JComboBox<String> teamDropdown = new JComboBox<>();
        for (Team t : leagueTeams) {
            if (!t.getName().equalsIgnoreCase("You")) {
                teamDropdown.addItem(t.getName());
            }
        }
        panel.add(teamDropdown);

        JTextArea feedback = new JTextArea();
        feedback.setEditable(false);
        feedback.setFont(new Font("SansSerif", Font.BOLD, 14));
        JScrollPane feedbackScroll = new JScrollPane(feedback);
        feedbackScroll.setPreferredSize(new Dimension(600, 60));
        panel.add(Box.createVerticalStrut(10));
        panel.add(feedbackScroll);

        JButton proposeButton = new JButton("Propose Trade");
        proposeButton.addActionListener(e -> {
            String userName = userPlayerField.getText();
            String oppName = oppPlayerField.getText();
            String oppTeamName = (String) teamDropdown.getSelectedItem();
            Team opponentTeam = findTeamByName(oppTeamName);

            List<String> userOffer = List.of(userName);
            List<String> oppOffer = List.of(oppName);

            boolean accepted = TradeEngine.executeTrade(userTeamObject, opponentTeam, userOffer, oppOffer);
            if (accepted) {
                feedback.setText("Trade accepted!");
                refreshPlayerLog();
            } else {
                feedback.setText("Trade rejected. Try offering a better deal.");
            }
        });

        JButton backButton = new JButton("Back to Game");
        backButton.addActionListener(e -> simulateMatchScreen(new JTextArea("Viewing updated results...")));

        JPanel buttonRow = new JPanel();
        buttonRow.add(proposeButton);
        buttonRow.add(backButton);
        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonRow);

        canvas.getContentPane().add(panel);
        canvas.revalidate();
        canvas.repaint();
    }


    public void createButtons() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton addToTeamButton = new JButton("Add To Team!");
        JButton removeFromTeamButton = new JButton("Remove From Team");
        JButton finalizeButton = new JButton("Finalize!");
        JButton nextWeekButton = new JButton("Next Week");

        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(addToTeamButton);
        buttonPanel.add(Box.createHorizontalGlue());
        buttonPanel.add(removeFromTeamButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(finalizeButton);
        buttonPanel.add(Box.createHorizontalStrut(10)); 
        buttonPanel.add(nextWeekButton);

        panel.add(Box.createVerticalStrut(10));
        panel.add(buttonPanel);
        addToTeamButton.addActionListener(e -> addToTeam());
        removeFromTeamButton.addActionListener(e -> removeFromTeam());
        finalizeButton.addActionListener(e -> {
            if (TeamValidator.isValidTeam(userTeam)) {
            userTeamObject = new Team("You", mapFromList(userTeam));
            leagueTeams = LeagueGenerator.generateLeagueTeams(players, userTeam);
            leagueTeams.add(userTeamObject);
            fixtures = FixtureGenerator.generateSchedule(leagueTeams);
            displayAIAssignedTeams();
            JOptionPane.showMessageDialog(canvas, "Team finalized! Click 'Next Week' to begin the season.");
        } else {
            JOptionPane.showMessageDialog(canvas, "Invalid team formation. You must have 1Goalkeeper, 4 Defenders, 3 Midfielders, and 3 Forwards.");
        }});
        nextWeekButton.addActionListener(e -> simulateCurrentWeek());
    }

    public void simulateCurrentWeek() {
        if (fixtures == null || leagueTeams == null) {
            JOptionPane.showMessageDialog(canvas, "League has not started yet.");
            return;
        }

        if (currentWeek >= fixtures.size()) {
            JOptionPane.showMessageDialog(canvas, "The season is over!");
            return;
        }

        List<String[]> weekMatches = fixtures.get(currentWeek);
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 30));

        for (String[] match : weekMatches) {
        Team t1 = findTeamByName(match[0]);
        Team t2 = findTeamByName(match[1]);

        MatchResults result = MatchEngine.matchResults(t1, t2);
        leagueTable.updateTable(result);
        resultArea.append(result.home.getName() + " " + result.homeGoals + " - " + result.awayGoals + " " + result.away.getName() + "\n");
        if (!result.homeScorers.isEmpty()) {
            resultArea.append("  " + result.home.getName() + " Goals:\n");
            for (String s : result.homeScorers) resultArea.append("    " + s + "\n");
        }
        if (!result.awayScorers.isEmpty()) {
            resultArea.append("  " + result.away.getName() + " Goals:\n");
            for (String s : result.awayScorers) resultArea.append("    " + s + "\n");
        }
            resultArea.append("\n");
        }
        currentWeek++;
        simulateMatchScreen(resultArea);
    }

    public void simulateMatchScreen(JTextArea resultArea) {
        canvas.getContentPane().removeAll();
        
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JScrollPane resultScroll = new JScrollPane(resultArea);
        resultScroll.setPreferredSize(new Dimension(800, 300));

        JTextArea tableArea = new JTextArea();
        tableArea.setEditable(false);
        tableArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        tableArea.append("League Table:\n");
        tableArea.append("Team\tW\tD\tL\tGF\tGA\tPts\n");

        for (Map.Entry<Team, LeagueTable.TeamStats> entry : leagueTable.getSortedTable()) {
            LeagueTable.TeamStats stats = entry.getValue();
            tableArea.append(entry.getKey().getName() + "\t" +
                    stats.wins + "\t" +
                    stats.draws + "\t" +
                    stats.losses + "\t" +
                    stats.goalsFor + "\t" +
                    stats.goalsAgainst + "\t" +
                    stats.points + "\n");
        }
        JScrollPane tableScroll = new JScrollPane(tableArea);
        tableScroll.setPreferredSize(new Dimension(600, 200));
        panel.add(tableScroll);
        panel.add(Box.createVerticalStrut(20));
        panel.add(resultScroll);

        JButton tradeButton = new JButton("Trade Players");
        tradeButton.addActionListener(e -> tradingScreen());
        panel.add(Box.createVerticalStrut(20));
        panel.add(tradeButton);

        JButton nextWeekButton = new JButton("Next Week");
        nextWeekButton.addActionListener(e -> simulateCurrentWeek());

        panel.add(Box.createVerticalStrut(20));
        panel.add(nextWeekButton);

        canvas.getContentPane().add(panel);
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

    private boolean checkForInvalidPlayer(String name) {
        if(name.equals("") || !players.containsKey(name)) {
            JOptionPane.showMessageDialog(canvas, "Invalid player.");
            return true;
        }
        return false;
    }

    private Team findTeamByName(String name) {
    for (Team t : leagueTeams) {
        if (t.getName().equalsIgnoreCase(name)) return t;
    }
        return null;
    }

    public void displayAIAssignedTeams() {
        if (leagueTeams == null || leagueTeams.isEmpty()) {
            JOptionPane.showMessageDialog(canvas, "League teams have not been generated yet.");
            return;
        }

        JTextArea info = new JTextArea();
        info.setEditable(false);
        info.setFont(new Font("Arial", Font.PLAIN, 30));

        info.append("Your Team (You)\n");
        for (Player p : userTeamObject.getPlayers().values()) {
            info.append("   " + p.getName() + " (" + p.getPosition() +
            ", Off: " + p.getOffFifaRating() +
            ", Def: " + p.getDefFifaRating() + ")\n");
        }
            info.append("\n");

            for (Team team : leagueTeams) {
                if (!team.getName().equalsIgnoreCase("You")) {
                    info.append(" " + team.getName() + "\n");
                    for (Player p : team.getPlayers().values()) {
                        info.append("   " + p.getName() + " (" + p.getPosition() +
                        ", Off: " + p.getOffFifaRating() +
                        ", Def: " + p.getDefFifaRating() + ")\n");
                    }
                    info.append("\n");
                }   
            }

        JScrollPane scrollPane = new JScrollPane(info);
        scrollPane.setPreferredSize(new Dimension(600, 500));
        JOptionPane.showMessageDialog(canvas, scrollPane, "League Teams and Players", JOptionPane.INFORMATION_MESSAGE);
    }


    public static void main(String[] args) {
        new GeneralManager();
    }
}
