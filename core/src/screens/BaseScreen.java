package screens;

import com.badlogic.gdx.Application;
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

import java.awt.Button;
import java.util.ArrayList;
import java.util.Collections;
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
import database.Database;
import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.GDXDialogsSystem;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.dialogs.GDXTextPrompt;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;
import de.tomgrill.gdxdialogs.core.listener.TextPromptListener;
import dialogs.GDXSpinnerDialog;
import dialogs.SpinnerDialogListener;
import listeners.StageListener;

public abstract class BaseScreen implements Screen {
    protected MyGame game;
    protected Stage screen;
    protected Texture background;
    private static GDXDialogs dialogs; // Dialogs for the game
    private static ArrayList<Player> players; // Players taking part in the gmae
    private SpriteBatch spriteBatch;
    private static Square[] board; // Board containing all the properties int the game
    private static String[] communityChest; // All cases of community chest
    private static String[] chance; // All cases of chance
    private StartGameButton startButton; // Button for starting the game
    private static BuyButton buyButton; // Button for buying a property
    private static AuctionButton auctionButton; // Button for auctioning a property
    private static ThrowDiceButton diceButton; // Button for throwing a dice
    private static MortgageButton mortgageButton; // Button for mortgaging a property
    private static UnmortgageButton unmortgageButton; // Button for unmortgaging a property
    private static SaveButton saveButton; // Button for saving the progress of the game
    private static CommunityChestButton chestButton; // Button for the community chest case
    private static ChanceButton chanceButton; // Button for the chance case
    private static EndTurnButton endButton; // Button for ending the turn
    private BitmapFont fontText; // Text displaying details of the player (Player name, Piece selected, money)
    private Database monopolyDatabase; // Database where all the progress is saved
    private static byte turn; // Turn of the player
    private static GDXButtonDialog buttonDialog; // AlertDialog wih a button

    /**
     * Main constructor of BaseScreen
     * Here we initialize everything that contains the game
     * @param mg Game
     * @param arrayPlayers ArrayList of players taking part in the game
     * @param db Database of the game
     */
    protected BaseScreen(MyGame mg, ArrayList<Player> arrayPlayers, Database db) {
        dialogs = GDXDialogsSystem.install();
        buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
        if(Gdx.app.getType() == Application.ApplicationType.Android) {
            // To add our own customized AlertDialog in the game, we have to register our created dialog in dialogs
            dialogs.registerDialog("dialogs.GDXSpinnerDialog", "dialog.AndroidDGXSpinnerDialog");
        }
        this.game = mg;
        this.monopolyDatabase=db;
        turn=0;
        board=Square.initSquares();
        communityChest=Square.initCommunityChest();
        chance=Square.initChance();
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
        saveButton.setVisible(false);

        // If the arrayPlayers pased by parameter is empty, we add the Players with it's default values
        // This means that the user is staring a new game
        if (!arrayPlayers.isEmpty()) {
            for (int i=0; i<arrayPlayers.size(); i++) {
                players.add(new Player(arrayPlayers.get(i), Gdx.graphics.getWidth()/1.6704f, Gdx.graphics.getHeight()/6.75f));
                screen.addActor(players.get(i));
            }
        } else {
            // If the arrayPlayers pased by parameter is not empty, we add the Players with values taken form the database
            // This means that the user is restarting a previous game
            arrayPlayers=db.loadGame(board);
            for (int i=0; i<arrayPlayers.size(); i++) {
                players.add(new Player(arrayPlayers.get(i), Gdx.graphics.getWidth()/1.6704f, Gdx.graphics.getHeight()/6.75f));
                players.get(i).setBoardPosition(0);
                players.get(i).setMoney(arrayPlayers.get(i).getMoney());
                players.get(i).setInJail(arrayPlayers.get(i).isInJail());
                players.get(i).setBankrupt(arrayPlayers.get(i).isBankrupt());
                players.get(i).setnGetOutOfJailFreeCards(arrayPlayers.get(i).getnGetOutOfJailFreeCards());
                players.get(i).setPropertiesBought(arrayPlayers.get(i).getPropertiesBought());
                players.get(i).playerMovement(arrayPlayers.get(i).getBoardPosition(), 0f, false);
                screen.addActor(players.get(i));
            }
            turn=(byte)db.loadTurn();
        }

        // Button that starts the game
        // Once it's clicked, it never shows up again (Unless the user is restarting a previous game)
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                startButton.setVisible(false);
                if (turn == 0) {
                    diceButton.setVisible(true);
                    mortgageButton.setVisible(true);
                    unmortgageButton.setVisible(true);
                    saveButton.setVisible(true);
                } else {
                    AiFunction();
                    if (turn==0) {
                        diceButton.setVisible(true);
                        mortgageButton.setVisible(true);
                        unmortgageButton.setVisible(true);
                        saveButton.setVisible(true);
                        endButton.setVisible(false);
                    } else {
                        endButton.setVisible(true);
                    }
                }
            }
        });

        // Button where the user bids for a property.
        // The user inputs a number on the dialog, while the AI generates a random number between the property value and property value+100
        // The player with the highest bid ends up buying the property
        auctionButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                final GDXTextPrompt textPrompt = dialogs.newDialog(GDXTextPrompt.class);
                final Property property=board[players.get(0).getBoardPosition()].getProperty();

                textPrompt.setTitle("Subasta");
                textPrompt.setMessage("Escriba su puja máxima de la propiedad\n" +
                        "Propiedad: "+property.getName()+"\n" +
                        "Valor: "+property.getValue()+"€");
                textPrompt.setConfirmButtonLabel("Pujar");

                textPrompt.setTextPromptListener(new TextPromptListener() {
                    @Override
                    public void confirm(String text) {
                        try {
                            final int inputValue=Integer.parseInt(text);
                            if (inputValue<players.get(0).getMoney()) {
                                Random r=new Random();
                                ArrayList<Integer> listBets=new ArrayList<>();
                                listBets.add(Integer.parseInt(text));
                                for (int i=0; i<players.size()-1; i++) {
                                    listBets.add(r.nextInt(100)+property.getValue());
                                }
                                final int maxBet=Collections.max(listBets);
                                final int nPlayer=listBets.indexOf(maxBet);

                                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                                showBasicDialog(buttonDialog, "Subasta",
                                        "La puja máxima fue de "+maxBet+"€, realizado por "+players.get(nPlayer).getName());
                                buttonDialog.setClickListener(new ButtonClickListener() {
                                    @Override
                                    public void click(int button) {
                                        board[players.get(nPlayer).getBoardPosition()].getProperty().setOwner(players.get(nPlayer));
                                        players.get(nPlayer).getPropertiesBought().add(board[players.get(nPlayer).getBoardPosition()].getProperty());
                                        players.get(nPlayer).setMoney(players.get(nPlayer).getMoney()-maxBet);
                                        buyButton.setVisible(false);
                                        auctionButton.setVisible(false);
                                        endButton.setVisible(true);
                                    }
                                });
                            } else {
                                // If the user has written a number higher than his balance, A dialog error will be shown
                                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                                showBasicDialog(buttonDialog, "Error", "El valor introducido es mayor a la cantidad de dinero que dispone. Introduzca un valor válido.");
                            }
                        } catch (NumberFormatException ex) {
                            // If the user has written something other than a number, a dalog error will be shown
                            buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                            showBasicDialog(buttonDialog, "Error", "Ha introducido un valor inválido. Por favor, intente de nuevo");
                        }
                    }

                    @Override
                    public void cancel() {
                    }
                });

                textPrompt.build().show();
            }
        });

        // Button where the user can mortgage his properties
        mortgageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // First, it will check if there are any properties that can be mortgaged
                final ArrayList<Property> propertiesInUse=new ArrayList<>();
                for (Property p: players.get(0).getPropertiesBought()) {
                    if (!p.isMortgaged()) {
                        propertiesInUse.add(p);
                    }
                }

                if (propertiesInUse.isEmpty()) { // If player doesn't have any properties mortgage-able
                    buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                    showBasicDialog(buttonDialog, "Hipoteca", "No dispone de ningúna propiedad para hipotecar");
                    buttonDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {

                        }
                    });
                } else { // If player has properties mortgage-able
                    final GDXSpinnerDialog spinnerDialog=dialogs.newDialog(GDXSpinnerDialog.class);
                    spinnerDialog.setTitle("Hipotecar");
                    spinnerDialog.setMessage("Elija la propiedad que desea hipotecar");
                    spinnerDialog.setOKButtonText("OK");
                    spinnerDialog.setCancelButtonText("Cancelar");
                    spinnerDialog.setList(propertiesInUse, false);
                    spinnerDialog.setSpinnerDialogListener(new SpinnerDialogListener() {
                        @Override
                        public void confirm() {
                            Property prop=propertiesInUse.get(spinnerDialog.getSelectedPosition());
                            buttonDialog=dialogs.newDialog(GDXButtonDialog.class);
                            showBasicDialog(buttonDialog, "Propiedad hipotecada",
                                    "Se ha hipotecado la propiedad "+prop.getName()+" por un valor de "+prop.getMortgagePrice()+"€");
                            players.get(0).getPropertiesBought().get(players.get(0).getPropertiesBought().indexOf(prop)).setMortgaged(true);
                            players.get(0).setMoney(players.get(0).getMoney()+prop.getMortgagePrice());
                        }

                        @Override
                        public void cancel() {

                        }
                    });

                    spinnerDialog.build().show();
                }
            }
        });

        // Button where the user can unmortgage properties if available
        // Similar to the mortage button, with the only diference being the properties shown in the spinner
        unmortgageButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // First, it will check if there are any properties that can be unmortgaged
                final ArrayList<Property> propertiesMortgaged=new ArrayList<>();
                for (Property p: players.get(0).getPropertiesBought()) {
                    if (p.isMortgaged()) {
                        propertiesMortgaged.add(p);
                    }
                }

                if (propertiesMortgaged.isEmpty()) { // If player doesn't have any properties mortgage-able
                    buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                    showBasicDialog(buttonDialog, "Hipoteca", "No dipsone de ningúna propiedad para deshipotecar");
                } else { // If player has properties mortgage-able
                    final GDXSpinnerDialog spinnerDialog=dialogs.newDialog(GDXSpinnerDialog.class);
                    spinnerDialog.setTitle("Deshipotecar");
                    spinnerDialog.setMessage("Elija la propiedad que desea deshipotecar");
                    spinnerDialog.setOKButtonText("OK");
                    spinnerDialog.setCancelButtonText("Cancelar");
                    spinnerDialog.setList(propertiesMortgaged, true);
                    spinnerDialog.setSpinnerDialogListener(new SpinnerDialogListener() {
                        @Override
                        public void confirm() {
                            Property prop=propertiesMortgaged.get(spinnerDialog.getSelectedPosition());
                            if (players.get(0).getMoney()>=prop.getRedeemPrice()) {
                                buttonDialog=dialogs.newDialog(GDXButtonDialog.class);
                                showBasicDialog(buttonDialog, "Propiedad deshipotecada",
                                        "Se ha deshipotecado la propiedad "+prop.getName()+" por un valor de "+prop.getRedeemPrice()+"€");
                                players.get(0).getPropertiesBought().get(players.get(0).getPropertiesBought().indexOf(prop)).setMortgaged(false);
                                players.get(0).setMoney(players.get(0).getMoney()-prop.getRedeemPrice());
                            } else {
                                buttonDialog=dialogs.newDialog(GDXButtonDialog.class);
                                showBasicDialog(buttonDialog, "Error deshipoteca",
                                        "No dispone del dinero necesario para deshipotecar la propiedad. \n" +
                                                "Valor para deshipotecar "+prop.getName()+": "+prop.getRedeemPrice()+"€+\n" +
                                                "Su dinero: "+players.get(0).getMoney()+"€");
                            }
                        }

                        @Override
                        public void cancel() {

                        }
                    });

                    spinnerDialog.build().show();
                }
            }
        });

        // Button where the user throws a dice
        // Once the dice is thrown, the player will advance to a certain position and depending on where it lands, it will do a specific thing
        diceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Random r = new Random();
                final int number = r.nextInt(6) + 1;
                final int number2 = r.nextInt(6) + 1;

                if (!players.get(0).isInJail()) { // If player isn't in jail
                    buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                    showBasicDialog(buttonDialog, players.get(0).getName()+" ha tirado dados", "Dado 1: "+number+"\nDado 2: "+number2);
                    buttonDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            players.get(0).playerMovement(number+number2, 0.5f, false);
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
                                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                                showBasicDialog(buttonDialog, players.get(0).getName()+" ha tirado dados", "Dado 1: "+number+"\nDado 2: "+number2);
                                buttonDialog.setClickListener(new ButtonClickListener() {
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
                                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                                showBasicDialog(buttonDialog, players.get(0).getName()+" ha tirado dados", "Dado 1: "+number+"\nDado 2: "+number2);
                                buttonDialog.setClickListener(new ButtonClickListener() {
                                    @Override
                                    public void click(int button) {
                                        players.get(0).playerMovement((number+number2), 0.5f, false);
                                        landingSituations(players.get(0));
                                    }
                                });
                            } else if (button==2) { // If the player wants to use his 'Get out of jail free' card
                                players.get(0).setnGetOutOfJailFreeCards(players.get(0).getnGetOutOfJailFreeCards()-1);
                                players.get(0).setInJail(false);
                                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                                showBasicDialog(buttonDialog, players.get(0).getName()+" ha tirado dados", "Dado 1: "+number+"\nDado 2: "+number2);
                                buttonDialog.setClickListener(new ButtonClickListener() {
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
                turn++;
            }
        });

        // Button where the player buys a property
        buyButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Property prop=board[players.get(0).getBoardPosition()].getProperty();
                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                showBasicDialog(buttonDialog, "Propiedad Comprada", players.get(0).getName()+" ha comprado "+prop.getName()+" por "+prop.getValue()+"€");
                buttonDialog.setClickListener(new ButtonClickListener() {
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

        // Button where the user ends his turn
        // After the button is pressed, the AI will do the same functions (throwing dice, buying property, etc.)
        endButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AiFunction();
            }
        });

        // Button for the community chest case
        chestButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                players.get(0).communityChestCase(communityChest, dialogs, players.get(0));
                chestButton.setVisible(false);
                endButton.setVisible(true);
            }
        });

        // Button for the chance case
        chanceButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                players.get(0).chanceCase(players.get(0), chance, dialogs);
                chanceButton.setVisible(false);
                if (board[players.get(0).getBoardPosition()].getType()==Square.SquareType.CITY||board[players.get(0).getBoardPosition()].getType()==Square.SquareType.STATION) {
                    endButton.setVisible(false);
                } else {
                    endButton.setVisible(true);
                }
            }
        });

        // Button where you can save the game progress
        // If the game is saved succesfully, a dialog will show
        saveButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                monopolyDatabase.saveGame(players, board, turn);
                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                showBasicDialog(buttonDialog,"Partida guardada", "Se ha guardado la partida");
            }
        });

        screen.addListener(new StageListener(screen));
        Gdx.input.setInputProcessor(screen);
    }

    /**
     * Function where certain situations will happen depending on where the player lands on the board
     * @param p The player affected
     */
    public static void landingSituations(final Player p) {
        switch (board[p.getBoardPosition()].getType()) {
            case CITY:
            case STATION:
                if (board[p.getBoardPosition()].getProperty().getOwner()==null) { // If property doesn't have an owner
                    if (!p.getName().contains("IA")) { // If the player isn't an AI
                        diceButton.setVisible(false);
                        endButton.setVisible(false);
                        buyButton.setVisible(true);
                        auctionButton.setVisible(true);
                    } else { // If the player is an AI
                        Property prop=board[p.getBoardPosition()].getProperty();
                        if (prop.getValue()<=p.getMoney()) { // If player has enough money to buy the property
                            buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                            showBasicDialog(buttonDialog, "Propiedad Comprada", p.getName()+" ha comprado "+prop.getName()+" por "+prop.getValue()+"€");
                            buttonDialog.setClickListener(new ButtonClickListener() {
                                @Override
                                public void click(int button) {
                                    p.buyProperty(board);
                                }
                            });
                        } else {
                            p.autoMortgageProperties(prop);
                            if (p.isBankrupt()) {
                                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                                showBasicDialog(buttonDialog, p.getName()+" declara bancarrota",
                                        "Debido a su situación financiera, "+p.getName()+" ha declarado bancarrota. " +
                                                "Todas sus propiedades se pueden comprar");
                                buttonDialog.setClickListener(new ButtonClickListener() {
                                    @Override
                                    public void click(int button) {
                                        for (Square sq: board) {
                                            if (sq.getType()==Square.SquareType.CITY||sq.getType()==Square.SquareType.STATION) {
                                                if (sq.getProperty().getOwner().getName().equals(p.getName())) {
                                                    sq.getProperty().setOwner(null);
                                                }
                                            }
                                        }
                                        players.get(players.indexOf(p)).remove();
                                    }
                                });
                            } else {
                                p.buyProperty(board);
                            }
                        }
                    }
                } else { // If property has an owner
                    final Property prop=board[p.getBoardPosition()].getProperty();
                    if (!prop.getOwner().getName().equals(p.getName())) { // If the property isn't owned by the same player throwing the dice
                        if (!prop.isMortgaged()) { // If property isn't mortgaged, the rent will have to be paid
                            if (p.getMoney()>=prop.getRentPrice()) { // If the AI player has enough money for the rent
                                p.payRent(dialogs, prop, players, p);
                            } else {
                                p.autoMortgageProperties(prop);
                                if (p.isBankrupt()) {
                                    buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                                    showBasicDialog(buttonDialog, p.getName()+" declara bancarrota",
                                            "Debido a su situación financiera, "+p.getName()+" ha declarado bancarrota. " +
                                                    "Todas sus propiedades se pueden comprar");
                                    p.setBankrupt(true);
                                } else {
                                    p.payRent(dialogs, prop, players, p);
                                }
                            }
                        } else { // If property is mortgaged, rent will not have to be paid
                            buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                            showBasicDialog(buttonDialog, prop.getName(), "Dicha propiedad esta hipotecada. Por eso, no hace falta pagar la renta");
                            buttonDialog.setClickListener(new ButtonClickListener() {
                                @Override
                                public void click(int button) {
                                    for (Square sq: board) {
                                        if (sq.getType()==Square.SquareType.CITY||sq.getType()==Square.SquareType.STATION) {
                                            if (sq.getProperty().getOwner().getName().equals(p.getName())) {
                                                sq.getProperty().setOwner(null);
                                            }
                                        }
                                    }
                                    players.get(players.indexOf(p)).remove();
                                }
                            });
                        }
                    }
                    if (!p.getName().contains("IA")) {
                        endButton.setVisible(true);
                        diceButton.setVisible(false);
                    }
                }
                break;
            case COMMUNITY_CHEST:
                if (!p.getName().contains("IA")) {
                    chestButton.setVisible(true);
                    diceButton.setVisible(false);
                } else {
                    p.communityChestCase(communityChest, dialogs, p);
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
                break;
            case TAXES:
                if (p.getBoardPosition()==4) {
                    buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                    showBasicDialog(buttonDialog, "Tasas", p.getName()+" debe cobrar el IVA. Pagar el 21% de su cuenta bancaria.");
                    buttonDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            if (!p.getName().contains("IA")) {
                                endButton.setVisible(true);
                            }
                            p.setMoney((int) (p.getMoney()*0.79));
                        }
                    });
                } else if (p.getBoardPosition()==36) {
                    buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                    showBasicDialog(buttonDialog, "Tasas", "Gastos mensuales. "+p.getName()+" debe pagar 100€ de su cuenta bancaria.");
                    buttonDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            if (!p.getName().contains("IA")) {
                                endButton.setVisible(true);
                            }
                            p.setMoney(p.getMoney()-100);
                        }
                    });
                }
                break;
            case NO_PARKING:
                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                showBasicDialog(buttonDialog, "Carcel", p.getName()+" ha aparcado el una zona de no parking. Váyase a la cárcel");
                buttonDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        if (!p.getName().contains("IA")) {
                            endButton.setVisible(true);
                        }
                        p.playerMovement(p.getBoardPosition()-10, 3f, true);
                        p.setInJail(true);
                    }
                });
                break;
            case JAIL_VISIT:
            case SPA:
                if (!p.getName().contains("IA")) {
                    diceButton.setVisible(false);
                    endButton.setVisible(true);
                }
        }
    }

    private static void showBasicDialog(GDXButtonDialog bDialog, String title, String message) {
        bDialog.setTitle(title);
        bDialog.setMessage(message);
        bDialog.addButton("OK");
        bDialog.build().show();
    }

    private static void AiFunction() {
        if (!players.get(turn).isBankrupt()) {
            final int aiPlayer=turn;
            endButton.setVisible(false);
            Random r = new Random();
            final int number = r.nextInt(6) + 1;
            final int number2 = r.nextInt(6) + 1;
            if (!players.get(aiPlayer).isInJail()) {
                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                showBasicDialog(buttonDialog, players.get(aiPlayer).getName()+" ha tirado dados", "Dado 1: "+number+"\nDado 2: "+number2);
                buttonDialog.setClickListener(new ButtonClickListener() {
                    @Override
                    public void click(int button) {
                        players.get(aiPlayer).playerMovement(number+number2, 0.5f, false);
                        landingSituations(players.get(aiPlayer));
                    }
                });
            } else {
                // If the AI Player is in jail
                // The order of preference for the AI Player to get out of jail is as follows:
                // 1º - The AI will check whether he has 'Get out of jail free' cards. If true, he will use them
                // 2º - If 1º option is false, he will check whether he has the amount necessary to pay the fine (50€). If true, he will pay the fine
                // 3º - If 2º option is false, he has no other option than throwing the dice. If he lands doubles, he will get out of jail.
                if (players.get(aiPlayer).getnGetOutOfJailFreeCards()>0) { // 1º Option
                    players.get(aiPlayer).setnGetOutOfJailFreeCards(players.get(turn).getnGetOutOfJailFreeCards()-1);
                    buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                    showBasicDialog(buttonDialog, players.get(aiPlayer).getName()+" ha salido de la cárcel",
                            players.get(aiPlayer).getName()+" ha salido de la cárcel usando su carta 'Salir de la cárcel gratis'");
                    buttonDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                            showBasicDialog(buttonDialog, players.get(aiPlayer).getName()+" ha tirado dados",
                                    "Dado 1: "+number+"\nDado 2: "+number2);
                            buttonDialog.setClickListener(new ButtonClickListener() {
                                @Override
                                public void click(int button) {
                                    players.get(aiPlayer).playerMovement((number+number2), 0.5f, false);
                                    landingSituations(players.get(aiPlayer));
                                    players.get(aiPlayer).setInJail(false);
                                }
                            });
                        }
                    });
                } else if (players.get(aiPlayer).getMoney()>=50) { // 2º Option
                    players.get(aiPlayer).setMoney(players.get(aiPlayer).getMoney()-50);
                    buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                    showBasicDialog(buttonDialog, players.get(aiPlayer).getName()+" ha salido de la cárcel",
                            players.get(aiPlayer).getName()+" ha salido de la cárcel pagando 50€");
                    buttonDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                            showBasicDialog(buttonDialog, players.get(aiPlayer).getName()+" ha tirado dados",
                                    "Dado 1: "+number+"\nDado 2: "+number2);
                            buttonDialog.setClickListener(new ButtonClickListener() {
                                @Override
                                public void click(int button) {
                                    players.get(aiPlayer).playerMovement((number+number2), 0.5f, false);
                                    landingSituations(players.get(aiPlayer));
                                    players.get(aiPlayer).setInJail(false);
                                }
                            });
                        }
                    });
                } else { // 3º Option
                    buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                    showBasicDialog(buttonDialog, players.get(aiPlayer).getName()+" ha tirado dados",
                            "Dado 1: "+number+"\nDado 2: "+number2);
                    buttonDialog.setClickListener(new ButtonClickListener() {
                        @Override
                        public void click(int button) {
                            if (number==number2) {
                                buttonDialog = dialogs.newDialog(GDXButtonDialog.class);
                                showBasicDialog(buttonDialog, players.get(aiPlayer).getName()+" ha salido de la cárcel",
                                        players.get(aiPlayer).getName()+" ha salido de la cárcel tirando dobles");
                                players.get(aiPlayer).playerMovement((number+number2), 0.5f, false);
                                landingSituations(players.get(aiPlayer));
                                players.get(aiPlayer).setInJail(false);
                            }
                        }
                    });
                }
            }
        }
        turn++;
        if (turn==players.size()) {
            turn=0;
            diceButton.setVisible(true);
            mortgageButton.setVisible(true);
            unmortgageButton.setVisible(true);
            saveButton.setVisible(true);
        } else {
            endButton.setVisible(true);
            mortgageButton.setVisible(false);
            unmortgageButton.setVisible(false);
            saveButton.setVisible(true);
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
        for (int i=0; i<players.size(); i++) { // Text with the player details will be drawn on the screen here
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
