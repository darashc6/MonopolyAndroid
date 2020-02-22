package com.darash.monopoly

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startNewGameActivity(view: View) {
        val newGame=Intent(this, NewGameActivity::class.java)
        startActivity(newGame)
    }
}
