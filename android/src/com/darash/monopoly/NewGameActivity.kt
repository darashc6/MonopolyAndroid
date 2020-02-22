package com.darash.monopoly
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity containing the setup for a new game
 */
class NewGameActivity : AppCompatActivity() {
    private val spinnerNumberPlayers by lazy { findViewById<Spinner>(R.id.spinnerNumberPlayers) } // Spinner containing the number of players
    private val layoutPieceSelection by lazy { findViewById<GridView>(R.id.layout_piece_selection) } // Layout where the user can choose the pieces

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)
        val adapterSpinner=ArrayAdapter.createFromResource(this, R.array.number_players, android.R.layout.simple_spinner_item)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNumberPlayers.adapter=adapterSpinner

        val adapterFiguras=CustomAdapter(this, Integer.parseInt(spinnerNumberPlayers.selectedItem as String))
        layoutPieceSelection.adapter=adapterFiguras

        spinnerNumberPlayers.onItemSelectedListener=(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                adapterFiguras.nPlayers=Integer.parseInt(spinnerNumberPlayers.selectedItem as String)
            }

        })
    }

    /**
     * Function where the new game starts
     */
    fun startGame(view: View) {
        val newGame=Intent(this, AndroidLauncher::class.java)
        startActivity(newGame)
    }
}
