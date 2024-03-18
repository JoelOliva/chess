package com.chess;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.chess.Chess.skin;

public class MainMenuScreen implements Screen {
    Game game;
    private final Stage stage;
    public MainMenuScreen(Game game) {
        this.game = game;

        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        TextButton singlePlayer = new TextButton("Single Player", skin);
        singlePlayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        TextButton twoPlayer = new TextButton("2 Player", skin);
        twoPlayer.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new GameScreen(game));
            }
        });
        TextButton online = new TextButton("Online", skin);
        TextButton help = new TextButton("Help", skin);
        help.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Chess.helpWindow.setVisible(true);
            }
        });

        Table root = new Table(skin);
        root.setFillParent(true);
        root.setBackground("window-round");
        Image logo = new Image(new Texture(Gdx.files.internal("images/logo.png")));
        root.add(logo);
        root.row();
        Table buttons = new Table();
        buttons.add(singlePlayer).size(350, singlePlayer.getHeight() * 0.85f).padBottom(10);
        buttons.row();
        buttons.add(twoPlayer).size(350, singlePlayer.getHeight() * 0.85f).padBottom(10);
        buttons.row();
        buttons.add(online).size(350, singlePlayer.getHeight() * 0.85f).padBottom(10);
        buttons.row();
        buttons.add(help).size(350, singlePlayer.getHeight() * 0.85f);
        buttons.setX(stage.getWidth() / 2);
        buttons.setY(stage.getHeight() / 2);
        root.add(buttons);

        stage.addActor(root);
        stage.addActor(Chess.helpWindow);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        stage.draw();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
