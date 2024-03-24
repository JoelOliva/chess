package com.chess;

import com.badlogic.gdx.Gdx;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.chess.Chess.skin;

public class StatusPane {
    private final Stage stage;
    public StatusPane(String user, boolean top) {
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Chess.TILESIZE / 2f));

        if (top) stage.getViewport().setScreenY(Gdx.graphics.getHeight() - Chess.TILESIZE / 2);
        else stage.getViewport().setScreenY(0);
        Label label = new Label(user, skin);
        label.setFontScaleX(0.5f);
        label.setFontScaleY(0.5f);

        Table pieces = new Table();
        Table root = new Table(skin);
        root.setFillParent(true);
        root.setBackground("window-round");
        root.add(label).padTop(10).padLeft(10);
        root.add(pieces).expandX();

        stage.addActor(root);
    }

    public void draw() {
        stage.getViewport().apply();
        stage.draw();
    }

    public void dispose() {
        stage.dispose();
    }
}
