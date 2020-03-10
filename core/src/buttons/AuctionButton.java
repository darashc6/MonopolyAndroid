package buttons;

import com.badlogic.gdx.Gdx;

/**
 * Class acting as the auction property button
 */
public class AuctionButton extends MainButton {
    /**
     * Constructor of the auction button
     * @param x Starting position x
     * @param y Starting position y
     */
    public AuctionButton(float x, float y) {
        super("buttons/auction_button.png", x, y, Gdx.graphics.getWidth()/8f, Gdx.graphics.getHeight()/15f);
    }
}
