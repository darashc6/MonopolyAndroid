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
    private int num;

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
                final int number = r.nextInt(6) + 1;
                final int number2 = r.nextInt(6) + 1;

                if (!testing.isInJail()) {
                    GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                    bDialog.setTitle(testing.getName()+" ha tirado dados");
                    bDialog.setMessage("Dado 1: "+number+"\nDado 2: "+number2);
                    bDialog.addButton("OK");
                    bDialog.build().show();
                    bDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            testing.playerMovement((number+number2), 0.5f, false);
                            landingSituations();
                        }
                    });
                } else {

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
            }
        });

        chestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Random r=new Random();
                num=r.nextInt(communityChest.length);
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
                                if (testing.getBoardPosition()-10<=0) {
                                    testing.playerMovement(testing.getBoardPosition()-10, 1f, true);
                                } else {
                                    testing.playerMovement(10-testing.getBoardPosition(), 0.5f, false);
                                }
                                testing.setInJail(true);
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
                                testing.playerMovement(40-testing.getBoardPosition(), 1f, false);
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
                num=r.nextInt(chance.length);
                String textChance=chance[num];

                GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                bDialog.setTitle("Suerte");
                bDialog.setMessage(textChance);
                bDialog.addButton("OK");
                bDialog.build().show();
                bDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        switch (num) {
                            case 0:
                                testing.playerMovement(39-testing.getBoardPosition(), 1f, false);
                                landingSituations();
                                break;
                            case 1:
                                testing.playerMovement(40-testing.getBoardPosition()+5, 1f, false);
                                testing.setMoney(testing.getMoney()+200);
                                landingSituations();
                                break;
                            case 2:
                                testing.setMoney(testing.getMoney()+150);
                                break;
                            case 3:
                                if (13-testing.getBoardPosition()<=0) {
                                    testing.playerMovement(40-testing.getBoardPosition()+13, 1f, false);
                                    testing.setMoney(testing.getMoney()+200);
                                } else {
                                    testing.playerMovement(13-testing.getBoardPosition(), 0.5f, false);
                                }
                                landingSituations();
                                break;
                            case 4:
                                if (10-testing.getBoardPosition()<=0) {
                                    testing.playerMovement(testing.getBoardPosition()-10, 1f, true);
                                } else {
                                    testing.playerMovement(10-testing.getBoardPosition(), 0.5f, false);
                                }
                                testing.setInJail(true);
                                break;
                            case 5:
                                testing.playerMovement(40-testing.getBoardPosition(), 1f, false);
                                testing.setMoney(testing.getMoney()+200);
                                break;
                            case 6:
                                testing.setMoney(testing.getMoney()+50);
                                break;
                            case 7:
                                testing.setnGetOutOfJailFreeCards(testing.getnGetOutOfJailFreeCards()+1);
                                break;
                            case 8:
                                if (24-testing.getBoardPosition()<=0) {
                                    testing.playerMovement(40-testing.getBoardPosition()+24, 1f, false);
                                    testing.setMoney(testing.getMoney()+200);
                                } else {
                                    testing.playerMovement(24-testing.getBoardPosition(), 0.5f, false);
                                }
                                landingSituations();
                                break;
                            case 9:
                                testing.playerMovement(3, 0.5f, true);
                                landingSituations();
                                break;
                            case 10:
                                testing.setMoney(testing.getMoney()-15);
                                break;
                        }
                        chanceButton.setVisible(false);
                    }
                });
            }
        });

        screen.addListener(new StageListener(screen));
        Gdx.input.setInputProcessor(screen);
    }

    private void landingSituations() {
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
                    final Property prop=board[testing.getBoardPosition()].getProperty();
                    bDialog.setMessage("La propiedad '"+prop.getName()+ "' está comprada por "+prop.getOwner().getName()+
                            ". Debes pagar "+prop.getRentPrice()+"€");

                    bDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            diceButton.setVisible(true);
                            testing.setMoney(testing.getMoney()-prop.getRentPrice());
                        }
                    });
                    bDialog.addButton("OK");
                    bDialog.build().show();
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
                if (testing.getBoardPosition()==4) {
                    GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                    bDialog.setTitle("Tasas");
                    bDialog.setMessage("Deberás cobrar el IVA. Pagar el 21% de tu cuenta bancaria.");

                    bDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            diceButton.setVisible(true);
                            testing.setMoney((int) (testing.getMoney()*0.79));
                        }
                    });
                    bDialog.addButton("OK");
                    bDialog.build().show();
                } else if (testing.getBoardPosition()==36) {
                    GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                    bDialog.setTitle("Tasas");
                    bDialog.setMessage("Gastos mensuales. Pagar 100€ de tu cuenta bancaria.");

                    bDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            diceButton.setVisible(true);
                            testing.setMoney(testing.getMoney()-100);
                        }
                    });
                    bDialog.addButton("OK");
                    bDialog.build().show();
                }
                break;
            case NO_PARKING:
                GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                bDialog.setTitle("Carcel");
                bDialog.setMessage("Ha aparcado el una zona de no parking. Váyase a la cárcel");

                bDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        testing.playerMovement(testing.getBoardPosition()-10, 15f, true);
                        testing.setInJail(true);
                    }
                });
                bDialog.addButton("OK");
                bDialog.build().show();
                break;
        }
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
