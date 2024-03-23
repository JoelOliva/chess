package com.chess;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

import static com.badlogic.gdx.Input.Keys;

public class GameScreen implements Screen {
    private int state;
    private final InputAdapter gameInput;
    private final FitViewport scene;
    private final StatusPane topPane;
    private final StatusPane bottomPane;
    private final PauseMenu pauseMenu;

    public GameScreen(Game game) {
        state = State.running;
        gameInput = new InputAdapter();
        OrthographicCamera camera = new OrthographicCamera(640, 640);
        camera.setToOrtho(false);
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2 - 40, 0);
        scene = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getWidth(), camera);

        topPane = new StatusPane("Player 2", true);
        bottomPane = new StatusPane("Player 1", false);
        pauseMenu = new PauseMenu(game, this);
        Gdx.input.setInputProcessor(gameInput);
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

        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE))
            if (state == State.running) {
                Gdx.input.setInputProcessor(pauseMenu.getStage());
                state = State.paused;
            }
            else if (state == State.paused) {
                Gdx.input.setInputProcessor(gameInput);
                state = State.running;
            }

        switch (state) {
            case State.running:
                break;
            case State.paused:
                displayPauseMenu();
                break;

        }
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
        pauseMenu.getStage().dispose();
    }

    private void displayPauseMenu() {
        pauseMenu.getStage().getViewport().apply();
        pauseMenu.getStage().draw();
    }

}
