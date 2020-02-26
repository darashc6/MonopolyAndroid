package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.darash.monopoly.MyGame;

import java.util.Random;

import classes.Player;
import classes.Square;
import listeners.StageListener;
import text.PlayerText;

public abstract class BaseScreen implements Screen {
    protected MyGame game;
    protected Stage screen;
    protected Texture background;
    private Player dice;
    private Player testing;
    private PlayerText testingText;
    private Square[] board;
    private Actor buy;

    protected BaseScreen(MyGame mg) {
        this.game = mg;
        this.board=Square.initSquares();
        dice = new Player("pieces/zapato.png", Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        buy = new Player("pieces/caballo.png", 1250, 160);
        testingText = new PlayerText();
        testing = new Player("pieces/zapato.png", Gdx.graphics.getWidth()/1.6704f, Gdx.graphics.getHeight()/6.75f);
        screen = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        screen.addActor(testing);
        screen.addActor(dice);
        screen.addActor(buy);
        screen.addActor(testingText);

        Gdx.app.log("Dimensiones", Gdx.graphics.getWidth()+", "+Gdx.graphics.getHeight());
        dice.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Random r = new Random();
                int number = r.nextInt(6) + 1;
                int number2 = r.nextInt(6) + 1;
                SequenceAction sa=new SequenceAction();
                for (int i=0; i<(number+number2); i++) {
                    if (testing.getBoardPosition() >= 0 && testing.getBoardPosition() <= 9) {
                        MoveByAction moveLeft = new MoveByAction();
                        if (testing.getBoardPosition() == 0 || testing.getBoardPosition() == 9) {
                            moveLeft.setAmountX(Gdx.graphics.getWidth()/14.91428f*-1);
                            moveLeft.setDuration(0.4f);
                            sa.addAction(moveLeft);
                            testing.addAction(sa);
                        } else {
                            // testing.setPosition(testing.getX() - (Gdx.graphics.getWidth() * ((Gdx.graphics.getWidth() / 12.9f) / Gdx.graphics.getWidth())), testing.getY());
                            moveLeft.setAmountX(Gdx.graphics.getWidth()/18.15652f*-1);
                            moveLeft.setDuration(0.4f);
                            sa.addAction(moveLeft);
                            testing.addAction(sa);
                        }
                    } else if (testing.getBoardPosition() >= 10 && testing.getBoardPosition() <= 19) {
                        MoveByAction moveUp = new MoveByAction();
                        if (testing.getBoardPosition() == 10 || testing.getBoardPosition() == 19) {
                            // testing.setPosition(testing.getX(), testing.getY() + (Gdx.graphics.getHeight() * ((Gdx.graphics.getHeight() / 9f) / Gdx.graphics.getHeight())));
                            moveUp.setAmountY(Gdx.graphics.getHeight()/9.81818f);
                            moveUp.setDuration(0.4f);
                            sa.addAction(moveUp);
                            testing.addAction(sa);
                        } else {
                            // testing.setPosition(testing.getX(), testing.getY() + (Gdx.graphics.getHeight() * ((Gdx.graphics.getHeight() / 13.012f) / Gdx.graphics.getHeight())));
                            moveUp.setAmountY(Gdx.graphics.getHeight()/14.02597f);
                            moveUp.setDuration(0.4f);
                            sa.addAction(moveUp);
                            testing.addAction(sa);
                        }
                    } else if (testing.getBoardPosition() >= 20 && testing.getBoardPosition() <= 29) {
                        MoveByAction moveRight = new MoveByAction();
                        if (testing.getBoardPosition() == 20 || testing.getBoardPosition() == 29) {
                            moveRight.setAmountX(Gdx.graphics.getWidth()/14.91428f);
                            moveRight.setDuration(0.4f);
                            sa.addAction(moveRight);
                            testing.addAction(sa);
                        } else {
                            // testing.setPosition(testing.getX() + (Gdx.graphics.getWidth() * ((Gdx.graphics.getWidth() / 12.9f) / Gdx.graphics.getWidth())), testing.getY());
                            moveRight.setAmountX(Gdx.graphics.getWidth()/18.15652f);
                            moveRight.setDuration(0.4f);
                            sa.addAction(moveRight);
                            testing.addAction(sa);
                        }
                    } else if (testing.getBoardPosition() >= 30 && testing.getBoardPosition() <= 39) {
                        MoveByAction moveDown = new MoveByAction();
                        if (testing.getBoardPosition() == 30 || testing.getBoardPosition() == 39) {
                            // testing.setPosition(testing.getX(), testing.getY() - (Gdx.graphics.getHeight() * ((Gdx.graphics.getHeight() / 9f) / Gdx.graphics.getHeight())));
                            moveDown.setAmountY(Gdx.graphics.getHeight()/9.81818f*-1);
                            moveDown.setDuration(0.4f);
                            sa.addAction(moveDown);
                            testing.addAction(sa);
                        } else {
                            moveDown.setAmountY(Gdx.graphics.getHeight()/14.02597f*-1);
                            moveDown.setDuration(0.4f);
                            sa.addAction(moveDown);
                            testing.addAction(sa);
                        }
                    }
                    if (testing.getBoardPosition() == 39) {
                        testing.setBoardPosition(0);
                        Gdx.app.log("Dimensiones 0: ", testing.getX()+", "+testing.getY());
                    } else {
                        testing.setBoardPosition(testing.getBoardPosition() + 1);
                    }
                }
                Gdx.app.log("Click", testing.getBoardPosition() + "");
                Gdx.app.log("Dice", number+number2+"");
                Gdx.app.log("Type", board[testing.getBoardPosition()].getType().toString());

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
