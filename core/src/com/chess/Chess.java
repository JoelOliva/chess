package com.chess;

import com.badlogic.gdx.Game;

public class Chess extends Game {
	
	@Override
	public void create () {
		setScreen(new MainMenuScreen(this));
	}
}
