package generalManager;

import java.util.*;

public class FixtureGenerator {

    public static List<List<String[]>> generateSchedule(List<Team> teams) {
        List<List<String[]>> fullSchedule = new ArrayList<>();

        int numTeams = teams.size();
        int numWeeks = numTeams - 1;

        List<Team> rotatingTeams = new ArrayList<>(teams);
        Team fixedTeam = rotatingTeams.remove(0);

        for (int week = 0; week < numWeeks; week++) {
            List<String[]> weekMatches = new ArrayList<>();

            for (int i = 0; i < numTeams / 2; i++) {
                Team home, away;

                if (i == 0) {
                    home = fixedTeam;
                    away = rotatingTeams.get(rotatingTeams.size() - 1);
                } else {
                    home = rotatingTeams.get(i - 1);
                    away = rotatingTeams.get(rotatingTeams.size() - i - 1);
                }

                weekMatches.add(new String[]{home.getName(), away.getName()});
            }

            Collections.rotate(rotatingTeams, 1);
            fullSchedule.add(weekMatches);
        }

        return fullSchedule;
    }
}

