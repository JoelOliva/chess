package com.chess;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.HashMap;

import static com.chess.Chess.TILESIZE;

public class Board {
    private enum Piece {WQ, WK, WB, WN, WR, WP, BQ, BK, BB, BN, BR, BP}

    private Piece[][] board;
    private final HashMap<Piece, TextureRegion> sprites;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;

    public Board() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        Texture spritesheet = new Texture("images/spritesheet.png");
        sprites = new HashMap<>(12);
        for (int i = 0; i < 2; i++)
            for (int j = 0; j < 6; j++)
                sprites.put(Piece.values()[(6 * i) + j], new TextureRegion(spritesheet, j * TILESIZE, i * TILESIZE, TILESIZE, TILESIZE));

        initializeBoard();
    }

    // TODO: Randomize which player initially gets white or black
    private void initializeBoard() {
        board = new Piece[8][8];
        board[0] = new Piece[]{Piece.BR, Piece.BN, Piece.BR, Piece.BK, Piece.BQ, Piece.BR, Piece.BN, Piece.BR};
        for (int i = 0; i < 8; i++)
            board[1][i] = Piece.BP;
        for (int i = 0; i < 8; i++)
            board[6][i] = Piece.WP;
        board[7] = new Piece[]{Piece.WR, Piece.WN, Piece.WR, Piece.WK, Piece.WQ, Piece.WR, Piece.WN, Piece.WR};
    }

    private void drawTiles(Camera camera) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        //renders one set of white tiles
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
    }

    private void drawSprites(Camera camera) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int i = 7; i >= 0; i--)
            for (int j = 0; j < board[0].length; j++)
                if (sprites.get(board[i][j]) != null)
                    batch.draw(sprites.get(board[i][j]), j * TILESIZE, i * TILESIZE);

        batch.end();
    }

    public void draw(Camera camera) {
        drawTiles(camera);
        drawSprites(camera);
    }

    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
    }
}
