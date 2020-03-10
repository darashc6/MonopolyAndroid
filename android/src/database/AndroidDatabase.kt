package database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import classes.Player
import classes.Square
import java.util.ArrayList

/**
 * Class used for storing game's data in the game's database
 * @param context Application's context
 * @author Darash
 */
class AndroidDatabase(var context: Context) : Database {
    var openHelper: DBOpenHelper=DBOpenHelper(context, 1) // Creates all the necessary tables for the game

    /**
     * Variables where the table's name are stored in a database
     */
    companion object {
        const val PLAYERS_TABLE_NAME = "playersMonopoly"
        const val PROPERTIES_TABLE_NAME = "propertiesMonopoly"
        const val SAVED_MATCH_TABLE_NAME = "matchSaved"
    }

    /**
     * Function used for loading a game from the previous state
     * @param board ArrayList of squares with all it's data
     * @return ArrayList of Players containing all the data from the previous state
     */
    override fun loadGame(board: Array<Square>): ArrayList<Player> {
        var players=ArrayList<Player>()
        val db=openHelper.writableDatabase
        val cursorPlayers: Cursor=db.query(PLAYERS_TABLE_NAME, null, null, null, null, null, null, null)
        val cursorProperties: Cursor=db.query(PROPERTIES_TABLE_NAME, null, null, null, null, null, null, null)

        // Loads player data from database
        while (cursorPlayers.moveToNext()) {
            var isInJail=false
            var isBankrupt=false
            if (cursorPlayers.getInt(cursorPlayers.getColumnIndex("isBankrupt"))==1) {
                isBankrupt=true
            }
            if (cursorPlayers.getInt(cursorPlayers.getColumnIndex("isInJail"))==1) {
                isInJail=true
            }
            players.add(Player(cursorPlayers.getString(cursorPlayers.getColumnIndex("name")),
                    cursorPlayers.getString(cursorPlayers.getColumnIndex("piece")),
                    cursorPlayers.getInt(cursorPlayers.getColumnIndex("position")),
                    cursorPlayers.getInt(cursorPlayers.getColumnIndex("money")),
                    isBankrupt,
                    isInJail,
                    cursorPlayers.getInt(cursorPlayers.getColumnIndex("cards"))))
        }
        cursorPlayers.close()

        // Loads property data for each Player from database
        for (player in players) {
            while (cursorProperties.moveToNext()) {
                val boardPosition=cursorProperties.getInt(cursorProperties.getColumnIndex("position"))
                val ownerName=cursorProperties.getString(cursorProperties.getColumnIndex("ownerName"))

                if (player.name==ownerName) {
                    player.propertiesBought.add(board[boardPosition].property)
                    board[boardPosition].property.owner=player
                }
            }
            cursorProperties.moveToPosition(-1)
        }

        cursorProperties.close()
        db.close()

        return players
    }

    /**
     * Function used to load the turn of the saved game
     */
    override fun loadTurn(): Int {
        var turn=0
        val db=openHelper.writableDatabase
        val cursorMatchSaved: Cursor=db.query(SAVED_MATCH_TABLE_NAME, null, null, null, null, null, null, null)

        if (cursorMatchSaved.moveToNext()) {
            turn=cursorMatchSaved.getInt(cursorMatchSaved.getColumnIndex("turn"))
        }
        cursorMatchSaved.close()

        return turn
    }

    /**
     * Checks whether there is a saved game in the database
     * Returns true if there is a previous game, false if otherwise
     */
    override fun checkSavedGame(): Boolean {
        var isGameSaved = false
        val db=openHelper.writableDatabase
        val cursorMatchSaved: Cursor=db.query(SAVED_MATCH_TABLE_NAME, null, null, null, null, null, null)

        if (cursorMatchSaved.moveToNext()) {
            if (cursorMatchSaved.getInt(cursorMatchSaved.getColumnIndex("saved"))==1) {
                isGameSaved=true
            }
        }
        cursorMatchSaved.close()

        return isGameSaved
    }

    /**
     * Erases all the data from a saved match from database
     */
    override fun deleteMatch() {
        val db=openHelper.writableDatabase
        db.delete(PLAYERS_TABLE_NAME, null, null)
        db.delete(PROPERTIES_TABLE_NAME, null, null)
        db.delete(SAVED_MATCH_TABLE_NAME, null, null)
    }

    /**
     * Function where it saves a game in it's current state
     */
    override fun saveGame(players: ArrayList<Player>, board: Array<Square>, turn: Byte) {
        val db=openHelper.writableDatabase
        db.delete(PLAYERS_TABLE_NAME, null, null)
        db.delete(PROPERTIES_TABLE_NAME, null, null)
        db.delete(SAVED_MATCH_TABLE_NAME, null, null)
        val cursorPlayers: Cursor=db.query(PLAYERS_TABLE_NAME, null, null, null, null, null, null, null)
        val cursorProperties: Cursor=db.query(PROPERTIES_TABLE_NAME, null, null, null, null, null, null, null)
        val cursorMatchSaved: Cursor=db.query(SAVED_MATCH_TABLE_NAME, null, null, null, null, null, null, null)
        val cv=ContentValues()

        // Stores the players data into the database
        for (p in players) {
            cv.put("name", p.name)
            cv.put("position", p.boardPosition)
            cv.put("piece", p.selectedPiece)
            cv.put("money", p.money)
            if (!p.isInJail) {
                cv.put("isInJail", 0)
            } else {
                cv.put("isInJail", 1)
            }
            if (!p.isBankrupt) {
                cv.put("isBankrupt", 0)
            } else {
                cv.put("isBankrupt", 1)
            }
            cv.put("cards", p.getnGetOutOfJailFreeCards())

            db.insert(PLAYERS_TABLE_NAME, null, cv)
            cv.clear()
        }
        cursorPlayers.close()

        // Stores the property data into the database
        for (sq in board) {
            if (sq.type == Square.SquareType.CITY || sq.type == Square.SquareType.STATION) {
                cv.put("position", sq.property.boardPosition)
                if (sq.property.owner==null) {
                    cv.put("ownerName", "null")
                } else {
                    cv.put("ownerName", sq.property.owner.name)
                }
                db.insert(PROPERTIES_TABLE_NAME, null, cv)
                cv.clear()
            }
        }
        cursorProperties.close()

        // Stored the boolean savedGame and the turn of the game
        cv.put("saved", 1)
        cv.put("turn", turn)
        db.insert(SAVED_MATCH_TABLE_NAME, null, cv)
        cursorMatchSaved.close()

        db.close()
    }
}