package com.darash.monopoly
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class NewGameActivity : AppCompatActivity() {
    private val spinnerNumeroJugadores by lazy { findViewById<Spinner>(R.id.spinnerNumeroJugadores) }
    private val layoutSeleccionFiguras by lazy { findViewById<GridView>(R.id.layout_seleccion_figuras) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_game)
        val adapterSpinner=ArrayAdapter.createFromResource(this, R.array.numero_jugadores, android.R.layout.simple_spinner_item)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerNumeroJugadores.adapter=adapterSpinner

        val adapterFiguras=CustomAdapter(this, Integer.parseInt(spinnerNumeroJugadores.selectedItem as String))
        layoutSeleccionFiguras.adapter=adapterFiguras

        spinnerNumeroJugadores.onItemSelectedListener=(object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                Toast.makeText(applicationContext, "SelectedValue: ${spinnerNumeroJugadores.selectedItem}", Toast.LENGTH_SHORT).show()
                adapterFiguras.nJugadores=Integer.parseInt(spinnerNumeroJugadores.selectedItem as String)
            }

        })
    }

    fun startGame(view: View) {
        val newGame=Intent(this, AndroidLauncher::class.java)
        startActivity(newGame)
    }
}
