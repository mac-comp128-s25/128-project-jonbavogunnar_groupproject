package generalManager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlayerCSVReader {

    public static Map<String, Player> readPlayersFromCSV() throws Exception {
        System.out.println("Reading player data...");

        // Change string to change CSV file being read
        String playerCSV = "Final_Raw_Data.csv";

        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(playerCSV))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] playerData = line.split(",");
                data.add(playerData);
            }

            System.out.println("\nCreating players...");
            Map<String, Player> players = new HashMap<String, Player>();
            for (int i = 1; i < data.size(); i++) {
                String[] player = data.get(i);
                String team = player[0];
                String name = player[1];
                String nationality = player[2];
                char position = player[3].charAt(0);
                int fifaRating = Integer.valueOf(player[4]);
                int offFifaRating = Integer.valueOf(player[5]);
                int defFifaRating = Integer.valueOf(player[6]);

                players.put(name, new Player(team, name, nationality, position, fifaRating, offFifaRating, defFifaRating));
            }
            return players;
            
        } catch (IOException e) {
            throw new Exception("Error reading the player CSV file: " + e.getMessage(), e.getCause());
        }
    }
}
