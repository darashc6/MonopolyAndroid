package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.darash.monopoly.MyGame;

import java.util.Random;

import classes.Square;
import listeners.StageListener;
import pieces.PlayerPiece;

public abstract class BaseScreen implements Screen {
    protected MyGame game;
    protected Stage screen;
    private Group players;
    protected Texture background;
    private PlayerPiece dice;
    private PlayerPiece testing;
    private Square[] board;

    protected BaseScreen(MyGame mg) {
        this.game = mg;
        this.board=Square.initSquares();
        dice = new PlayerPiece("pieces/zapato.png", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        dice.setTouchable(Touchable.enabled);
        testing = new PlayerPiece("pieces/zapato.png", Gdx.graphics.getWidth() / 1.32f, Gdx.graphics.getHeight() / 18);
        screen = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        screen.addActor(testing);
        screen.addActor(dice);

        dice.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Random r = new Random();
                int number = r.nextInt(6) + 1;
                for (int i=0; i<number; i++) {
                    if (testing.getBoardPosition() >= 0 && testing.getBoardPosition() <= 9) {
                        MoveByAction moveLeftAction = new MoveByAction();
                        if (testing.getBoardPosition() == 0 || testing.getBoardPosition() == 9) {
                            testing.setPosition(testing.getX() - (Gdx.graphics.getWidth() * ((Gdx.graphics.getWidth() / 9.5f) / Gdx.graphics.getWidth())), testing.getY());
                        } else {
                            testing.setPosition(testing.getX() - (Gdx.graphics.getWidth() * ((Gdx.graphics.getWidth() / 12.9f) / Gdx.graphics.getWidth())), testing.getY());
                        /*moveLeftAction.setAmount(Gdx.graphics.getWidth()/12.9f*-number, 0);
                        moveLeftAction.setDuration(0.3f*number);
                        testing.addAction(moveLeftAction);*/
                        }
                    } else if (testing.getBoardPosition() >= 10 && testing.getBoardPosition() <= 19) {
                        if (testing.getBoardPosition() == 10 || testing.getBoardPosition() == 19) {
                            testing.setPosition(testing.getX(), testing.getY() + (Gdx.graphics.getHeight() * ((Gdx.graphics.getHeight() / 9f) / Gdx.graphics.getHeight())));
                        } else {
                            testing.setPosition(testing.getX(), testing.getY() + (Gdx.graphics.getHeight() * ((Gdx.graphics.getHeight() / 13.012f) / Gdx.graphics.getHeight())));
                        }
                    } else if (testing.getBoardPosition() >= 20 && testing.getBoardPosition() <= 29) {
                        if (testing.getBoardPosition() == 20 || testing.getBoardPosition() == 29) {
                            testing.setPosition(testing.getX() + (Gdx.graphics.getWidth() * ((Gdx.graphics.getWidth() / 9.5f) / Gdx.graphics.getWidth())), testing.getY());
                        } else {
                            testing.setPosition(testing.getX() + (Gdx.graphics.getWidth() * ((Gdx.graphics.getWidth() / 12.9f) / Gdx.graphics.getWidth())), testing.getY());
                        }
                    } else if (testing.getBoardPosition() >= 30 && testing.getBoardPosition() <= 39) {
                        if (testing.getBoardPosition() == 30 || testing.getBoardPosition() == 39) {
                            testing.setPosition(testing.getX(), testing.getY() - (Gdx.graphics.getHeight() * ((Gdx.graphics.getHeight() / 9f) / Gdx.graphics.getHeight())));
                        } else {
                            testing.setPosition(testing.getX(), testing.getY() - (Gdx.graphics.getHeight() * ((Gdx.graphics.getHeight() / 13.012f) / Gdx.graphics.getHeight())));
                        }
                    }
                    if (testing.getBoardPosition() == 39) {
                        testing.setBoardPosition(0);
                    } else {
                        testing.setBoardPosition(testing.getBoardPosition() + 1);
                    }
                }
                Gdx.app.log("Click", testing.getBoardPosition() + "");
                if(board[testing.getBoardPosition()].getProperty()!=null) {
                    Gdx.app.log("Square", board[testing.getBoardPosition()].getProperty().toString());
                }
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
