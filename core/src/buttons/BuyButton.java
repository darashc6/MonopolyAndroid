package buttons;

import com.badlogic.gdx.Gdx;

/**
 * Class acting as a buy property button
 */
public class BuyButton extends MainButton {
    /**
     * Constructor of the buy button
     * @param x Starting position x
     * @param y Starting position y
     */
    public BuyButton(float x, float y) {
        super("buttons/buy_button.png", x, y, Gdx.graphics.getWidth()/8f, Gdx.graphics.getHeight()/15f);
    }
}
