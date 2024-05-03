package com.chess;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.HashMap;
import static com.chess.Chess.TILESIZE;

/**
 * Represents an instance of a drawn chess board on a <code>GameScreen</code>. 
 * Each <code>Piece</code> is stored in a two-dimensional array and then 
 * subsequently stored in a <code>HashMap</code> with corresponding textures. 
 * 
 * Original code was written by Joel Oliva, modifications have been made by
 * Noah Ewell to gain functionality with moving pieces.
 * 
 * @author Noah Ewell, Joel Oliva
 */
public class Board {
    private static Piece[][] board;
    private final HashMap<Piece, TextureRegion> sprites;
    private final ShapeRenderer shapeRenderer;
    private final SpriteBatch batch;
    private GameScreen screen;

    /**
     * Constructor takes in a <code>GameScreen</code>, reads sprites in as textures
     * from a .png. Creates and stores sprites as pieces in a hashmap.
     * Initializes the board with starting locations for pieces.
     * 
     * @param screen the screen being rendered
     */
    public Board(GameScreen screen) {
        this.screen = screen;
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();

        Texture spritesheet = new Texture("images/spritesheet.png");
        sprites = new HashMap<>();
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 7; j++) {
                sprites.put(Piece.values()[(7 * i) + j], new TextureRegion
                		(spritesheet, j * TILESIZE, i * TILESIZE, TILESIZE, TILESIZE));
            }
        }
        initializeBoard();
    }

    /**
     * Initializes the chess board with pieces in the correct starting locations.
     * 
     * First dimension is y coordinates (bottom to top).
     * Second dimension is x coordinates (left to right).
     */
    private void initializeBoard() {
        setBoard(new Piece[8][8]);
        getBoard()[0] = new Piece[]{Piece.BR, Piece.BN, 
        		Piece.BB, Piece.BK, Piece.BQ, Piece.BB, Piece.BN, Piece.BR};
        for (int i = 0; i < 8; i++) {
            getBoard()[1][i] = Piece.BP;
        }
        for (int i = 0; i < 8; i++) {
            getBoard()[6][i] = Piece.WP;
        }
        getBoard()[7] = new Piece[]{Piece.WR, Piece.WN, 
        		Piece.WB, Piece.WQ, Piece.WK, Piece.WB, Piece.WN, Piece.WR};
    }

    /**
     * Renders the entire game board including tiles and sprites, adjusting 
     * for cursor (x) visibility and movement.
     * 
     * @param camera 	the camera.
     * @param cursorX 	the x-coordinate of the cursor.
     * @param cursorY 	the y-coordinate of the cursor.
     * @param isMoving 	whether the cursor is moving a piece.
     */
    public void draw(Camera camera, int cursorX, int cursorY, boolean isMoving) {
        drawTiles(camera);
        drawSprites(camera, cursorX, cursorY, isMoving);
    }

    /**
     * Draws the chessboard's tiles.
     * This method was fully written by Joel Oliva.
     * 
     * @param camera 	the camera.
     */
    private void drawTiles(Camera camera) {
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        float yAxis = 80;
        float xAxis;
        for (int i = 0; i < 4; i++) {
            xAxis = 0;
            for (int j = 0; j < 4; j++) {
                shapeRenderer.rect(xAxis, yAxis, 80, 80);
                xAxis = 160 + xAxis;
            }
            yAxis = yAxis + 160;
        }
        yAxis = 0;
        for (int i = 0; i < 4; i++) {
            xAxis = 80;
            for (int j = 0; j < 4; j++) {
                shapeRenderer.rect(xAxis, yAxis, 80, 80);
                xAxis = 160 + xAxis;
            }
            yAxis = yAxis + 160;
        }
        shapeRenderer.end();
    }

    /**
     * Draws the chessboard's pieces. Checks whether or not to draw if moving.
     * 
     * @param camera 	the camera.
     * @param cursorX 	the x-coordinate of the cursor.
     * @param cursorY 	the y-coordinate of the cursor.
     * @param isMoving 	whether the cursor is moving a piece.
     */
    private void drawSprites(Camera camera, int cursorX, int cursorY, boolean isMoving) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int i = 7; i >= 0; i--) {
            for (int j = 0; j < getBoard()[0].length; j++) {
                if (sprites.get(getBoard()[i][j]) != null) {
                    if (!(i == cursorY && j == cursorX && isMoving)) {
                        batch.draw(sprites.get(getBoard()[i][j]), j * TILESIZE, i * TILESIZE);
                    }
                }
            }
        }
        batch.end();
    }

    /**
     * Draws the cursor on the board if it's visible.
     * 
     * @param camera 			the camera.
     * @param cursorX 			the x-coordinate to draw the cursor.
     * @param cursorY 			the y-coordinate to draw the cursor.
     * @param isCursorVisible 	whether the cursor should be drawn.
     */
    public void drawCursor(Camera camera, int x, int y, boolean isCursorVisible) {
        if (!isCursorVisible) return;

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        TextureRegion cursorTexture = sprites.get(Piece.WX);
        if (cursorTexture != null) {
            batch.draw(cursorTexture, x * TILESIZE, y * TILESIZE);
        }
        batch.end();
    }

    /**
     * Disposes resources.
     */
    public void dispose() {
        shapeRenderer.dispose();
        batch.dispose();
    }

    /**
     * Gets the board.
     * 
     * @return board
     */
    public static Piece[][] getBoard() {
        return board;
    }

    /**
     * Sets the board.
     * 
     * @param board
     */
    public void setBoard(Piece[][] board) {
        Board.board = board;
    }

    /**
     * Gets the board's current pieces
     * 
     * @param x			x-coordinate of piece
     * @param y			y-coordinate of piece
     * @return board
     */
    public Piece getPiece(int x, int y) {
        return board[y][x];
    }

    /**
     * Sets the board's new pieces
     * 
     * @param x			x-coordinate of piece
     * @param y			y-coordinate of piece
     * @return board
     */
    public void setPiece(int x, int y, Piece piece) {
        board[y][x] = piece;
    }
}

