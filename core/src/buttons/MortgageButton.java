package buttons;

import com.badlogic.gdx.Gdx;

/**
 * Class acting as the mortgage property button
 */
public class MortgageButton extends MainButton {
    /**
     * Constructor of the mortgage button
     * @param x Starting position x
     * @param y Starting position y
     */
    public MortgageButton(float x, float y) {
        super("buttons/mortgage_button.png", x, y, Gdx.graphics.getWidth()/6f, Gdx.graphics.getHeight()/15f);
    }
}
