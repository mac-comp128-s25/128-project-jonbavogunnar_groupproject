package generalManager;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.ui.Button;

public class GeneralManager {
    public CanvasWindow canvas;
    public GeneralManager() {
        canvas = new CanvasWindow("SoccerGM", 1920, 1080);
        Button startButton = new Button("Play");
        canvas.add(startButton);
        canvas.draw();
        startButton.onClick(() -> selectingScreen());
    }
    public void selectingScreen() {
        canvas.removeAll();
    }
}
