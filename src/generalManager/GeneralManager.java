package generalManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.ui.Button;
import edu.macalester.graphics.ui.TextField;

public class GeneralManager {
    private CanvasWindow canvas;
    private GraphicsGroup buttonGroup;
    private Map<String, Player> players;
    private TextField searchBar;
    private List<Player> teamList;

    public GeneralManager() {
        try {
            players = PlayerCSVReader.readPlayersFromCSV();
        } catch (Exception e) {
            e.printStackTrace();
        }
        canvas = new CanvasWindow("SoccerGM", 1920, 1080);
        Button startButton = new Button("Play");
        canvas.add(startButton);
        canvas.draw();
        startButton.onClick(() -> selectingScreen());
        teamList = new ArrayList<>();
    }

    public void selectingScreen() {
        canvas.removeAll();
        GraphicsText welcomeText = new GraphicsText("Welcome to the Game! \nSelect your team", canvas.getWidth()/2, canvas.getHeight()/4);
        welcomeText.setFontSize(20);
        searchBar = new TextField();
        searchBar.setCenter(canvas.getWidth()/2, canvas.getHeight()/2);
        canvas.add(searchBar);
        canvas.add(welcomeText);
        buttonGroup = new GraphicsGroup();
        canvas.add(buttonGroup);
        searchBar.onChange(input -> {
            playerHighlighting(input.toLowerCase());
        });
    }

    public void playerHighlighting(String input) {
        canvas.remove(buttonGroup);
        buttonGroup = new GraphicsGroup();
        canvas.add(buttonGroup);
        if (input.length() >= 3){
            int x = 60;
            int y = 50;
            for (String player : players.keySet()) {
                player = player.toLowerCase();
                if (player.contains(input)) {
                    Button playerButton = new Button(player);
                    playerButton.setCenter(x, y);
                    y += 30;
                    buttonGroup.add(playerButton);
                }
            }
        }
    }
    
    public static void main(String[] args) {
        new GeneralManager();
    }
}
