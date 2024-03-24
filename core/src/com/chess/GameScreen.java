package com.chess;

import com.badlogic.gdx.*;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.badlogic.gdx.Input.Keys;

public class GameScreen implements Screen {
    private int state;
    private final InputAdapter gameInput;
    private final FitViewport scene;
    private final StatusPane topPane;
    private final StatusPane bottomPane;
    private final PauseMenu pauseMenu;
    private final ShapeRenderer shapeRenderer;
    private Texture[][] pieces; // Array to hold textures for each game piece
    private final SpriteBatch batch = new SpriteBatch();

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
        shapeRenderer = new ShapeRenderer();
        Gdx.input.setInputProcessor(gameInput);
    }

    @Override
    public void show() {
      
    }

    
    @Override
    public void render(float delta) {
        //renders the black background
        ScreenUtils.clear(0, 0, 0, 1);
        scene.apply();

        //renders one set of white tiles
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        float yAxis = 80;
        float xAxis;

        for(int i = 0; i < 4; i++) {
            xAxis = 0;
            for(int j = 0; j < 4; j++) {
                shapeRenderer.rect(xAxis, yAxis, 80, 80);
                xAxis = 160 + xAxis;
            }
            yAxis = yAxis + 160;
        }

        //renders the other set of white tiles
        yAxis = 0;
        for(int i = 0; i < 4; i++) {
            xAxis = 80;
            
            for(int j = 0; j < 4; j++) {
                shapeRenderer.rect(xAxis, yAxis, 80, 80);
                xAxis = 160 + xAxis;
            }
            yAxis = yAxis + 160;
        }
        
        shapeRenderer.end();
   
        // I commented out the below two lines because they were messing with the display of game pieces. But that means that the top and bottom panes aren't visible aynmore. Wasn't sure how to fix -Bryson
        // topPane.draw();
        // bottomPane.draw();

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
                    // Draw game pieces when the game is running
                    drawGamePieces();
                    break;
                case State.paused:
                    // Display pause menu when the game is paused
                    displayPauseMenu();
                    break;
        }
    }
            
    /**
     * Makes the game pieces visible
     * @Bryson
     *  */       
    private void drawGamePieces() {
            // Draw game pieces
            float pieceWidth = 59;
            float pieceHeight = 74;
            float xAxis = 14;
            batch.begin();

            // Initialize textures of pieces
            Texture whitePawn = new Texture("sprites/wp.png");
            Texture whiteBishop = new Texture("sprites/wb.png");
            Texture whiteKnight = new Texture("sprites/wkn.png");
            Texture whiteRook = new Texture("sprites/wr.png");
            Texture whiteQueen = new Texture("sprites/wq.png");
            Texture whiteKing = new Texture("sprites/wk.png");
            Texture blackPawn = new Texture("sprites/bp.png");
            Texture blackBishop = new Texture("sprites/bb.png");
            Texture blackKnight = new Texture("sprites/bkn.png");
            Texture blackRook = new Texture("sprites/br.png");
            Texture blackQueen = new Texture("sprites/bq.png");
            Texture blackKing = new Texture("sprites/bk.png");

            // Draw  white pawns
            for (int i = 0; i < 1; i++) {
                xAxis = 14;
                for (int j = 0; j < 8; j++) {
                    batch.draw(whitePawn, xAxis, 81, pieceWidth, pieceHeight); 
                    xAxis = 80 + xAxis;
                }
            }
            // Draw black pawns
            for (int i = 0; i < 1; i++) {
                xAxis = 14;
                for (int j = 0; j < 8; j++) {
                    batch.draw(blackPawn, xAxis, 481, pieceWidth, pieceHeight); 
                    xAxis = 80 + xAxis;
                }
            }
            // Draw bishops
            pieceWidth = 70;
            batch.draw(whiteBishop, 168, 2, pieceWidth, pieceHeight);
            batch.draw(whiteBishop, 408, 2, pieceWidth, pieceHeight);
            batch.draw(blackBishop, 168, 562, pieceWidth, pieceHeight);
            batch.draw(blackBishop, 408, 562, pieceWidth, pieceHeight);

            // Draw knights
            pieceWidth = 64;
            batch.draw(whiteKnight, 90, 3, pieceWidth, pieceHeight);
            batch.draw(whiteKnight, 490, 3, pieceWidth, pieceHeight);
            batch.draw(blackKnight, 90, 563, pieceWidth, pieceHeight);
            batch.draw(blackKnight, 490, 563, pieceWidth, pieceHeight);

            // Draw rooks
            pieceWidth = 65;
            batch.draw(whiteRook, 10, 1, pieceWidth, pieceHeight);
            batch.draw(whiteRook, 570, 1, pieceWidth, pieceHeight);
            batch.draw(blackRook, 10, 561, pieceWidth, pieceHeight);
            batch.draw(blackRook, 570, 561, pieceWidth, pieceHeight);

            // Draw queens
            pieceWidth = 78;
            batch.draw(whiteQueen, 243, 2, pieceWidth, pieceHeight);
            batch.draw(blackQueen, 243, 562, pieceWidth, pieceHeight);

            // Draw kings
            pieceWidth = 74;
            batch.draw(whiteKing, 325, 2, pieceWidth, pieceHeight);
            batch.draw(blackKing, 325, 562, pieceWidth, pieceHeight);

            batch.end();
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
