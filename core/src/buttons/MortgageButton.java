package buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class MortgageButton extends Actor {
    private Sprite sprite;

    public MortgageButton(float x, float y) {
        sprite=new Sprite(new Texture("buttons/mortgage_button.png"));
        sprite.setBounds(x, y, Gdx.graphics.getWidth()/6, Gdx.graphics.getHeight()/15);
        this.setPosition(x, y);
        this.setSize(Gdx.graphics.getWidth()/6,Gdx.graphics.getHeight()/15);
        this.setOrigin(x,y);
        sprite.setOrigin(this.getOriginX(),this.getOriginY());
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
