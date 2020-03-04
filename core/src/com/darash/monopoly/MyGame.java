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

public class MyGame extends Game {
	private BaseScreen currentScreen;
	private ArrayList<Player> arrayPlayers;
	private Database databaseMonopoly;

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

	private void setCurrentScreen(BaseScreen bs) {
		this.currentScreen=bs;
		this.setScreen(currentScreen);
	}
}
