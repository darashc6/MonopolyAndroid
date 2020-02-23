package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.darash.monopoly.MyGame;

import listeners.StageListener;
import pieces.Player;

public abstract class BaseScreen implements Screen {
    protected MyGame game;
    protected Stage screen;
    private Group players;
    protected Texture background;
    private Player dice;
    private Player testing;

    protected BaseScreen(MyGame mg) {
        this.game = mg;

        dice = new Player("pieces/zapato.png", Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight() / 2);
        dice.setTouchable(Touchable.enabled);
        testing=new Player("pieces/zapato.png", Gdx.graphics.getWidth()/1.32f, Gdx.graphics.getHeight() / 18);
        screen = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        screen.addActor(testing);
        screen.addActor(dice);
        Gdx.app.log("Dimensiones", Gdx.graphics.getWidth() + ", " + Gdx.graphics.getHeight());
        Gdx.app.log("Dimensiones ajustadas ", Gdx.graphics.getWidth() / 2f + ", " + Gdx.graphics.getHeight() / 18);

        dice.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("Click", "Clicked");
                testing.setPosition(testing.getX()-(Gdx.graphics.getWidth()*(162/2088f)), testing.getY());
            }
        });

        screen.addListener(new StageListener(screen));
        Gdx.input.setInputProcessor(screen);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        screen.getBatch().begin();
        screen.getBatch().draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        screen.getBatch().end();
        screen.act(delta);
        screen.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
