package generalManager;

import java.util.Map;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.ui.Button;
import edu.macalester.graphics.ui.TextField;

public class GeneralManager {
    private CanvasWindow canvas;
    private Team team;
    private Map<String, Player> players;
    private TextField searchBar;

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
    }

    public void selectingScreen() {
        canvas.removeAll();
        GraphicsText welcomeText = new GraphicsText("Welcome to the Game! \nSelect your team", canvas.getWidth()/2, canvas.getHeight()/4);
        welcomeText.setFontSize(20);
        searchBar = new TextField();
        searchBar.setCenter(canvas.getWidth()/2, canvas.getHeight()/2);
        canvas.add(searchBar);
        canvas.add(welcomeText);
    }

    public void playerHighlighting() {
        if (searchBar.getText().length() >= 3) {
            
        }
    }
    
    public static void main(String[] args) {
        new GeneralManager();
    }
}
