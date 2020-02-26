package text;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlayerText extends Actor {
    private SpriteBatch textBatch;
    private BitmapFont text;

    public PlayerText() {
        textBatch=new SpriteBatch();
        text=new BitmapFont();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        textBatch.begin();
        text.draw(textBatch, "HIIIIIIIIWFNREIU!!", Gdx.graphics.getWidth()/3, Gdx.graphics.getHeight()/3, 200f, 1, false);
        textBatch.end();
    }
}
