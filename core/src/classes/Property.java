package classes;

/**
 * Class that acts as a Property
 * @author Darash
 */
public class Property {
    private int boardPosition; // Position on the board
    private String name; // Property's name
    private int value; // Property's value
    private int rentPrice; // Property's rent value
    private int mortgagePrice; // Property's mortgage value
    private boolean isMortgaged; // Checks if the property is mortgaged
    private int redeemPrice; // Property's unmortgage value
    private Player owner; // Property's owner

    /**
     * Main constructor of the Property class
     * At the beginning of the game, each property doesn't have an owner, nor is it mortgaged
     * @param boardPosition Position on the board
     * @param name Property's name
     * @param value Property's rent value
     * @param rentPrice Property's mortgage value
     * @param mortgagePrice Checks if the property is mortgaged
     * @param redeemPrice Property's unmortgage value
     */
    public Property(int boardPosition, String name, int value, int rentPrice, int mortgagePrice, int redeemPrice) {
        this.boardPosition = boardPosition;
        this.name = name;
        this.value = value;
        this.rentPrice = rentPrice;
        this.mortgagePrice = mortgagePrice;
        this.isMortgaged = false;
        this.redeemPrice = redeemPrice;
        this.owner = null;
    }

    // Start of getters and setters of Property
    public int getBoardPosition() {
        return boardPosition;
    }

    public void setBoardPosition(int boardPosition) {
        this.boardPosition = boardPosition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(int rentPrice) {
        this.rentPrice = rentPrice;
    }

    public int getMortgagePrice() {
        return mortgagePrice;
    }

    public void setMortgagePrice(int mortgagePrice) {
        this.mortgagePrice = mortgagePrice;
    }

    public boolean isMortgaged() {
        return isMortgaged;
    }

    public void setMortgaged(boolean mortgaged) {
        isMortgaged = mortgaged;
    }

    public int getRedeemPrice() {
        return redeemPrice;
    }

    public void setRedeemPrice(int redeemPrice) {
        this.redeemPrice = redeemPrice;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
    }
    // End of Getters and setters of Property


    @Override
    public String toString() {
        return "Property{" +
                "boardPosition=" + boardPosition +
                ", name='" + name + '\'' +
                ", value=" + value +
                ", rentPrice=" + rentPrice +
                ", mortgagePrice=" + mortgagePrice +
                ", isMortgaged=" + isMortgaged +
                ", redeemPrice=" + redeemPrice +
                '}';
    }
}
