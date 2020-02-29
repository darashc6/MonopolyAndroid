package listeners;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * Class acting as a listener for the stage
 */
public class StageListener extends InputListener {
    private Stage stage; // Stage of the game

    /**
     * Constructor of the Stage class
     * @param s Stage of the game
     */
    public StageListener(Stage s) {
        this.stage=s;
    }
}
