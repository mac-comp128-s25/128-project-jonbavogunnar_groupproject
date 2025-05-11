package generalManager;

import java.util.*;

public class LeagueTable {
    private final Map<Team, TeamStats> table = new HashMap<>();

    public void updateTable(MatchResults results){
        Team home = results.home;
        Team away = results.away;
        int homeGoals = results.homeGoals;
        int awayGoals = results.awayGoals;

        updateTeamStats(home, homeGoals, awayGoals);
        updateTeamStats(away, awayGoals, homeGoals);
    }

    private void updateTeamStats(Team team, int goalsFor, int goalsAgainst){
        TeamStats stats = table.getOrDefault(team, new TeamStats());
        stats.played++;
        stats.goalsFor += goalsFor;
        stats.goalsAgainst += goalsAgainst;

        if (goalsFor > goalsAgainst) {
            stats.wins++;
            stats.points += 3;
        } else if (goalsFor == goalsAgainst) {
            stats.draws++;
            stats.points +=1;
        } else{
            stats.losses++;
        }

        table.put(team, stats);
    }

    public void displayTable() {
        System.out.println("League Table:");
        System.out.println("Team | Played | Wins | Draws | Losses | GF | GA | Points");
        for (Map.Entry<Team, TeamStats> entry : table.entrySet()) {
            TeamStats stats = entry.getValue();
            System.out.printf("%-15s %-7d %-5d %-6d %-7d %-3d %-3d %-7d\n", entry.getKey(),
                    stats.played, stats.wins, stats.draws, stats.losses, stats.goalsFor, stats.goalsAgainst, stats.points);
        }
    }

    private static class TeamStats {
        int played = 0;
        int wins = 0;
        int draws = 0;
        int losses = 0;
        int goalsFor = 0;
        int goalsAgainst = 0;
        int points = 0;
    }
}
