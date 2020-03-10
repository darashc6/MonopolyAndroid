package buttons;

import com.badlogic.gdx.Gdx;

/**
 * Class acting as the community chest button
 */
public class CommunityChestButton extends MainButton {
    /**
     * Constructor of the community chest button
     * @param x Starting position x
     * @param y Starting position y
     */
    public CommunityChestButton(float x, float y) {
        super("buttons/community_chest_button.png", x, y, Gdx.graphics.getWidth()/6f, Gdx.graphics.getHeight()/12f);
    }
}
