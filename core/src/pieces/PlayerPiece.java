package pieces;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;


public class PlayerPiece extends Actor {
    private Sprite sprite;
    private String name;
    private int boardPosition;

    public PlayerPiece(String texture, float x, float y) {
        this.boardPosition=0;
        sprite=new Sprite(new Texture(texture));
        sprite.setBounds(x, y, Gdx.graphics.getWidth()/12, Gdx.graphics.getHeight()/12);
        this.setPosition(x, y);
        this.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.setOrigin(x,y);
        sprite.setOrigin(this.getOriginX(),this.getOriginY());
    }

    public void moveByPixels(float x, float y) {
        this.setPosition(x, y);
        sprite.setPosition(x, y);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.setPosition(getX(), getY());
        sprite.setScale(getScaleX(), getScaleY());
        sprite.setRotation(getRotation());
        sprite.draw(batch);
    }

    public void setBoardPosition(int boardPosition) {
        this.boardPosition = boardPosition;
    }

    public int getBoardPosition() {
        return boardPosition;
    }


}
