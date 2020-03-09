package database;

import java.util.ArrayList;

import classes.Player;
import classes.Square;

public interface Database {
    void saveGame(ArrayList<Player> players, Square[] board, Byte turn);
    ArrayList<Player> loadGame(Square[] board);
    boolean checkSavedGame();
    void deleteMatch();
    int loadTurn();
}
