package com.darash.monopoly;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

import java.util.ArrayList;

import constants.Functions;
import classes.Player;
import database.AndroidDatabase;

/**
 * Class that launches the game
 * @author Darash
 */
public class AndroidLauncher extends AndroidApplication {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        ArrayList<Player> arrayListPlayers=new ArrayList<>();
        boolean previousGame=getIntent().getBooleanExtra("previousGame", false);
        if (!previousGame) {
            arrayListPlayers=(ArrayList<Player>) getIntent().getSerializableExtra("arrayPlayers");
        }
        initialize(new MyGame(arrayListPlayers, new AndroidDatabase(this)), config);
    }

    /**
     * When the back button is pressed, it will show an AlertDialog during the game
     */
    @Override
    public void onBackPressed() {
        Functions.Companion.exitApplicaction(this);
    }
}
