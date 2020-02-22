package com.darash.monopoly

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.Spinner

class CustomAdapter(private val context: Context, var nJugadores: Int): BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view=LayoutInflater.from(context).inflate(R.layout.layout_character_selection, null)
        val spinnerFiguras=view.findViewById<Spinner>(R.id.spinnerFiguras)
        val adapterSpinner= ArrayAdapter.createFromResource(context, R.array.numero_jugadores, android.R.layout.simple_spinner_item)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerFiguras.adapter=adapterSpinner

        return view
    }

    override fun getItem(position: Int): Any {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return nJugadores
    }
}