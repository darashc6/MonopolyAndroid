package buttons;

import com.badlogic.gdx.Gdx;

/**
 * Class acting as a chance button
 */
public class ChanceButton extends MainButton {
    /**
     * Constructor of the chance button
     * @param x Starting position x
     * @param y Starting position y
     */
    public ChanceButton(float x, float y) {
        super("buttons/chance_button.png", x, y, Gdx.graphics.getWidth()/6f, Gdx.graphics.getHeight()/12f);
    }
}
