package classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import de.tomgrill.gdxdialogs.core.GDXDialogs;
import de.tomgrill.gdxdialogs.core.dialogs.GDXButtonDialog;
import de.tomgrill.gdxdialogs.core.listener.ButtonClickListener;
import screens.BaseScreen;

/**
 * Class that acts as a player of the Monopoly match
 * @author Darash
 */
public class Player extends Actor implements Serializable {
    private Sprite sprite; // Sprite of the player
    private String name; // Player name
    private int boardPosition; // Position in the board
    private String selectedPiece; // Name of the selected piece by the player
    private int money; // Amount of money the player has
    private ArrayList<Property> propertiesBought; // Properties bought by the player
    private boolean isBankrupt; // Checks if the player is bankrupt
    private boolean isInJail; // Checks if the player is in jail
    private int nGetOutOfJailFreeCards; // Number of 'Get out of jail free' Cards the player has

    /**
     * Initial constructor of the Player class
     * At the beginning of the match, the player nor is bankrupt, nor is he in jail, nor does he have 'Get out of jail free' Cards, and nor does he have any properties
     * This constructor is used in the Android activity, since there are no sprites in the Activity
     * @param name Player name
     * @param piece Selected piece
     */
    public Player(String name, String piece) {
        this.name = name;
        this.selectedPiece=piece;
        this.boardPosition = 0;
        this.money = 3000;
        this.propertiesBought = new ArrayList<Property>();
        this.isBankrupt = false;
        this.isInJail = false;
        this.nGetOutOfJailFreeCards = 0;
    }

    /**
     * 2nd constructor of the Player class
     * This constructor will be used to initialize the player in the game
     * @param p Player
     * @param x Initial position x
     * @param y Initial position y
     */
    public Player(Player p, float x, float y) {
        this.name = p.getName();
        this.boardPosition = p.getBoardPosition();
        this.selectedPiece = p.getSelectedPiece();
        this.money = p.getMoney();
        this.propertiesBought = p.getPropertiesBought();
        this.isBankrupt = p.isBankrupt();
        this.isInJail = p.isInJail();
        this.nGetOutOfJailFreeCards = p.getnGetOutOfJailFreeCards();

        sprite=new Sprite(new Texture("pieces/"+selectedPiece.toLowerCase()+".png"));
        sprite.setBounds(x, y, Gdx.graphics.getWidth()/25f, Gdx.graphics.getHeight()/25f);
        this.setPosition(x, y);
        this.setSize(Gdx.graphics.getWidth()/25f,Gdx.graphics.getHeight()/25f);
        this.setOrigin(x, y);
        sprite.setOrigin(this.getOriginX(),this.getOriginY());
    }

    /**
     * 3rd constructor of the Player class
     * This constructor is used to retrieve all the player data from the game's database
     * @param name Player's name
     * @param piece Selected piece chosen
     * @param position Board position occupied
     * @param money Player's money
     * @param bankrupt true if the player is bankrupt, false if otherwise
     * @param jail true if the player is in jail, false if otherwise
     * @param cards The amount of 'Get out of jail free' cards the user has
     */
    public Player(String name, String piece, int position, int money, boolean bankrupt, boolean jail, int cards) {
        this.name = name;
        this.selectedPiece=piece;
        this.boardPosition = position;
        this.money = money;
        this.propertiesBought = new ArrayList<Property>();
        this.isBankrupt = bankrupt;
        this.isInJail = jail;
        this.nGetOutOfJailFreeCards = cards;
    }

    // Start of getters and setters of Player
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBoardPosition() {
        return boardPosition;
    }

    public void setBoardPosition(int boardPosition) {
        this.boardPosition = boardPosition;
    }

    public String getSelectedPiece() {
        return selectedPiece;
    }

    public void setSelectedPiece(String selectedPiece) {
        this.selectedPiece = selectedPiece;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public ArrayList<Property> getPropertiesBought() {
        return propertiesBought;
    }

    public void setPropertiesBought(ArrayList<Property> propertiesBought) {
        this.propertiesBought = propertiesBought;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void setBankrupt(boolean bankrupt) {
        isBankrupt = bankrupt;
    }

    public boolean isInJail() {
        return isInJail;
    }

    public void setInJail(boolean inJail) {
        isInJail = inJail;
    }

    public int getnGetOutOfJailFreeCards() {
        return nGetOutOfJailFreeCards;
    }

    public void setnGetOutOfJailFreeCards(int nGetOutOfJailFreeCards) {
        this.nGetOutOfJailFreeCards = nGetOutOfJailFreeCards;
    }
    // End of getters and setters of Player


    /**
     * Function drawing the sprite in the game
     * @param batch batch
     * @param parentAlpha parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.setPosition(getX(), getY());
        sprite.setScale(getScaleX(), getScaleY());
        sprite.draw(batch);
    }

    /**
     * Function where the player buys a property
     * @param s Board
     */
    public void buyProperty(Square[] s) {
        s[this.getBoardPosition()].getProperty().setOwner(this);
        this.getPropertiesBought().add(s[this.getBoardPosition()].getProperty());
        this.setMoney(this.getMoney()-s[this.getBoardPosition()].getProperty().getValue());
    }

    /**
     * Function where the player moves around the board
     * @param diceNumber Number of the dice thrown
     * @param duration Duration of the movement
     * @param reverseMovement False if the player moves around the board clockwise, true if it moves around anti-clockwise
     */
    public void playerMovement(int diceNumber, float duration, boolean reverseMovement){
        SequenceAction sa=new SequenceAction();
        if (!reverseMovement) {
            for (int i=0; i<diceNumber; i++) {
                if (this.getBoardPosition() >= 0 && this.getBoardPosition() <= 9) {
                    MoveByAction moveLeft = new MoveByAction();
                    if (this.getBoardPosition() == 0 || this.getBoardPosition() == 9) {
                        moveLeft.setAmountX(Gdx.graphics.getWidth()/14.91428f*-1);
                    } else {
                        moveLeft.setAmountX(Gdx.graphics.getWidth()/18.15652f*-1);
                    }
                    moveLeft.setDuration(duration);
                    sa.addAction(moveLeft);
                    this.addAction(sa);
                } else if (this.getBoardPosition() >= 10 && this.getBoardPosition() <= 19) {
                    MoveByAction moveUp = new MoveByAction();
                    if (this.getBoardPosition() == 10 || this.getBoardPosition() == 19) {
                        moveUp.setAmountY(Gdx.graphics.getHeight()/9.81818f);
                    } else {
                        moveUp.setAmountY(Gdx.graphics.getHeight()/14.02597f);
                    }
                    moveUp.setDuration(duration);
                    sa.addAction(moveUp);
                    this.addAction(sa);
                } else if (this.getBoardPosition() >= 20 && this.getBoardPosition() <= 29) {
                    MoveByAction moveRight = new MoveByAction();
                    if (this.getBoardPosition() == 20 || this.getBoardPosition() == 29) {
                        moveRight.setAmountX(Gdx.graphics.getWidth()/14.91428f);
                    } else {
                        moveRight.setAmountX(Gdx.graphics.getWidth()/18.15652f);
                    }
                    moveRight.setDuration(duration);
                    sa.addAction(moveRight);
                    this.addAction(sa);
                } else if (this.getBoardPosition() >= 30 && this.getBoardPosition() <= 39) {
                    MoveByAction moveDown = new MoveByAction();
                    if (this.getBoardPosition() == 30 || this.getBoardPosition() == 39) {
                        moveDown.setAmountY(Gdx.graphics.getHeight()/9.81818f*-1);
                    } else {
                        moveDown.setAmountY(Gdx.graphics.getHeight()/14.02597f*-1);
                    }
                    moveDown.setDuration(duration);
                    sa.addAction(moveDown);
                    this.addAction(sa);
                }
                if (this.getBoardPosition() == 39) {
                    this.setBoardPosition(0);
                    this.setMoney(this.getMoney()+200);
                    Gdx.app.log("Dimensiones 0: ", this.getX()+", "+this.getY());
                } else {
                    this.setBoardPosition(this.getBoardPosition() + 1);
                }
            }
        } else {
            for (int i=0; i<diceNumber; i++) {
                if (this.getBoardPosition() >= 1 && this.getBoardPosition() <= 10) {
                    MoveByAction moveRight = new MoveByAction();
                    if (this.getBoardPosition() == 1 || this.getBoardPosition() == 10) {
                        moveRight.setAmountX(Gdx.graphics.getWidth()/14.91428f);
                    } else {
                        moveRight.setAmountX(Gdx.graphics.getWidth()/18.15652f);
                    }
                    moveRight.setDuration(duration);
                    sa.addAction(moveRight);
                    this.addAction(sa);
                } else if (this.getBoardPosition() >= 11 && this.getBoardPosition() <= 20) {
                    MoveByAction moveDown = new MoveByAction();
                    if (this.getBoardPosition() == 11 || this.getBoardPosition() == 20) {
                        moveDown.setAmountY(Gdx.graphics.getHeight()/9.81818f*-1);
                    } else {
                        moveDown.setAmountY(Gdx.graphics.getHeight()/14.02597f*-1);
                    }
                    moveDown.setDuration(duration);
                    sa.addAction(moveDown);
                    this.addAction(sa);
                } else if (this.getBoardPosition() >= 21 && this.getBoardPosition() <= 30) {
                    MoveByAction moveLeft = new MoveByAction();
                    if (this.getBoardPosition() == 21 || this.getBoardPosition() == 30) {
                        moveLeft.setAmountX(Gdx.graphics.getWidth()/14.91428f*-1);
                    } else {
                        moveLeft.setAmountX(Gdx.graphics.getWidth()/18.15652f*-1);
                    }
                    moveLeft.setDuration(duration);
                    sa.addAction(moveLeft);
                    this.addAction(sa);
                } else if ((this.getBoardPosition() >= 31 && this.getBoardPosition() <= 39) || this.getBoardPosition() == 0) {
                    MoveByAction moveUp= new MoveByAction();
                    if (this.getBoardPosition() == 31 || this.getBoardPosition() == 0) {
                        moveUp.setAmountY(Gdx.graphics.getHeight()/9.81818f);
                    } else {
                        moveUp.setAmountY(Gdx.graphics.getHeight()/14.02597f);
                    }
                    moveUp.setDuration(duration);
                    sa.addAction(moveUp);
                    this.addAction(sa);
                }
                if (this.getBoardPosition()==0) {
                    this.setBoardPosition(39);
                    this.setMoney(this.getMoney()+200);
                } else {
                    this.setBoardPosition(this.getBoardPosition()-1);
                }
            }
        }
    }

    /**
     * Function where the community chest case will occur in case the player lands in one of the 'Community Chest' position
     * @param cc Array of String containing all the community chest cases
     * @param dialogs Dialogs where the result will be shown
     * @param p Player taking part
     */
    public void communityChestCase(String[] cc, GDXDialogs dialogs, final Player p) {
        Random r=new Random();
        final int randomNum=r.nextInt(cc.length);
        String textCommunityChest=cc[randomNum];

        GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
        bDialog.setTitle("Arca comunal");
        bDialog.setMessage(textCommunityChest);
        bDialog.addButton("OK");
        bDialog.build().show();
        bDialog.setClickListener(new ButtonClickListener() {
            @Override
            public void click(int button) {
                switch (randomNum) {
                    case 0:
                        p.setnGetOutOfJailFreeCards(getnGetOutOfJailFreeCards()+1);
                        break;
                    case 1:
                    case 4:
                        p.setMoney(getMoney()-50);
                        break;
                    case 2:
                    case 3:
                    case 9:
                        p.setMoney(getMoney()+100);
                        break;
                    case 5:
                        setMoney(getMoney()+20);
                        break;
                    case 6:
                        p.setMoney(getMoney()-100);
                        break;
                    case 7:
                        if (p.getBoardPosition()-10>=0) {
                            p.playerMovement(getBoardPosition()-10, 1f, true);
                        } else {
                            p.playerMovement(10-getBoardPosition(), 0.5f, false);
                        }
                        p.setInJail(true);
                        break;
                    case 8:
                        p.setMoney(getMoney()+25);
                        break;
                    case 10:
                        p.setMoney(getMoney()+50);
                        break;
                    case 11:
                        p.setMoney(getMoney()+10);
                        break;
                    case 12:
                        p.playerMovement(40-getBoardPosition(), 1f, false);
                        break;
                    case 13:
                        p.setMoney(getMoney()+200);
                        break;
                }
            }
        });
    }

    /**
     * Function where the chance case will happen when the player lands in one of the 'Chance' positions
     * @param player Player taking part
     * @param c Array of String containing all the chance cases
     * @param dialogs Dialogs where the result will be shown
     */
    public void chanceCase(final Player player, String[] c, GDXDialogs dialogs) {
        final Random r=new Random();
        final int randomNum=r.nextInt(c.length);
        String textChance=c[randomNum];

        GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
        bDialog.setTitle("Suerte");
        bDialog.setMessage(textChance);
        bDialog.addButton("OK");
        bDialog.build().show();
        bDialog.setClickListener(new ButtonClickListener() {
            @Override
            public void click(int button) {
                switch (randomNum) {
                    case 0:
                        playerMovement(39-player.getBoardPosition(), 1f, false);
                        BaseScreen.landingSituations(player);
                        break;
                    case 1:
                        player.playerMovement(40-player.getBoardPosition()+5, 1f, false);
                        player.setMoney(player.getMoney()+200);
                        BaseScreen.landingSituations(player);
                        break;
                    case 2:
                        player.setMoney(player.getMoney()+150);
                        break;
                    case 3:
                        if (13-player.getBoardPosition()<=0) {
                            player.playerMovement(40-player.getBoardPosition()+13, 1f, false);
                            player.setMoney(player.getMoney()+200);
                        } else {
                            player.playerMovement(13-player.getBoardPosition(), 0.5f, false);
                        }
                        BaseScreen.landingSituations(player);
                        break;
                    case 4:
                        if (10-player.getBoardPosition()<=0) {
                            player.playerMovement(player.getBoardPosition()-10, 1f, true);
                        } else {
                            player.playerMovement(10-player.getBoardPosition(), 0.5f, false);
                        }
                        player.setInJail(true);
                        break;
                    case 5:
                        player.playerMovement(40-player.getBoardPosition(), 1f, false);
                        break;
                    case 6:
                        player.setMoney(player.getMoney()+50);
                        break;
                    case 7:
                        player.setnGetOutOfJailFreeCards(player.getnGetOutOfJailFreeCards()+1);
                        break;
                    case 8:
                        if (24-player.getBoardPosition()<=0) {
                            player.playerMovement(40-player.getBoardPosition()+24, 1f, false);
                        } else {
                            player.playerMovement(24-player.getBoardPosition(), 0.5f, false);
                        }
                        BaseScreen.landingSituations(player);
                        break;
                    case 9:
                        player.playerMovement(3, 0.5f, true);
                        BaseScreen.landingSituations(player);
                        break;
                    case 10:
                        player.setMoney(player.getMoney()-15);
                        break;
                }
            }
        });
    }

    /**
     * Function where the player will pay the property's rent
     * @param dialogs Dialog showing information
     * @param prop Property where the player has landed
     * @param players ArrayList of players in the game
     * @param p Player who has to pay the rent
     */
    public void payRent(GDXDialogs dialogs, final Property prop, final ArrayList<Player> players, final Player p) {
        GDXButtonDialog bDialog = dialogs.newDialog(GDXButtonDialog.class);
        bDialog.setTitle("Pagar Renta");
        bDialog.setMessage(prop.getOwner().getName()+" es el dueño de "+prop.getName()+". " +
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

    /**
     * Function overriding the toString function
     * @return String with all the Player data
     */
    @Override
    public String toString() {
        return "Player{" +
                "sprite=" + sprite +
                ", name='" + name + '\'' +
                ", boardPosition=" + boardPosition +
                ", money=" + money +
                ", propertiesBought=" + propertiesBought +
                ", isBankrupt=" + isBankrupt +
                ", isInJail=" + isInJail +
                ", nGetOutOfJailFreeCards=" + nGetOutOfJailFreeCards +
                '}';
    }
}
