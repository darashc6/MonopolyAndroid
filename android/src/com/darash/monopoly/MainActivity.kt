package com.darash.monopoly

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity launcher of the application
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Function that starts a new game activity
     */
    fun startNewGameActivity(view: View) {
        val newGame=Intent(this, NewGameActivity::class.java)
        startActivity(newGame)
    }
}
