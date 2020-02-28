package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.darash.monopoly.MyGame;
import java.util.Random;
import buttons.AuctionButton;
import buttons.BuyButton;
import buttons.ChanceButton;
import buttons.CommunityChestButton;
import buttons.MortgageButton;
import buttons.StartGameButton;
import buttons.ThrowDiceButton;
import buttons.UnmortgageButton;
import classes.Player;
import classes.Property;
import classes.Square;
import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;
import listeners.StageListener;
import text.PlayerText;

public abstract class BaseScreen implements Screen {
    protected MyGame game;
    protected Stage screen;
    protected Texture background;
    private GDXDialogs dialogs;
    private Player testing;
    private PlayerText testingText;
    private Square[] board;
    private String[] communityChest;
    private String[] chance;
    private StartGameButton startButton;
    private BuyButton buyButton;
    private AuctionButton auctionButton;
    private ThrowDiceButton diceButton;
    private MortgageButton mortgageButton;
    private UnmortgageButton unmortgageButton;
    private CommunityChestButton chestButton;
    private ChanceButton chanceButton;

    protected BaseScreen(MyGame mg) {
        this.game = mg;
        board=Square.initSquares();
        communityChest=Square.initCommunityChest();
        chance=Square.initChance();
        dialogs = GDXDialogsSystem.install();
        screen = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        testing = new Player("pieces/zapato.png", Gdx.graphics.getWidth()/1.6704f, Gdx.graphics.getHeight()/6.75f);
        startButton = new StartGameButton(Gdx.graphics.getWidth()/3.7963f, Gdx.graphics.getHeight()/2.16f);
        buyButton = new BuyButton(Gdx.graphics.getWidth()/4.64f, Gdx.graphics.getHeight()/2.16f);
        auctionButton = new AuctionButton(Gdx.graphics.getWidth()/2.784f, Gdx.graphics.getHeight()/2.16f);
        diceButton = new ThrowDiceButton(Gdx.graphics.getWidth()/3.7963f, Gdx.graphics.getHeight()/2.16f);
        mortgageButton = new MortgageButton(Gdx.graphics.getWidth()/6.96f, Gdx.graphics.getHeight()/36f);
        unmortgageButton = new UnmortgageButton(Gdx.graphics.getWidth()/2.784f, Gdx.graphics.getHeight()/36f);
        chestButton=new CommunityChestButton(Gdx.graphics.getWidth()/3.7963f, Gdx.graphics.getHeight()/2.16f);
        chanceButton=new ChanceButton(Gdx.graphics.getWidth()/3.7963f, Gdx.graphics.getHeight()/2.16f);
        screen.addActor(testing);
        screen.addActor(diceButton);
        screen.addActor(buyButton);
        screen.addActor(auctionButton);
        screen.addActor(mortgageButton);
        screen.addActor(unmortgageButton);
        screen.addActor(startButton);
        screen.addActor(chestButton);
        screen.addActor(chanceButton);
        buyButton.setVisible(false);
        auctionButton.setVisible(false);
        diceButton.setVisible(false);
        mortgageButton.setVisible(false);
        unmortgageButton.setVisible(false);
        chestButton.setVisible(false);
        chanceButton.setVisible(false);
        // screen.addActor(testingText);

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                diceButton.setVisible(true);
                mortgageButton.setVisible(true);
                unmortgageButton.setVisible(true);
                startButton.setVisible(false);
            }
        });

        auctionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                bDialog.setTitle("Buy a item");
                bDialog.setMessage("Do you want to buy the mozarella?");

                bDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        // handle button click here
                    }
                });

                bDialog.addButton("OK");
                bDialog.build().show();
            }
        });


        Gdx.app.log("Dimensiones", Gdx.graphics.getWidth()+", "+Gdx.graphics.getHeight());
        diceButton.addListener(new ClickListener() {
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
                            moveLeft.setAmountX(Gdx.graphics.getWidth()/18.15652f*-1);
                            moveLeft.setDuration(0.4f);
                            sa.addAction(moveLeft);
                            testing.addAction(sa);
                        }
                    } else if (testing.getBoardPosition() >= 10 && testing.getBoardPosition() <= 19) {
                        MoveByAction moveUp = new MoveByAction();
                        if (testing.getBoardPosition() == 10 || testing.getBoardPosition() == 19) {
                            moveUp.setAmountY(Gdx.graphics.getHeight()/9.81818f);
                            moveUp.setDuration(0.4f);
                            sa.addAction(moveUp);
                            testing.addAction(sa);
                        } else {
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
                            moveRight.setAmountX(Gdx.graphics.getWidth()/18.15652f);
                            moveRight.setDuration(0.4f);
                            sa.addAction(moveRight);
                            testing.addAction(sa);
                        }
                    } else if (testing.getBoardPosition() >= 30 && testing.getBoardPosition() <= 39) {
                        MoveByAction moveDown = new MoveByAction();
                        if (testing.getBoardPosition() == 30 || testing.getBoardPosition() == 39) {
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
                switch (board[testing.getBoardPosition()].getType()) {
                    case CITY:
                    case STATION:
                        if (board[testing.getBoardPosition()].getProperty().getOwner()==null) {
                            buyButton.setVisible(true);
                            auctionButton.setVisible(true);
                            diceButton.setVisible(false);
                        } else {
                            GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                            bDialog.setTitle("Propiedad Comprada");
                            Property prop=board[testing.getBoardPosition()].getProperty();
                            bDialog.setMessage("La propiedad '"+prop.getName()+ "' está comprada por "+prop.getOwner().getName()+
                                    ". Debes pagar "+prop.getRentPrice()+"€");

                            bDialog.setClickListener(new ButtonClickListener() {
                                @Override
                                public void click(int button) {

                                }
                            });
                            bDialog.addButton("OK");
                            bDialog.build().show();
                            testing.setMoney(testing.getMoney()-prop.getRentPrice());
                        }
                        break;
                    case COMMUNITY_CHEST:
                        chestButton.setVisible(true);
                        diceButton.setVisible(false);
                        break;
                    case CHANCE:
                        chanceButton.setVisible(true);
                        diceButton.setVisible(false);
                        break;
                    case GO:
                        testing.setMoney(testing.getMoney()+200);
                        break;
                    case TAXES:
                        break;
                    case NO_PARKING:
                        break;
                    default:
                        break;
                }
            }
        });

        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                testing.buyProperty(board);

                GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                bDialog.setTitle("Propiedad Comprada");
                Property prop=board[testing.getBoardPosition()].getProperty();
                bDialog.setMessage(testing.getName()+" ha comprado "+prop.getName()+" por "+prop.getValue()+"€");
                bDialog.addButton("OK");
                bDialog.build().show();
                bDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        buyButton.setVisible(false);
                        auctionButton.setVisible(false);
                        diceButton.setVisible(true);
                    }
                });

                Gdx.app.log("Comprar", "Propiedad Comprada");
                Gdx.app.log("Comprar", "Se ha comprado "+board[testing.getBoardPosition()].getProperty());
                Gdx.app.log("Comprar", "Nuevo balance del usuario: "+testing.getMoney());
                Gdx.app.log("Comprar", testing.getPropertiesBought()+"");
            }
        });

        chestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Random r=new Random();
                final int num=r.nextInt(communityChest.length);
                String textCommunityChest=communityChest[num];

                GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                bDialog.setTitle("Arca comunal");
                bDialog.setMessage(textCommunityChest);
                bDialog.addButton("OK");
                bDialog.build().show();
                bDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        switch (num) {
                            case 0:
                                testing.setnGetOutOfJailFreeCards(testing.getnGetOutOfJailFreeCards()+1);
                                break;
                            case 1:
                            case 4:
                                testing.setMoney(testing.getMoney()-50);
                                break;
                            case 2:
                            case 3:
                            case 9:
                                testing.setMoney(testing.getMoney()+100);
                                break;
                            case 5:
                                testing.setMoney(testing.getMoney()+20);
                                break;
                            case 6:
                                testing.setMoney(testing.getMoney()-100);
                                break;
                            case 7:
                                // TODO Case go to jail
                                break;
                            case 8:
                                testing.setMoney(testing.getMoney()+25);
                                break;
                            case 10:
                                testing.setMoney(testing.getMoney()+50);
                                break;
                            case 11:
                                testing.setMoney(testing.getMoney()+10);
                                break;
                            case 12:
                                // TODO Case 'GO'
                                break;
                            case 13:
                                testing.setMoney(testing.getMoney()+200);
                                break;
                        }
                        chestButton.setVisible(false);
                        diceButton.setVisible(true);
                        Gdx.app.log("Chest", testing.getMoney()+"");
                    }
                });
            }
        });

        chanceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Random r=new Random();
                String textChance=chance[r.nextInt(chance.length)];

                GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                bDialog.setTitle("Suerte");
                bDialog.setMessage(textChance);
                bDialog.addButton("OK");
                bDialog.build().show();
                bDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        // TODO switch de suerte
                        chanceButton.setVisible(false);
                        diceButton.setVisible(true);
                    }
                });
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
