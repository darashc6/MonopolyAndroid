package buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ThrowDiceButton extends Actor {
    private Sprite sprite;

    public ThrowDiceButton(float x, float y) {
        sprite=new Sprite(new Texture("buttons/dice_button.png"));
        sprite.setBounds(x, y, Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/12);
        this.setPosition(x, y);
        this.setSize(Gdx.graphics.getWidth()/6,Gdx.graphics.getHeight()/12);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.setPosition(getX(), getY());
        sprite.setScale(getScaleX(), getScaleY());
        sprite.setRotation(getRotation());
        sprite.draw(batch);
    }
}
