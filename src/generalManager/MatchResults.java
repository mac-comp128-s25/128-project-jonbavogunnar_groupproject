package generalManager;

import java.util.ArrayList;
import java.util.List;

public class MatchResults {
    public Team home, away;
    public int homeGoals, awayGoals;
    public List<String> homeScorers = new ArrayList<>();
    public List<String> awayScorers = new ArrayList<>();
    private List<String> team1Scorers;
    private List<String> team2Scorers;

    public MatchResults(Team home, Team away, int homeGoals, int awayGoals){
        this.home = home;
        this.away = away;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }

    public void setTeam1Scorers(List<String> team1Scorers){
        this.team1Scorers = team1Scorers;
    }

    public void setTeam2Scorers(List<String> team2Scorers){
        this.team2Scorers = team2Scorers;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Match Result: ").append(home.getName()).append(" ")
          .append(homeGoals).append(" - ").append(awayGoals).append(" ").append(away.getName()).append("\n\n");

        sb.append(home.getName()).append(" Scorers:\n");
        if (homeScorers.isEmpty()) {
            sb.append("  None\n");
        } else {
            for (String s : homeScorers) sb.append("  • ").append(s).append("\n");
        }

        sb.append("\n").append(away.getName()).append(" Scorers:\n");
        if (awayScorers.isEmpty()) {
            sb.append("  None\n");
        } else {
            for (String s : awayScorers) sb.append("  • ").append(s).append("\n");
        }

        return sb.toString();
    }
}
