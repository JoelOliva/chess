package com.chess;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import static com.chess.Chess.skin;

public class PauseMenu {
    private final Stage stage;
    public PauseMenu(Game game, Screen gameScreen) {
        stage = new Stage(new ScreenViewport());

        Pixmap pixmap = new Pixmap(1,1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fillRectangle(0, 0, 1, 1);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        Image transparentLayer = new Image(texture);
        transparentLayer.setSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        transparentLayer.getColor().a = 0.5f;

        TextButton surrender = new TextButton("Surrender", skin);
        surrender.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.dispose();
                game.setScreen(Chess.mainMenu);
            }
        });
        TextButton openHelp = new TextButton("Help", skin);
        openHelp.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Chess.helpWindow.setVisible(true);
            }
        });
        Table root = new Table(Chess.skin);
        root.setFillParent(true);
        Table menu = new Table();
        menu.add(surrender).size(300, surrender.getHeight()).padBottom(10);
        menu.row();
        menu.add(openHelp).size(300, surrender.getHeight());
        root.add(menu);

        stage.addActor(transparentLayer);
        stage.addActor(root);
        stage.addActor(Chess.helpWindow);
    }

    public Stage getStage() {
        return stage;
    }
}
