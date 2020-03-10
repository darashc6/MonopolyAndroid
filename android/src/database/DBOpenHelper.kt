package database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Class used to create all the necessary tables for the game's database
 * @param context Application's context
 * @param version Databse version
 * @author Darash
 */
class DBOpenHelper(var context: Context, var version: Int): SQLiteOpenHelper(context, "bdMonopoly", null, version) {

    /**
     * Creates all the necessary tables for the game
     */
    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("CREATE TABLE playersMonopoly(name TEXT PRIMARY KEY, " +
                "position INT(2), " +
                "piece TEXT, " +
                "money INT(4), " +
                "isInJail INT(1) DEFAULT 0," +
                "isBankrupt INT(1) DEFAULT 0," +
                "cards INT(2))")
        db.execSQL("CREATE TABLE propertiesMonopoly(position INT(2) PRIMARY KEY," +
                "ownerName TEXT)")
        db.execSQL("CREATE TABLE matchSaved(saved INT(1) PRIMARY KEY DEFAULT 0, " +
                "turn INT(1))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}