package database;

import java.util.ArrayList;

import classes.Player;
import classes.Square;

public interface Database {
    public void saveGame(ArrayList<Player> players, Square[] board);
    public ArrayList<Player> loadGame(Square[] board);
    public boolean checkSavedGame();
    public void deleteMatch();
}
