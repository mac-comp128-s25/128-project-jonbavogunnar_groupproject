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
}
