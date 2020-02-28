package buttons;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class AuctionButton extends Actor {
    private Sprite sprite;

    public AuctionButton(float x, float y) {
        sprite=new Sprite(new Texture("buttons/auction_button.png"));
        sprite.setBounds(x, y, Gdx.graphics.getWidth()/8, Gdx.graphics.getHeight()/15);
        this.setPosition(x, y);
        this.setSize(Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/15);
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
