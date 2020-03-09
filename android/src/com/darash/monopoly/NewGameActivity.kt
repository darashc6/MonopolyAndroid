package com.darash.monopoly
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import classes.Player

/**
 * Activity containing the setup for a new game
 */
class NewGameActivity : AppCompatActivity() {
    private val spinnerNumberPlayers by lazy { findViewById<Spinner>(R.id.spinnerNumberPlayers) } // Spinner containing the number of players
    private val layoutPieceSelection by lazy { findViewById<GridView>(R.id.layout_piece_selection) } // Layout where the user can choose the pieces
    private lateinit var arrayPlayers: ArrayList<Player>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)
        arrayPlayers=ArrayList()
        arrayPlayers.add(Player("Jugador", "Barco"))
        arrayPlayers.add(Player("IA 1", "Barco"))
        val adapterSpinner=ArrayAdapter.createFromResource(this, R.array.number_players, android.R.layout.simple_spinner_item)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNumberPlayers.adapter=adapterSpinner

        val adapterFiguras=CustomAdapter(this, arrayPlayers)
        layoutPieceSelection.adapter=adapterFiguras

        spinnerNumberPlayers.onItemSelectedListener=(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val nPlayers = Integer.parseInt(spinnerNumberPlayers.selectedItem as String)
                if (arrayPlayers.size < nPlayers) {
                    while (arrayPlayers.size < nPlayers) {
                        arrayPlayers.add(Player("IA "+arrayPlayers.size, "Barco"))
                    }
                } else if (arrayPlayers.size > nPlayers) {
                    while (arrayPlayers.size > nPlayers) {
                        arrayPlayers.removeAt(arrayPlayers.size-1)
                    }
                }
            }

        })
    }

    /**
     * Function where the new game starts
     */
    fun startGame(view: View) {
        val newGame=Intent(this, AndroidLauncher::class.java)
        newGame.putExtra("arrayPlayers", arrayPlayers)
        newGame.putExtra("previousGame", false)
        startActivity(newGame)
    }
}
