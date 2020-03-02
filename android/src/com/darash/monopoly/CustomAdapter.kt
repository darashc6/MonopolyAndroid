package com.darash.monopoly

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*

/**
 * Class that creates a custom adapter for gridView
 * @param context Application Context
 * @param nPlayers Number of players choosen by the user via the spinner
 */
class CustomAdapter(private val context: Context, var nPlayers: Int): BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view=LayoutInflater.from(context).inflate(R.layout.layout_piece_selection, null)
        val spinnerPieces=view.findViewById<Spinner>(R.id.spinnerPieces)
        val imagePiece=view.findViewById<ImageView>(R.id.imageViewPiece)
        val adapterSpinner= ArrayAdapter.createFromResource(context, R.array.pieces, R.layout.custom_spinner_item)
        val playerNameText=view.findViewById<EditText>(R.id.playerTextName)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPieces.adapter=adapterSpinner
        spinnerPieces.onItemSelectedListener=(object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when(spinnerPieces.selectedItem as String) {
                    "Barco" -> {
                        imagePiece.setImageResource(context.resources.getIdentifier("barco", "drawable", context.packageName))
                    }
                    "Caballo" -> {
                        imagePiece.setImageResource(context.resources.getIdentifier("caballo", "drawable", context.packageName))
                    }
                    "Carretilla" -> {
                        imagePiece.setImageResource(context.resources.getIdentifier("carretilla", "drawable", context.packageName))
                    }
                    "Coche" -> {
                        imagePiece.setImageResource(context.resources.getIdentifier("coche", "drawable", context.packageName))
                    }
                    "Dedal" -> {
                        imagePiece.setImageResource(context.resources.getIdentifier("dedal", "drawable", context.packageName))
                    }
                    "Gorra" -> {
                        imagePiece.setImageResource(context.resources.getIdentifier("gorra", "drawable", context.packageName))
                    }
                    "Guerrero" -> {
                        imagePiece.setImageResource(context.resources.getIdentifier("guerrero", "drawable", context.packageName))
                    }
                    "Zapato" -> {
                        imagePiece.setImageResource(context.resources.getIdentifier("zapato", "drawable", context.packageName))
                    }
                }
            }

        })

        playerNameText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                playerNameText.text=playerNameText.text
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

        })

        return view
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return nPlayers
    }
}