package buttons;

import com.badlogic.gdx.Gdx;

/**
 * Class acting as the start game button
 */
public class StartGameButton extends MainButton {
    /**
     * Constructor of the start button
     * @param x Starting position x
     * @param y Starting position y
     */
    public StartGameButton(float x, float y) {
        super("buttons/match_start_button.png", x, y, Gdx.graphics.getWidth()/6f, Gdx.graphics.getHeight()/12f);
    }
}
