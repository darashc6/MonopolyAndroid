package database;

import java.util.ArrayList;

import classes.Player;
import classes.Square;

/**
 * Interface containing all the functions necessary for the database
 */
public interface Database {
    /**
     * Function used to save the progress of the game
     * @param players Players in the game
     * @param board Game's board
     * @param turn Turn of the game
     */
    void saveGame(ArrayList<Player> players, Square[] board, Byte turn);

    /**
     * Function used to load a game from the database
     * @param board Game's board
     * @return ArrayList of players containing all the data from the saved game
     */
    ArrayList<Player> loadGame(Square[] board);

    /**
     * Function used to check whether there is a game saved in the database
     * @return True if there is a game saved, false if otherwise
     */
    boolean checkSavedGame();

    /**
     * Function used to delete a match previously saved
     * It will be used if the user wants to start a game from scratch
     */
    void deleteMatch();

    /**
     * Function used to load the turn of the game
     * @return Integer indicating the turn
     */
    int loadTurn();
}
