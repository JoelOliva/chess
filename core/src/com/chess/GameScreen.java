package com.chess;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {
    Game game;
    private final FitViewport scene;
    private final StatusPane topPane;
    private final StatusPane bottomPane;


    public GameScreen(Game game) {
        this.game = game;
        OrthographicCamera camera = new OrthographicCamera(640, 640);
        camera.setToOrtho(false);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2 - 40, 0);
        scene = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getWidth(), camera);

        topPane = new StatusPane("Player 2", true);
        bottomPane = new StatusPane("Player 1", false);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {
        ScreenUtils.clear(0, 0, 0, 1);
        scene.apply();

        topPane.draw();
        bottomPane.draw();
    }

    @Override
    public void resize(int i, int i1) {
        scene.update(i, i1);
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
        topPane.dispose();
        bottomPane.dispose();
    }
}
