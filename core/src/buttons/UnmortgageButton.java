package buttons;

import com.badlogic.gdx.Gdx;

/**
 * Class acting as the unmortgage button
 */
public class UnmortgageButton extends MainButton {
    /**
     * Constructor of the unmortgage button
     * @param x Starting position x
     * @param y Starting position y
     */
    public UnmortgageButton(float x, float y) {
        super("buttons/unmortgage_button.png", x, y, Gdx.graphics.getWidth() / 6f, Gdx.graphics.getHeight() / 15f);
    }
}
