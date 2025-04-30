package generalManager;

import java.awt.Color;
import java.util.List;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.ui.Button;
import edu.macalester.graphics.ui.TextField;

public class GeneralManager {
    private CanvasWindow canvas;
    private List<Player> players;
    private PlayerCSVReader playerData;

    public static void main(String[] args) {
        new GeneralManager();
    }

    public GeneralManager() {
        canvas = new CanvasWindow("SoccerGM", 1920, 1080);
        Button startButton = new Button("Play");
        startButton.setCenter(canvas.getWidth()/2, canvas.getHeight()/1.5);
        canvas.add(startButton);
        canvas.draw();
        startButton.onClick(() -> selectingScreen());
        this.players = playerData.getPlayers();
    }

    public void selectingScreen() {
        canvas.removeAll();
        GraphicsText welcomText = new GraphicsText("Welcome to the Game! \nSelect your team", canvas.getWidth()/2, canvas.getHeight()/4);
        welcomText.setFontSize(20);
        TextField searchBar = new TextField();
        searchBar.setCenter(canvas.getWidth()/2, canvas.getHeight()/2);
        canvas.add(searchBar);
        canvas.add(welcomText);
    }
}
