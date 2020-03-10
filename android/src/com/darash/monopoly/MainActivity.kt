package com.darash.monopoly

import constants.Functions
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import database.AndroidDatabase
import services.MyService

/**
 * Activity launcher of the application
 */
class MainActivity : AppCompatActivity() {
    private val databaseGame by lazy { AndroidDatabase(this) }
    private var gameSaved: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startOwnService()
    }

    /**
     * Function that starts a new game activity
     */
    fun startNewGameActivity(view: View) {
        gameSaved=databaseGame.checkSavedGame()
        if (gameSaved) {
            // If game is saved in the database, an AlertDialog will be shown asking whether the user wants to overwrite the previous game
            val dialog = AlertDialog.Builder(this)
            dialog.setIcon(R.drawable.ic_warning_black_24dp)
            dialog.setTitle(getString(R.string.overwrite_game_title))
            dialog.setMessage(getString(R.string.overwrite_game_message))

            dialog.setNegativeButton(getString(R.string.overwrite_game_negative), object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    // Do nothing
                }
            })

            dialog.setPositiveButton(getString(R.string.overwrite_game_positive), object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    // When pressed, it will delete the previous game in the database and it will start a new one from scratch
                    Toast.makeText(applicationContext, applicationContext.getString(R.string.match_deleted_message), Toast.LENGTH_LONG).show()
                    databaseGame.deleteMatch()
                    val newGame=Intent(applicationContext, NewGameActivity::class.java)
                    startActivity(newGame)
                }
            })
            dialog.show()
        } else {
            val newGame=Intent(this, NewGameActivity::class.java)
            startActivity(newGame)
        }
    }

    /**
     * Function that starts the customized service
     */
    fun startOwnService() {
        val intentService=Intent(this, MyService::class.java)
        startService(intentService)
    }

    /**
     * When the back button is pressed, an AlertDialog will be shown asking whether the user wants to close the application
     */
    override fun onBackPressed() {
        Functions.exitApplicaction(this)
    }

    /**
     *
     */
    fun loadPreviousGame(view: View) {
        gameSaved=databaseGame.checkSavedGame()
        if (gameSaved) {
            val loadGame=Intent(this, AndroidLauncher::class.java)
            loadGame.putExtra("previousGame", true)
            startActivity(loadGame)
        } else {
            Toast.makeText(this, this.getString(R.string.no_saved_match_message), Toast.LENGTH_LONG).show()
        }
    }
}
