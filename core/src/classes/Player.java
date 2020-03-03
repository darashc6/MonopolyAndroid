package classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import java.util.ArrayList;

/**
 * Class that acts as a player of the Monopoly match
 * @author Darash
 */
public class Player extends Actor {
    private Sprite sprite; // Sprite of the player
    private String name; // Player name
    private int boardPosition; // Position in the board
    private String selectedPiece; // Name of the selected piece by the player
    private int money; // Amount of money the player has
    private ArrayList<Property> propertiesBought; // Properties bought by the player
    private ArrayList<Property> propertiesMortgaged; // Properties mortgaged by the player
    private ArrayList<Property> propertiesRedeemable; // Properties that can be paid off
    private boolean isBankrupt; // Checks if the player is bankrupt
    private boolean isInJail; // Checks if the player is in jail
    private int nGetOutOfJailFreeCards; // Number of 'Get out of jail free' Cards the player has

    /**
     * Initial constructor of the Player class
     * At the beginning of the match, the player nor is bankrupt, nor is he in jail, nor does he have 'Get out of jail free' Cards, and nor does he have any properties
     * @param name Player name
     * @param piece Selected piece
     */
    public Player(String name, String piece) {
        this.name = name;
        this.boardPosition = 0;
        this.money = 2000;
        this.propertiesBought = new ArrayList<Property>();
        this.propertiesMortgaged = new ArrayList<Property>();
        this.propertiesRedeemable = new ArrayList<Property>();
        this.isBankrupt = false;
        this.isInJail = false;
        this.nGetOutOfJailFreeCards = 0;

        /*sprite=new Sprite(new Texture("pieces/"+piece+".png"));
        sprite.setBounds(Gdx.graphics.getWidth()/1.6704f, Gdx.graphics.getHeight()/6.75f, Gdx.graphics.getWidth()/25f, Gdx.graphics.getHeight()/25f);
        this.setPosition(Gdx.graphics.getWidth()/1.6704f, Gdx.graphics.getHeight()/6.75f);
        this.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.setOrigin(Gdx.graphics.getWidth()/1.6704f, Gdx.graphics.getHeight()/6.75f);
        sprite.setOrigin(this.getOriginX(),this.getOriginY());*/
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

    public ArrayList<Property> getPropertiesMortgaged() {
        return propertiesMortgaged;
    }

    public void setPropertiesMortgaged(ArrayList<Property> propertiesMortgaged) {
        this.propertiesMortgaged = propertiesMortgaged;
    }

    public ArrayList<Property> getPropertiesRedeemable() {
        return propertiesRedeemable;
    }

    public void setPropertiesRedeemable(ArrayList<Property> propertiesRedeemable) {
        this.propertiesRedeemable = propertiesRedeemable;
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
                } else {
                    this.setBoardPosition(this.getBoardPosition()-1);
                }
            }
        }
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
                ", selectedPiece='" + selectedPiece + '\'' +
                ", money=" + money +
                ", propertiesBought=" + propertiesBought +
                ", propertiesMortgaged=" + propertiesMortgaged +
                ", propertiesRedeemable=" + propertiesRedeemable +
                ", isBankrupt=" + isBankrupt +
                ", isInJail=" + isInJail +
                ", nGetOutOfJailFreeCards=" + nGetOutOfJailFreeCards +
                '}';
    }
}
