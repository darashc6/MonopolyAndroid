package buttons;

import com.badlogic.gdx.Gdx;

/**
 * Class acting as the end turn button
 */
public class EndTurnButton extends MainButton {
    /**
     * Constructor of the end turn button
     * @param x Starting position x
     * @param y Starting position y
     */
    public EndTurnButton(float x, float y) {
        super("buttons/end_turn_button.png", x, y, Gdx.graphics.getWidth()/6f, Gdx.graphics.getHeight()/12f);
    }
}
