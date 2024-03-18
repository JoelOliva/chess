package com.chess;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Chess extends Game {
	static Skin skin;
	static Window helpWindow;
	static MainMenuScreen mainMenu;

	@Override
	public void create () {
		TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("UI/pixthulhu-ui.atlas"));
		skin = new Skin(Gdx.files.internal("UI/pixthulhu-ui.json"), atlas);
		helpWindow = new Window("", skin);
		helpWindow.setHeight(Gdx.graphics.getHeight());
		helpWindow.setWidth(450);
		helpWindow.setX(Gdx.graphics.getWidth() / 2f - helpWindow.getWidth() / 2);
		helpWindow.setMovable(false);
		Button closeButton = new Button(skin);
		closeButton.setHeight(50);
		closeButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				helpWindow.setVisible(false);
			}
		});
		Table table = new Table();
		Label h1 = new Label("Rules", skin);
		h1.setFontScale(1.5f);
		Label h2 = new Label("Move Types", skin);
		h2.setFontScale(1.5f);
		table.add(closeButton).padRight(350).size(50, 50);
		table.row();
		table.add(h1);
		table.row().height(550);
		table.add(h2);
		helpWindow.add(table);
		helpWindow.setVisible(false);

		mainMenu = new MainMenuScreen(this);
		setScreen(mainMenu);
	}
}
