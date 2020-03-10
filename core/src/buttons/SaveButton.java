package buttons;

import com.badlogic.gdx.Gdx;

/**
 * Class acting as the save game button
 */
public class SaveButton extends MainButton {
    /**
     * Constructor of the save button
     * @param x Starting position x
     * @param y Starting position y
     */
    public SaveButton(float x, float y) {
        super("buttons/save_button.png", x, y, Gdx.graphics.getWidth()/6f,Gdx.graphics.getHeight()/15f);
    }
}
