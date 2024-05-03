package com.chess;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * Represents the game screen for chess. 
 * Handles game state, rendering, and user interaction.
 * 
 * Original code was written by Joel Oliva, modifications have been made by
 * Noah Ewell to gain functionality with moving pieces.
 * 
 * @author Noah Ewell, Joel Oliva
 */
public class GameScreen implements Screen {
    private int state;
    private final GameInputProcessor gameInput;
    private final FitViewport scene;
    private final StatusPane topPane;
    private final StatusPane bottomPane;
    private final PauseMenu pauseMenu;
    final Board board;

    private float blinkTimer = 0;
    boolean isXVisible = true;
    private final static float BLINK_INTERVAL = 0.20f;
    int cursorX = 3, cursorY = 3;
    boolean isMoving = false;
    Piece currentPiece = null;
    int oldX, oldY;

    /**
     * Constructs a new game screen setting up the UI components and environment.
     * 
     * @param game 	The main game object which manages the screens.
     */
    public GameScreen(Game game) {
        state = State.running;
        gameInput = new GameInputProcessor(this);
        Gdx.input.setInputProcessor(gameInput);
        OrthographicCamera camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getWidth());
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        scene = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getWidth(), camera);
        topPane = new StatusPane("Player 2", true);
        bottomPane = new StatusPane("Player 1", false);
        pauseMenu = new PauseMenu(game, this);
        board = new Board(this);
        Gdx.input.setInputProcessor(gameInput);
    }

    @Override
    public void show() { 
    }

    /**
     * Updates the screen frame by frame.
     * 
     * @param delta 	Time in seconds since the last frame.
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0, 1);
        scene.apply();
        board.draw(scene.getCamera(), cursorX, cursorY, isMoving);

        updateCursor(delta);

        if (isXVisible) {
            board.drawCursor(scene.getCamera(), cursorX, cursorY, true);
        }

        topPane.draw();
        bottomPane.draw();

        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
            if (state == State.running) {
                Gdx.input.setInputProcessor(pauseMenu.getStage());
                state = State.paused;
            } else if (state == State.paused) {
                Gdx.input.setInputProcessor(gameInput);
                state = State.running;
            }
        }

        if (state == State.paused) {
            displayPauseMenu();
        }
    }

    /**
     * Updates cursor's visibility according to BLINK_INTERVAL.
     * 
     * @param delta		time in seconds since last frame
     */
    private void updateCursor(float delta) {
        blinkTimer += delta;
        if (blinkTimer >= BLINK_INTERVAL) {
            isXVisible = !isXVisible;
            blinkTimer = 0;
        }
    }

    /**
     * Finalizes piece movement.
     */
    public void confirmMovement() {
        if (currentPiece != null) {
            board.setPiece(oldX, oldY, null);
            board.setPiece(cursorX, cursorY, currentPiece);
            currentPiece = null;
            isMoving = false;
        }
    }

    /**
     * Resizes game screen.
     */
    @Override
    public void resize(int width, int height) {
        scene.update(width, height);
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

    /**
     * Disposes resources.
     */
    @Override
    public void dispose() {
        topPane.dispose();
        bottomPane.dispose();
        pauseMenu.getStage().dispose();
        board.dispose();
    }

    /**
     * Displays pause menu.
     */
    private void displayPauseMenu() {
        pauseMenu.getStage().getViewport().apply();
        pauseMenu.getStage().draw();
    }
}
