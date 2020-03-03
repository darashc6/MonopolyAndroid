package com.darash.monopoly

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import classes.Player

/**
 * Class that creates a custom adapter for gridView
 * @param context Application Context
 * @param nPlayers Number of players choosen by the user via the spinner
 */
class CustomAdapter(context: Context, var arrayPlayers: ArrayList<Player>): ArrayAdapter<Player>(context, 0, arrayPlayers) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val arrayPosition=position
        val view=LayoutInflater.from(context).inflate(R.layout.layout_piece_selection, null)
        val spinnerPieces=view.findViewById<Spinner>(R.id.spinnerPieces)
        val imagePiece=view.findViewById<ImageView>(R.id.imageViewPiece)
        val adapterSpinner= ArrayAdapter.createFromResource(context, R.array.pieces, R.layout.custom_spinner_item)
        val playerName=view.findViewById<TextView>(R.id.playerName)
        playerName.text=arrayPlayers[position].name
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPieces.adapter=adapterSpinner
        // spinnerPieces.setSelection(adapterSpinner.getPosition(arrayPlayers[position].selectedPiece))
        spinnerPieces.onItemSelectedListener=(object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(spinnerPieces.selectedItem as String) {
                    "Barco" -> {
                        imagePiece.setImageResource(context.resources.getIdentifier("barco", "drawable", context.packageName))
                        arrayPlayers[arrayPosition].selectedPiece="barco"
                    }
                    "Caballo" -> {
                        imagePiece.setImageResource(context.resources.getIdentifier("caballo", "drawable", context.packageName))
                        arrayPlayers[arrayPosition].selectedPiece="caballo"
                    }
                    "Carretilla" -> {
                        imagePiece.setImageResource(context.resources.getIdentifier("carretilla", "drawable", context.packageName))
                        arrayPlayers[arrayPosition].selectedPiece="carretilla"
                    }
                    "Coche" -> {
                        imagePiece.setImageResource(context.resources.getIdentifier("coche", "drawable", context.packageName))
                        arrayPlayers[arrayPosition].selectedPiece="coche"
                    }
                    "Dedal" -> {
                        imagePiece.setImageResource(context.resources.getIdentifier("dedal", "drawable", context.packageName))
                        arrayPlayers[arrayPosition].selectedPiece="dedal"
                    }
                    "Gorra" -> {
                        imagePiece.setImageResource(context.resources.getIdentifier("gorra", "drawable", context.packageName))
                        arrayPlayers[arrayPosition].selectedPiece="gorra"
                    }
                    "Guerrero" -> {
                        imagePiece.setImageResource(context.resources.getIdentifier("guerrero", "drawable", context.packageName))
                        arrayPlayers[arrayPosition].selectedPiece="guerrero"
                    }
                    "Zapato" -> {
                        imagePiece.setImageResource(context.resources.getIdentifier("zapato", "drawable", context.packageName))
                        arrayPlayers[arrayPosition].selectedPiece="zapato"
                    }
                }
            }

        })

        return view
    }
}