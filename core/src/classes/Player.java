package classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.ArrayList;

/**
 * Class that acts as a player of the Monopoly match
 * @author Darash
 */
public class Player extends Actor {
    private Sprite sprite;
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
     *
     */
    public Player(String texture, float x, float y) {
        this.name = "Player";
        this.boardPosition = 0;
        this.money = 2000;
        this.propertiesBought = new ArrayList<Property>();
        this.propertiesMortgaged = new ArrayList<Property>();
        this.propertiesRedeemable = new ArrayList<Property>();
        this.isBankrupt = false;
        this.isInJail = false;
        this.nGetOutOfJailFreeCards = 0;

        sprite=new Sprite(new Texture(texture));
        sprite.setBounds(x, y, Gdx.graphics.getWidth()/25, Gdx.graphics.getHeight()/25);
        this.setPosition(x, y);
        this.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.setOrigin(x,y);
        sprite.setOrigin(this.getOriginX(),this.getOriginY());
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


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        sprite.setPosition(getX(), getY());
        sprite.setScale(getScaleX(), getScaleY());
        sprite.setRotation(getRotation());
        sprite.draw(batch);
    }

    public void buyProperty(Square[] s) {
        s[this.getBoardPosition()].getProperty().setOwner(this);
        this.getPropertiesBought().add(s[this.getBoardPosition()].getProperty());
        this.setMoney(this.getMoney()-s[this.getBoardPosition()].getProperty().getValue());

    }

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
