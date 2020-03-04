package com.darash.monopoly;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.darash.monopoly.MyGame;

import java.util.ArrayList;

import Constants.Functions;
import classes.Player;
import database.AndroidDatabase;

/**
 * Class that launches the game
 */
public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        ArrayList<Player> arrayListPlayers=(ArrayList<Player>) getIntent().getSerializableExtra("arrayPlayers");
        initialize(new MyGame(arrayListPlayers, new AndroidDatabase(this)), config);
    }

    @Override
    public void onBackPressed() {
        Functions.Companion.exitApplicaction(this);
    }
}
