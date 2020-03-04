package database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import classes.Player
import classes.Square
import java.util.ArrayList

class AndroidDatabase(var context: Context) : Database {
    var openHelper: DBOpenHelper=DBOpenHelper(context, 1)
    companion object {
        const val PLAYERS_TABLE_NAME = "playersMonopoly"
        const val PROPERTIES_TABLE_NAME = "propertiesMonopoly"
    }

    override fun loadGame(board: Array<Square>): ArrayList<Player> {
        var players=ArrayList<Player>()
        val db=openHelper.writableDatabase
        val cursorPlayers: Cursor=db.query(PLAYERS_TABLE_NAME, null, null, null, null, null, null, null)
        val cursorProperties: Cursor=db.query(PROPERTIES_TABLE_NAME, null, null, null, null, null, null, null)

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

        for (player in players) {
            while (cursorProperties.moveToNext()) {
                val boardPosition=cursorProperties.getInt(cursorProperties.getColumnIndex("position"))
                val ownerName=cursorProperties.getString(cursorProperties.getColumnIndex("ownerName"))

                if (player.name==ownerName) {
                    player.propertiesBought.add(board[boardPosition].property)
                }
            }
            cursorProperties.moveToPosition(-1)
        }

        cursorProperties.close()
        db.close()

        return players
    }

    override fun saveGame(players: ArrayList<Player>, board: Array<Square>) {
        val db=openHelper.writableDatabase
        db.delete(PLAYERS_TABLE_NAME, null, null)
        db.delete(PROPERTIES_TABLE_NAME, null, null)
        val cursorPlayers: Cursor=db.query(PLAYERS_TABLE_NAME, null, null, null, null, null, null, null)
        val cursorProperties: Cursor=db.query(PROPERTIES_TABLE_NAME, null, null, null, null, null, null, null)
        val cv=ContentValues()

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

        for (sq in board) {
            if (sq.type == Square.SquareType.CITY || sq.type == Square.SquareType.STATION) {
                cv.put("position", sq.property.boardPosition)
                cv.put("ownerName", sq.property.owner.name)
                db.insert(PROPERTIES_TABLE_NAME, null, cv)
                cv.clear()
            }
        }
        cursorProperties.close()
        db.close()
    }
}