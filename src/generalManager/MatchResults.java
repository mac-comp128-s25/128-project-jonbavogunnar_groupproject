package generalManager;

public class MatchResults {
    private Team home, away;
    private int homeGoals, awayGoals;

    public MatchResults(Team home, Team away, int homeGoals, int awayGoals){
        this.home = home;
        this.away = away;
        this.homeGoals = homeGoals;
        this.awayGoals = awayGoals;
    }
}
