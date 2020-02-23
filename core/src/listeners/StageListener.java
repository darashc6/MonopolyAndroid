package listeners;

import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class StageListener extends InputListener {
    private Stage stage;

    public StageListener(Stage s) {
        this.stage=s;
    }
}
