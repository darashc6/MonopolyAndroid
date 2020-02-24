package classes;

/**
 * Class acting as a square in the board
 */
public class Square {
    private SquareType type; // Type of Square
    private Property property; // Property of the square

    /**
     * Enum indicating the types of the square
     */
    public enum SquareType {
        CITY,
        STATION,
        CHANCE,
        COMMUNITY_CHEST,
        TAXES,
        NO_PARKING,
        JAIL_VISIT,
        SPA,
        GO
    }

    /**
     * Main constructor of the Square class
     * @param type // Type of Square
     * @param property // Property of the square
     */
    public Square(SquareType type, Property property) {
        this.type = type;
        this.property = property;
    }

    /**
     * Secondary constructor of the Square class
     * This constructor is used in case the square doesn't have a property
     * @param type // Type of Square
     */
    public Square(SquareType type) {
        this.type = type;
        this.property = null;
    }

    /**
     * Function initializing all the squares on the board
     * @return Array of the squares with all its data
     */
    public static Square[] initSquares() {
        Square[] sq=new Square[40];

        sq[0]=new Square(SquareType.GO);
        sq[1]=new Square(SquareType.CITY, new Property(1, "Cáceres", 100, 6, 50, 110, null));
        sq[2]=new Square(SquareType.COMMUNITY_CHEST);
        sq[3]=new Square(SquareType.CITY, new Property(3, "Badajoz", 120, 8, 60, 125, null));
        sq[4]=new Square(SquareType.TAXES);
        sq[5]=new Square(SquareType.STATION, new Property(5, "Málaga María Zambrano", 200, 25, 100, 220, null));
        sq[6]=new Square(SquareType.CITY, new Property(6, "Granada", 300, 26, 150, 330, null));
        sq[7]=new Square(SquareType.CITY, new Property(7, "Córdoba", 300, 26, 150, 330, null));
        sq[8]=new Square(SquareType.CHANCE);
        sq[9]=new Square(SquareType.CITY, new Property(9, "Sevilla", 320, 28, 160, 350, null));
        sq[10]=new Square(SquareType.JAIL_VISIT);
        sq[11]=new Square(SquareType.CITY, new Property(11, "Alicante", 180, 14, 90, 200, null));
        sq[12]=new Square(SquareType.CITY, new Property(12, "Castellón", 180, 14, 90, 200, null));
        sq[13]=new Square(SquareType.CITY, new Property(13, "Valencia", 200, 16, 100, 220, null));
        sq[14]=new Square(SquareType.CITY, new Property(14, "Universidad", 180, 25, 90, 200, null));
        sq[15]=new Square(SquareType.STATION, new Property(15, "Barcelona Sants", 200, 25, 100, 220, null));
        sq[16]=new Square(SquareType.COMMUNITY_CHEST);
        sq[17]=new Square(SquareType.CITY, new Property(17, "Lleida", 260, 22, 130, 285, null));
        sq[18]=new Square(SquareType.CITY, new Property(18, "Tarragona", 260, 22, 130, 285, null));
        sq[19]=new Square(SquareType.CITY, new Property(19, "Girona", 280, 24, 140, 310, null));
        sq[20]=new Square(SquareType.SPA);
        sq[21]=new Square(SquareType.CITY, new Property(21, "Huesca", 140, 10, 70, 155, null));
        sq[22]=new Square(SquareType.CITY, new Property(22, "Teruel", 140, 10, 70, 155, null));
        sq[23]=new Square(SquareType.CHANCE);
        sq[24]=new Square(SquareType.CITY, new Property(24, "Zaragoza", 160, 12, 80, 175, null));
        sq[25]=new Square(SquareType.STATION, new Property(25, "Santiago de Compostela", 200, 25, 100, 220, null));
        sq[26]=new Square(SquareType.CITY, new Property(26, "Compañia Solar", 180, 25, 90, 200, null));
        sq[27]=new Square(SquareType.CITY, new Property(27, "Lugo", 160, 12, 80, 175, null));
        sq[28]=new Square(SquareType.CITY, new Property(28, "Vigo", 160, 12, 80, 175, null));
        sq[29]=new Square(SquareType.CITY, new Property(29, "Orense", 180, 14, 90, 200, null));
        sq[30]=new Square(SquareType.NO_PARKING);
        sq[31]=new Square(SquareType.CITY, new Property(31, "Guadalajara", 240, 20, 120, 265, null));
        sq[32]=new Square(SquareType.CITY, new Property(32, "Cuenca", 240, 20, 120, 265, null));
        sq[33]=new Square(SquareType.CITY, new Property(33, "Toledo", 260, 22, 130, 280, null));
        sq[34]=new Square(SquareType.STATION, new Property(34, "Madrid Atocha", 200, 25, 100, 220, null));
        sq[35]=new Square(SquareType.COMMUNITY_CHEST);
        sq[36]=new Square(SquareType.TAXES);
        sq[37]=new Square(SquareType.CITY, new Property(37, "Islas Baleares", 350, 35, 175, 385, null));
        sq[38]=new Square(SquareType.CHANCE);
        sq[39]=new Square(SquareType.CITY, new Property(39, "Islas Canarias", 400, 50, 200, 440, null));

        return sq;
    }

    /**
     * Getter that returns the type of square
     * @return Type of the square
     */
    public SquareType getType() {
        return type;
    }

    /**
     * Setter that modifies the type of square
     * @param type The new type of square
     */
    public void setType(SquareType type) {
        this.type = type;
    }

    /**
     * Getter returning the all the property data
     * @return Property data
     */
    public Property getProperty() {
        return property;
    }

    /**
     * Setter modifying the property data
     * @param property Modified property
     */
    public void setProperty(Property property) {
        this.property = property;
    }
}
