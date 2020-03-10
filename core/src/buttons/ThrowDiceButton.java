package buttons;

import com.badlogic.gdx.Gdx;

/**
 * Class acting as the throwing dice button
 */
public class ThrowDiceButton extends MainButton {
    /**
     * Constructor of the dice throw button
     * @param x Starting position x
     * @param y Starting position y
     */
    public ThrowDiceButton(float x, float y) {
        super("buttons/dice_button.png", x, y, Gdx.graphics.getWidth()/6f, Gdx.graphics.getHeight()/12f);
    }
}
