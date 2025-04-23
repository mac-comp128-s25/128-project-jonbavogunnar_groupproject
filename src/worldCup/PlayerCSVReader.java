package worldCup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PlayerCSVReader {
    public static void main(String[] args) {
        System.out.println("Reading player data...");

        String playerCSV = "Final_Raw_Data.csv";

        List<String[]> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(playerCSV))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] playerData = line.split(",");
                data.add(playerData);
            }

            System.out.println("\nCreating players...");
            List<Player> players = new ArrayList<>();
            for (int i = 1; i < data.size(); i++) {
                String[] player = data.get(i);
                String team = player[0];
                String name = player[1];
                String nationality = player[2];
                char position = player[3].charAt(0);
                int fifaRating = Integer.valueOf(player[4]);

                players.add(new Player(team, name, nationality, position, fifaRating));
            }

            for (Player player : players) {
                System.out.println(player);
            }
            
        } catch (IOException e) {
            System.err.println("Error reading the player CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
