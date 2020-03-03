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

import java.util.ArrayList;
import java.util.Random;
import buttons.AuctionButton;
import buttons.BuyButton;
import buttons.ChanceButton;
import buttons.CommunityChestButton;
import buttons.EndTurnButton;
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
    private static GDXDialogs dialogs;
    private static ArrayList<Player> playablePlayers;
    private ArrayList<Player> players;
    private PlayerText testingText;
    private static Square[] board;
    private static String[] communityChest;
    private static String[] chance;
    private StartGameButton startButton;
    private static BuyButton buyButton;
    private static AuctionButton auctionButton;
    private static ThrowDiceButton diceButton;
    private MortgageButton mortgageButton;
    private UnmortgageButton unmortgageButton;
    private static CommunityChestButton chestButton;
    private static ChanceButton chanceButton;
    private static EndTurnButton endButton;
    private int num;

    protected BaseScreen(MyGame mg, ArrayList<Player> arrayPlayers) {
        this.game = mg;
        this.players = arrayPlayers;
        board=Square.initSquares();
        communityChest=Square.initCommunityChest();
        chance=Square.initChance();
        dialogs = GDXDialogsSystem.install();
        screen = new Stage(new FillViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        startButton = new StartGameButton(Gdx.graphics.getWidth()/3.7963f, Gdx.graphics.getHeight()/2.16f);
        buyButton = new BuyButton(Gdx.graphics.getWidth()/4.64f, Gdx.graphics.getHeight()/2.16f);
        auctionButton = new AuctionButton(Gdx.graphics.getWidth()/2.784f, Gdx.graphics.getHeight()/2.16f);
        diceButton = new ThrowDiceButton(Gdx.graphics.getWidth()/3.7963f, Gdx.graphics.getHeight()/2.16f);
        mortgageButton = new MortgageButton(Gdx.graphics.getWidth()/6.96f, Gdx.graphics.getHeight()/36f);
        unmortgageButton = new UnmortgageButton(Gdx.graphics.getWidth()/2.784f, Gdx.graphics.getHeight()/36f);
        chestButton=new CommunityChestButton(Gdx.graphics.getWidth()/3.7963f, Gdx.graphics.getHeight()/2.16f);
        chanceButton=new ChanceButton(Gdx.graphics.getWidth()/3.7963f, Gdx.graphics.getHeight()/2.16f);
        endButton=new EndTurnButton(Gdx.graphics.getWidth()/3.7963f, Gdx.graphics.getHeight()/2.16f);

        playablePlayers=new ArrayList<>();

        screen.addActor(diceButton);
        screen.addActor(buyButton);
        screen.addActor(auctionButton);
        screen.addActor(mortgageButton);
        screen.addActor(unmortgageButton);
        screen.addActor(startButton);
        screen.addActor(chestButton);
        screen.addActor(chanceButton);
        screen.addActor(endButton);
        buyButton.setVisible(false);
        auctionButton.setVisible(false);
        diceButton.setVisible(false);
        mortgageButton.setVisible(false);
        unmortgageButton.setVisible(false);
        chestButton.setVisible(false);
        chanceButton.setVisible(false);
        endButton.setVisible(false);
        // screen.addActor(testingText);

        for (int i=0; i<arrayPlayers.size(); i++) {
            playablePlayers.add(new Player(arrayPlayers.get(i).getName(), "pieces/"+arrayPlayers.get(i).getSelectedPiece()+".png",
                    Gdx.graphics.getWidth()/1.6704f, Gdx.graphics.getHeight()/6.75f));
            screen.addActor(playablePlayers.get(i));
        }

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
                        // TODO Set auction dialog
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
                Gdx.app.log("Jugador IA", playablePlayers.get(1).getPropertiesBought()+"");
                Gdx.app.log("Jugador IA", playablePlayers.get(1)+"");
                Random r = new Random();
                final int number = r.nextInt(6) + 1;
                final int number2 = r.nextInt(6) + 1;

                if (!playablePlayers.get(0).isInJail()) { // If player isn't in jail
                    GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                    bDialog.setTitle(playablePlayers.get(0).getName()+" ha tirado dados");
                    bDialog.setMessage("Dado 1: "+number+"\nDado 2: "+number2);
                    bDialog.addButton("OK");
                    bDialog.build().show();
                    bDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            playablePlayers.get(0).playerMovement((number+number2), 0.5f, false);
                            landingSituations(playablePlayers.get(0));
                        }
                    });
                } else { // If player is in jail
                    GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                    bDialog.setTitle("Carcel");
                    bDialog.setMessage("Estas en la carcel. Elija lo que desea hacer");
                    bDialog.addButton("Dados");
                    bDialog.addButton("Pagar 50€");
                    if (playablePlayers.get(0).getnGetOutOfJailFreeCards()>0) {
                        bDialog.addButton("Usar carta");
                    }
                    bDialog.build().show();
                    bDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            if (button==0) { // If the player opts to throw de dice
                                GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                                bDialog.setTitle(playablePlayers.get(0).getName()+" ha tirado dados");
                                bDialog.setMessage("Dado 1: "+number+"\nDado 2: "+number2);
                                bDialog.addButton("OK");
                                bDialog.build().show();
                                bDialog.setClickListener(new ButtonClickListener() {
                                    @Override
                                    public void click(int button) {
                                        if (number==number2) {
                                            playablePlayers.get(0).setInJail(false);
                                            playablePlayers.get(0).playerMovement((number+number2), 0.5f, false);
                                            landingSituations(playablePlayers.get(0));
                                        }
                                    }
                                });
                            } else if (button==1) { // If the player wants to pay his way out of jail
                                playablePlayers.get(0).setMoney(playablePlayers.get(0).getMoney()-50);
                                playablePlayers.get(0).setInJail(false);
                                GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                                bDialog.setTitle(playablePlayers.get(0).getName()+" ha tirado dados");
                                bDialog.setMessage("Dado 1: "+number+"\nDado 2: "+number2);
                                bDialog.addButton("OK");
                                bDialog.build().show();
                                bDialog.setClickListener(new ButtonClickListener() {
                                    @Override
                                    public void click(int button) {
                                        playablePlayers.get(0).playerMovement((number+number2), 0.5f, false);
                                        landingSituations(playablePlayers.get(0));
                                    }
                                });
                            } else if (button==2) { // If the player wants to use his 'Get out of jail free' card
                                playablePlayers.get(0).setnGetOutOfJailFreeCards(playablePlayers.get(0).getnGetOutOfJailFreeCards()-1);
                                playablePlayers.get(0).setInJail(false);
                                GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                                bDialog.setTitle(playablePlayers.get(0).getName()+" ha tirado dados");
                                bDialog.setMessage("Dado 1: "+number+"\nDado 2: "+number2);
                                bDialog.addButton("OK");
                                bDialog.build().show();
                                bDialog.setClickListener(new ButtonClickListener() {
                                    @Override
                                    public void click(int button) {
                                        playablePlayers.get(0).playerMovement((number+number2), 0.5f, false);
                                        landingSituations(playablePlayers.get(0));
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
                Property prop=board[playablePlayers.get(0).getBoardPosition()].getProperty();
                bDialog.setMessage(playablePlayers.get(0).getName()+" ha comprado "+prop.getName()+" por "+prop.getValue()+"€");
                bDialog.addButton("OK");
                bDialog.build().show();
                bDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        playablePlayers.get(0).buyProperty(board);

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
                for (int i=1; i<playablePlayers.size(); i++) {
                    final int aiPlayer=i;
                    Random r = new Random();
                    final int number = r.nextInt(6) + 1;
                    final int number2 = r.nextInt(6) + 1;
                    if (!playablePlayers.get(aiPlayer).isInJail()) { // If player isn't in jail
                        GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
                        bDialog.setTitle(playablePlayers.get(aiPlayer).getName()+" ha tirado dados");
                        bDialog.setMessage("Dado 1: "+number+"\nDado 2: "+number2);
                        bDialog.addButton("OK");
                        bDialog.build().show();
                        bDialog.setClickListener(new ButtonClickListener() {
                            @Override
                            public void click(int button) {
                                playablePlayers.get(aiPlayer).playerMovement((number+number2), 0.5f, false);
                                landingSituations(playablePlayers.get(aiPlayer));
                            }
                        });
                    } else { // If player is in jail

                    }
                }
                diceButton.setVisible(true);
            }
        });

        chestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playablePlayers.get(0).communityChestCase(communityChest, dialogs);
                chestButton.setVisible(false);
                endButton.setVisible(true);
            }
        });

        chanceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                playablePlayers.get(0).chanceCase(playablePlayers.get(0), chance, dialogs);
                chanceButton.setVisible(false);
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
                                for (int i=0; i<playablePlayers.size(); i++) {
                                    if (!ownerPaid) {
                                        if (playablePlayers.get(i).equals(prop.getOwner())) {
                                            playablePlayers.get(i).setMoney(playablePlayers.get(i).getMoney()+prop.getRentPrice());
                                            ownerPaid=true;
                                        }
                                    }
                                    if (!playerPaid) {
                                        if (playablePlayers.get(i).equals(p)) {
                                            playablePlayers.get(i).setMoney(playablePlayers.get(i).getMoney()-prop.getRentPrice());
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
