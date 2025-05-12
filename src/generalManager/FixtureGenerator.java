package generalManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FixtureGenerator {
    public static List<List<String[]>> generateSchedule(List<Team> teams) {
        List<List<String[]>> weeklySchedule = new ArrayList<>();
        int totalWeeks = teams.size() - 1;
        int totalTeams = teams.size();

        List<Team> rotating = new ArrayList<>(teams);
        Team fixed = rotating.remove(0);

        for (int week = 0; week < totalWeeks; week++) {
            List<String[]> weekMatches = new ArrayList<>();
            for (int i = 0; i < totalTeams / 2; i++) {
                Team home = (i == 0) ? fixed : rotating.get(i - 1);
                Team away = rotating.get(rotating.size() - i - 1);
                weekMatches.add(new String[]{home.getName(), away.getName()});
            }
            Collections.rotate(rotating, 1);
            weeklySchedule.add(weekMatches);
        }

        return weeklySchedule;
    }
}
