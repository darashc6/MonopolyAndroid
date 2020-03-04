package buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class BuyButton extends Actor {
    private Sprite sprite;

    public BuyButton(float x, float y) {
        sprite=new Sprite(new Texture("buttons/buy_button.png"));
        sprite.setBounds(x, y, Gdx.graphics.getWidth()/8f, Gdx.graphics.getHeight()/15f);
        this.setPosition(x, y);
        this.setSize(Gdx.graphics.getWidth()/8f,Gdx.graphics.getHeight()/15f);
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
