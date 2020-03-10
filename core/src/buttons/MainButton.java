package buttons;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 * Class acting as a button in the game
 */
public class MainButton extends Actor {
    private Sprite sprite; // Sprite containing the button

    /**
     * Main constructor of the class
     * @param texture Texture of the button
     * @param x Starting position x
     * @param y Starting position y
     * @param width Width of the button
     * @param height Height of the button
     */
    public MainButton(String texture, float x, float y, float width, float height) {
        sprite=new Sprite(new Texture(texture));
        sprite.setBounds(x, y, width, height);
        this.setPosition(x, y);
        this.setSize(width,height);
    }

    /**
     * Draws the button on the screen
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.setPosition(getX(), getY());
        sprite.setScale(getScaleX(), getScaleY());
        sprite.draw(batch);
    }
}
