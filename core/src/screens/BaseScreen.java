package screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.darash.monopoly.MyGame;

import java.util.ArrayList;
import java.util.Random;
import buttons.AuctionButton;
import buttons.BuyButton;
import buttons.ChanceButton;
import buttons.CommunityChestButton;
import buttons.EndTurnButton;
import buttons.MortgageButton;
import buttons.SaveButton;
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

public abstract class BaseScreen implements Screen {
    protected MyGame game;
    protected Stage screen;
    protected Texture background;
    private static GDXDialogs dialogs;
    private static ArrayList<Player> players;
    private SpriteBatch spriteBatch;
    private static Square[] board;
    private static String[] communityChest;
    private static String[] chance;
    private StartGameButton startButton;
    private static BuyButton buyButton;
    private static AuctionButton auctionButton;
    private static ThrowDiceButton diceButton;
    private MortgageButton mortgageButton;
    private UnmortgageButton unmortgageButton;
    private SaveButton saveButton;
    private static CommunityChestButton chestButton;
    private static ChanceButton chanceButton;
    private static EndTurnButton endButton;
    private BitmapFont fontText;

    protected BaseScreen(MyGame mg, ArrayList<Player> arrayPlayers) {
        Gdx.app.log("Dimensiones", Gdx.graphics.getHeight()+", "+Gdx.graphics.getWidth());
        this.game = mg;
        board=Square.initSquares();
        communityChest=Square.initCommunityChest();
        chance=Square.initChance();
        dialogs = GDXDialogsSystem.install();
        screen = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        startButton = new StartGameButton(Gdx.graphics.getWidth()/3.7963f, Gdx.graphics.getHeight()/2.16f);
        buyButton = new BuyButton(Gdx.graphics.getWidth()/4.64f, Gdx.graphics.getHeight()/2.16f);
        auctionButton = new AuctionButton(Gdx.graphics.getWidth()/2.784f, Gdx.graphics.getHeight()/2.16f);
        diceButton = new ThrowDiceButton(Gdx.graphics.getWidth()/3.7963f, Gdx.graphics.getHeight()/2.16f);
        mortgageButton = new MortgageButton(Gdx.graphics.getWidth()/16.704f, Gdx.graphics.getHeight()/36f);
        unmortgageButton = new UnmortgageButton(Gdx.graphics.getWidth()/3.9771f, Gdx.graphics.getHeight()/36f);
        chestButton=new CommunityChestButton(Gdx.graphics.getWidth()/3.7963f, Gdx.graphics.getHeight()/2.16f);
        chanceButton=new ChanceButton(Gdx.graphics.getWidth()/3.7963f, Gdx.graphics.getHeight()/2.16f);
        endButton=new EndTurnButton(Gdx.graphics.getWidth()/3.7963f, Gdx.graphics.getHeight()/2.16f);
        saveButton=new SaveButton(Gdx.graphics.getWidth()/2.2572f, Gdx.graphics.getHeight()/36f);

        spriteBatch=new SpriteBatch();
        FreeTypeFontGenerator fontGenerator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/comici.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        fontParameter.size = 40;
        fontParameter.borderWidth = 2;
        fontParameter.borderColor = Color.WHITE;
        fontParameter.color = Color.BLACK;
        fontText = fontGenerator.generateFont(fontParameter);


        players=new ArrayList<>();

        screen.addActor(diceButton);
        screen.addActor(buyButton);
        screen.addActor(auctionButton);
        screen.addActor(mortgageButton);
        screen.addActor(unmortgageButton);
        screen.addActor(startButton);
        screen.addActor(chestButton);
        screen.addActor(chanceButton);
        screen.addActor(endButton);
        screen.addActor(saveButton);

        buyButton.setVisible(false);
        auctionButton.setVisible(false);
        diceButton.setVisible(false);
        mortgageButton.setVisible(false);
        unmortgageButton.setVisible(false);
        chestButton.setVisible(false);
        chanceButton.setVisible(false);
        endButton.setVisible(false);
        saveButton.setVisible(true);

        for (int i=0; i<arrayPlayers.size(); i++) {
            players.add(new Player(arrayPlayers.get(i).getName(), arrayPlayers.get(i).getSelectedPiece(),
                    Gdx.graphics.getWidth()/1.6704f, Gdx.graphics.getHeight()/6.75f));
            screen.addActor(players.get(i));
        }

        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                diceButton.setVisible(true);
                mortgageButton.setVisible(true);
                unmortgageButton.setVisible(true);
                saveButton.setVisible(true);
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
                        // TODO Auction Dialog
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
                Gdx.app.log("Jugador IA", players.get(1).getPropertiesBought()+"");
                Gdx.app.log("Jugador IA", players.get(1)+"");
                Random r = new Random();
                final int number = r.nextInt(6) + 1;
                final int number2 = r.nextInt(6) + 1;

                if (!players.get(0).isInJail()) { // If player isn't in jail
                    GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                    bDialog.setTitle(players.get(0).getName()+" ha tirado dados");
                    bDialog.setMessage("Dado 1: "+number+"\nDado 2: "+number2);
                    bDialog.addButton("OK");
                    bDialog.build().show();
                    bDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            players.get(0).playerMovement((number+number2), 0.5f, false);
                            landingSituations(players.get(0));
                        }
                    });
                } else { // If player is in jail
                    GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                    bDialog.setTitle("Carcel");
                    bDialog.setMessage("Estas en la carcel. Elija lo que desea hacer");
                    bDialog.addButton("Dados");
                    bDialog.addButton("Pagar 50€");
                    if (players.get(0).getnGetOutOfJailFreeCards()>0) {
                        bDialog.addButton("Usar carta");
                    }
                    bDialog.build().show();
                    bDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            if (button==0) { // If the player opts to throw the dice
                                GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                                bDialog.setTitle(players.get(0).getName()+" ha tirado dados");
                                bDialog.setMessage("Dado 1: "+number+"\nDado 2: "+number2);
                                bDialog.addButton("OK");
                                bDialog.build().show();
                                bDialog.setClickListener(new ButtonClickListener() {
                                    @Override
                                    public void click(int button) {
                                        if (number==number2) {
                                            players.get(0).setInJail(false);
                                            players.get(0).playerMovement((number+number2), 0.5f, false);
                                            landingSituations(players.get(0));
                                        }
                                    }
                                });
                            } else if (button==1) { // If the player wants to pay his way out of jail
                                players.get(0).setMoney(players.get(0).getMoney()-50);
                                players.get(0).setInJail(false);
                                GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                                bDialog.setTitle(players.get(0).getName()+" ha tirado dados");
                                bDialog.setMessage("Dado 1: "+number+"\nDado 2: "+number2);
                                bDialog.addButton("OK");
                                bDialog.build().show();
                                bDialog.setClickListener(new ButtonClickListener() {
                                    @Override
                                    public void click(int button) {
                                        players.get(0).playerMovement((number+number2), 0.5f, false);
                                        landingSituations(players.get(0));
                                    }
                                });
                            } else if (button==2) { // If the player wants to use his 'Get out of jail free' card
                                players.get(0).setnGetOutOfJailFreeCards(players.get(0).getnGetOutOfJailFreeCards()-1);
                                players.get(0).setInJail(false);
                                GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                                bDialog.setTitle(players.get(0).getName()+" ha tirado dados");
                                bDialog.setMessage("Dado 1: "+number+"\nDado 2: "+number2);
                                bDialog.addButton("OK");
                                bDialog.build().show();
                                bDialog.setClickListener(new ButtonClickListener() {
                                    @Override
                                    public void click(int button) {
                                        players.get(0).playerMovement((number+number2), 0.5f, false);
                                        landingSituations(players.get(0));
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });

        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                bDialog.setTitle("Propiedad Comprada");
                Property prop=board[players.get(0).getBoardPosition()].getProperty();
                bDialog.setMessage(players.get(0).getName()+" ha comprado "+prop.getName()+" por "+prop.getValue()+"€");
                bDialog.addButton("OK");
                bDialog.build().show();
                bDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        players.get(0).buyProperty(board);

                        buyButton.setVisible(false);
                        auctionButton.setVisible(false);
                        endButton.setVisible(true);
                    }
                });
            }
        });

        endButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO AI Function here
                endButton.setVisible(false);
                for (int i=1; i<players.size(); i++) {
                    final int aiPlayer=i;
                    Random r = new Random();
                    final int number = r.nextInt(6) + 1;
                    final int number2 = r.nextInt(6) + 1;
                    if (!players.get(aiPlayer).isInJail()) { // If player isn't in jail
                        GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                        bDialog.setTitle(players.get(aiPlayer).getName()+" ha tirado dados");
                        bDialog.setMessage("Dado 1: "+number+"\nDado 2: "+number2);
                        bDialog.addButton("OK");
                        bDialog.build().show();
                        bDialog.setClickListener(new ButtonClickListener() {
                            @Override
                            public void click(int button) {
                                players.get(aiPlayer).playerMovement((number+number2), 0.5f, false);
                                landingSituations(players.get(aiPlayer));
                            }
                        });
                    } else { // If AI Player is in jail
                        // The order of preference for the AI Player to get out of jail is as follows:
                        // 1º - The AI will check whether he has 'Get out of jail free' cards. If true, he will use them
                        // 2º - If 1º option is false, he will check whether he has the amount necessary to pay the fine (50€). If true, he will pay the fine
                        // 3º - If 2º option is false, he has no other option than throwing the dice. If he lands doubles, he will get out of jail.
                        if (players.get(aiPlayer).getnGetOutOfJailFreeCards()>0) { // 1º Option
                            players.get(aiPlayer).setnGetOutOfJailFreeCards(players.get(aiPlayer).getnGetOutOfJailFreeCards()-1);
                            GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                            bDialog.setTitle(players.get(aiPlayer).getName()+" ha salido de la cárcel");
                            bDialog.setMessage(players.get(aiPlayer).getName()+" ha salido de la cárcel usando su carta 'Salir de la cárcel gratis'");
                            bDialog.addButton("OK");
                            bDialog.setClickListener(new ButtonClickListener() {
                                @Override
                                public void click(int button) {
                                    GDXButtonDialog dialogDiceThrow = dialogs.newDialog(GDXButtonDialog.class);
                                    dialogDiceThrow.setTitle(players.get(aiPlayer).getName()+" ha tirado dados");
                                    dialogDiceThrow.setMessage("Dado 1: "+number+"\nDado 2: "+number2);
                                    dialogDiceThrow.addButton("OK");
                                    dialogDiceThrow.build().show();
                                    dialogDiceThrow.setClickListener(new ButtonClickListener() {
                                        @Override
                                        public void click(int button) {
                                            players.get(aiPlayer).playerMovement((number+number2), 0.5f, false);
                                            landingSituations(players.get(aiPlayer));
                                        }
                                    });
                                }
                            });
                        } else if (players.get(aiPlayer).getMoney()>=50) { // 2º Option
                            players.get(aiPlayer).setMoney(players.get(aiPlayer).getMoney()-50);
                            GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                            bDialog.setTitle(players.get(aiPlayer).getName()+" ha salido de la cárcel");
                            bDialog.setMessage(players.get(aiPlayer).getName()+" ha salido de la cárcel pagando 50€");
                            bDialog.addButton("OK");
                            bDialog.setClickListener(new ButtonClickListener() {
                                @Override
                                public void click(int button) {
                                    GDXButtonDialog dialogDiceThrow = dialogs.newDialog(GDXButtonDialog.class);
                                    dialogDiceThrow.setTitle(players.get(aiPlayer).getName()+" ha tirado dados");
                                    dialogDiceThrow.setMessage("Dado 1: "+number+"\nDado 2: "+number2);
                                    dialogDiceThrow.addButton("OK");
                                    dialogDiceThrow.build().show();
                                    dialogDiceThrow.setClickListener(new ButtonClickListener() {
                                        @Override
                                        public void click(int button) {
                                            players.get(aiPlayer).playerMovement((number+number2), 0.5f, false);
                                            landingSituations(players.get(aiPlayer));
                                        }
                                    });
                                }
                            });
                        } else { // 3º Option
                            GDXButtonDialog dialogDiceThrow = dialogs.newDialog(GDXButtonDialog.class);
                            dialogDiceThrow.setTitle(players.get(aiPlayer).getName()+" ha tirado dados");
                            dialogDiceThrow.setMessage("Dado 1: "+number+"\nDado 2: "+number2);
                            dialogDiceThrow.addButton("OK");
                            dialogDiceThrow.build().show();
                            dialogDiceThrow.setClickListener(new ButtonClickListener() {
                                @Override
                                public void click(int button) {
                                    if (number==number2) {
                                        GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                                        bDialog.setTitle(players.get(aiPlayer).getName()+" ha salido de la cárcel");
                                        bDialog.setMessage(players.get(aiPlayer).getName()+" ha salido de la cárcel tirando dobles");
                                        players.get(aiPlayer).playerMovement((number+number2), 0.5f, false);
                                        landingSituations(players.get(aiPlayer));
                                    }
                                }
                            });
                        }
                    }
                }
                diceButton.setVisible(true);
            }
        });

        chestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                players.get(0).communityChestCase(communityChest, dialogs);
                chestButton.setVisible(false);
                endButton.setVisible(true);
            }
        });

        chanceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                players.get(0).chanceCase(players.get(0), chance, dialogs);
                chanceButton.setVisible(false);
                endButton.setVisible(true);
            }
        });

        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // TODO Save game to database
            }
        });

        screen.addListener(new StageListener(screen));
        Gdx.input.setInputProcessor(screen);
    }

    public static void landingSituations(final Player p) {
        switch (board[p.getBoardPosition()].getType()) {
            case CITY:
            case STATION:
                if (board[p.getBoardPosition()].getProperty().getOwner()==null) { // If property doesn't have an owner
                    if (!p.getName().contains("IA")) {
                        diceButton.setVisible(false);
                        buyButton.setVisible(true);
                        auctionButton.setVisible(true);
                    } else {
                        GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                        bDialog.setTitle("Propiedad Comprada");
                        Property prop=board[p.getBoardPosition()].getProperty();
                        bDialog.setMessage(p.getName()+" ha comprado "+prop.getName()+" por "+prop.getValue()+"€");
                        bDialog.addButton("OK");
                        bDialog.setClickListener(new ButtonClickListener() {
                            @Override
                            public void click(int button) {
                                p.buyProperty(board);
                            }
                        });
                        bDialog.build().show();
                    }
                } else { // If property has an owner
                    final Property prop=board[p.getBoardPosition()].getProperty();
                    if (!prop.getOwner().equals(p)) { // If the property isn't owned by the same player throwing the dice
                        GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                        bDialog.setTitle("Pagar Renta");
                        bDialog.setMessage(p.getName()+" es el dueño de "+prop.getName()+". " +
                                "Deberá pagar la renta de la propiedad: "+prop.getRentPrice()+"€");
                        bDialog.addButton("OK");
                        bDialog.setClickListener(new ButtonClickListener() {
                            @Override
                            public void click(int button) {
                                boolean ownerPaid=false;
                                boolean playerPaid=false;
                                for (int i=0; i<players.size(); i++) {
                                    if (!ownerPaid) {
                                        if (players.get(i).equals(prop.getOwner())) {
                                            players.get(i).setMoney(players.get(i).getMoney()+prop.getRentPrice());
                                            ownerPaid=true;
                                        }
                                    }
                                    if (!playerPaid) {
                                        if (players.get(i).equals(p)) {
                                            players.get(i).setMoney(players.get(i).getMoney()-prop.getRentPrice());
                                            playerPaid=true;
                                        }
                                    }
                                }
                            }
                        });
                        bDialog.build().show();
                    }
                }
                break;
            case COMMUNITY_CHEST:
                if (!p.getName().contains("IA")) {
                    chestButton.setVisible(true);
                    diceButton.setVisible(false);
                } else {
                    p.communityChestCase(communityChest, dialogs);
                }
                break;
            case CHANCE:
                if (!p.getName().contains("IA")) {
                    chanceButton.setVisible(true);
                    diceButton.setVisible(false);
                } else {
                    p.chanceCase(p, chance, dialogs);
                }
                break;
            case GO:
                p.setMoney(p.getMoney()+200);
                break;
            case TAXES:
                if (p.getBoardPosition()==4) {
                    GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                    bDialog.setTitle("Tasas");
                    bDialog.setMessage(p.getName()+" debe cobrar el IVA. Pagar el 21% de su cuenta bancaria.");
                    bDialog.addButton("OK");

                    bDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            if (!p.getName().contains("IA")) {
                                endButton.setVisible(true);
                            }
                            p.setMoney((int) (p.getMoney()*0.79));
                        }
                    });
                    bDialog.build().show();
                } else if (p.getBoardPosition()==36) {
                    GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                    bDialog.setTitle("Tasas");
                    bDialog.setMessage("Gastos mensuales. "+p.getName()+" debe pagar 100€ de su cuenta bancaria.");
                    bDialog.addButton("OK");

                    bDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            if (!p.getName().contains("IA")) {
                                endButton.setVisible(true);
                            }
                            p.setMoney(p.getMoney()-100);
                        }
                    });
                    bDialog.build().show();
                }
                break;
            case NO_PARKING:
                GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                bDialog.setTitle("Carcel");
                bDialog.setMessage(p.getName()+" ha aparcado el una zona de no parking. Váyase a la cárcel");
                bDialog.addButton("OK");

                bDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        if (!p.getName().contains("IA")) {
                            endButton.setVisible(true);
                        }
                        p.playerMovement(p.getBoardPosition()-10, 3f, true);
                        p.setInJail(true);
                    }
                });
                bDialog.build().show();
                break;
            case JAIL_VISIT:
            case SPA:
                if (!p.getName().contains("IA")) {
                    diceButton.setVisible(false);
                    endButton.setVisible(true);
                }
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
        spriteBatch.begin();
        for (int i=0; i<players.size(); i++) {
            if (i==0) {
                fontText.draw(spriteBatch, "Nombre: "+players.get(i).getName()+"\n " +
                        "Figura: "+players.get(i).getSelectedPiece()+"\n" +
                        "Dinero: "+players.get(i).getMoney()+"€", Gdx.graphics.getWidth()/1.3257f, Gdx.graphics.getHeight()/1.038f);
            } else {
                fontText.draw(spriteBatch, "Nombre: "+players.get(i).getName()+"\n " +
                        "Figura: "+players.get(i).getSelectedPiece()+"\n" +
                        "Dinero: "+players.get(i).getMoney()+"€", Gdx.graphics.getWidth()/1.3257f, Gdx.graphics.getHeight()/1.038f-(Gdx.graphics.getHeight()/4.32f)*i);
            }
        }
        spriteBatch.end();
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
