package com.darash.monopoly;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

import classes.Player;
import database.Database;
import screens.BaseScreen;
import screens.Board;

/**
 * Class that show the game
 */
public class MyGame extends Game {
	private BaseScreen currentScreen; // Game's current screen
	private ArrayList<Player> arrayPlayers; // ArrayList of Players
	private Database databaseMonopoly; // Game's database

	/**
	 * Constructor of Game
	 * @param players ArrayList of players
	 * @param db Game's database
	 */
	public MyGame(ArrayList<Player> players, Database db) {
		this.arrayPlayers=players;
		this.databaseMonopoly=db;
	}

	@Override
	public void create () {
		this.setCurrentScreen(new Board(this, arrayPlayers, databaseMonopoly));
	}

	@Override
	public void render () {
		currentScreen.render(Gdx.graphics.getDeltaTime());
	}
	
	@Override
	public void dispose () {
		currentScreen.dispose();
	}

	/**
	 * Fucntion that sets the current screen of the game
	 * @param bs Screen to show
	 */
	private void setCurrentScreen(BaseScreen bs) {
		this.currentScreen=bs;
		this.setScreen(currentScreen);
	}
}
