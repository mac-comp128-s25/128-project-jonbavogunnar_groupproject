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

        String playerCSV = "National_Teams_Roster_Data.csv";

        List<List<String>> data = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(playerCSV))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] playerData = line.split(",");
                List<String> lineData = Arrays.asList(playerData);
                data.add(lineData);
            }

            System.out.println("\nData from Player Dataset");
            System.out.println("Data Variables: " + String.join(", ", data.get(0)));
            for (int i = 1; i < data.size(); i++) {
                List<String> player = data.get(i);
                System.out.println("Player " + i + ": " + String.join(", ", player));
            }
        } catch (IOException e) {
            System.err.println("Error reading the player CSV file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
