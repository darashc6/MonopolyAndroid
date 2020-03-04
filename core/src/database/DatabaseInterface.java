package database;

import java.util.ArrayList;

import classes.Player;

public interface DatabaseInterface {
    public void saveGame();
    public ArrayList<Player> loadGame();
}
