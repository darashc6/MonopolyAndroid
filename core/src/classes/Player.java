package classes;

import java.util.ArrayList;

/**
 * Class that acts as a player of the Monopoly match
 * @author Darash
 */
public class Player {
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
     * @param name Player's name
     * @param sp Name of the selected piece by the player
     */
    public Player(String name, String sp) {
        this.name = name;
        this.boardPosition = 0;
        this.selectedPiece = sp;
        this.money = 2000;
        this.propertiesBought = new ArrayList<Property>();
        this.propertiesMortgaged = new ArrayList<Property>();
        this.propertiesRedeemable = new ArrayList<Property>();
        this.isBankrupt = false;
        this.isInJail = false;
        this.nGetOutOfJailFreeCards = 0;
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
}
