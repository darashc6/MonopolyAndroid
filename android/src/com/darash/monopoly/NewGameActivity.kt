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
        arrayPlayers= ArrayList(2)
        val adapterSpinner=ArrayAdapter.createFromResource(this, R.array.number_players, android.R.layout.simple_spinner_item)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNumberPlayers.adapter=adapterSpinner

        var adapterFiguras=CustomAdapter(this, arrayPlayers)
        layoutPieceSelection.adapter=adapterFiguras

        spinnerNumberPlayers.onItemSelectedListener=(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (spinnerNumberPlayers.selectedItem as String) {
                    "2" -> {
                        arrayPlayers.clear()
                        arrayPlayers.add(Player("Jugador", "Barco"))
                        arrayPlayers.add(Player("IA 1", "Barco"))
                    }
                    "3" -> {
                        arrayPlayers.clear()
                        arrayPlayers.add(Player("Jugador", "Barco"))
                        arrayPlayers.add(Player("IA 1", "Barco"))
                        arrayPlayers.add(Player("IA 2", "Barco"))
                    }
                    "4" -> {
                        arrayPlayers.clear()
                        arrayPlayers.add(Player("Jugador", "Barco"))
                        arrayPlayers.add(Player("IA 1", "Barco"))
                        arrayPlayers.add(Player("IA 2", "Barco"))
                        arrayPlayers.add(Player("IA 3", "Barco"))
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
        var bundle=Bundle()
        newGame.putExtra("arrayPlayers", arrayPlayers)
        startActivity(newGame)
    }
}
