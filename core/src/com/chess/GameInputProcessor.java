package com.chess;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

/**
 * Handles piece movement and menu control through keyboard input.
 * 
 * @author Noah Ewell
 */
public class GameInputProcessor extends InputAdapter {
    private GameScreen screen;

    /**
     * Constructor initializes screen.
     * 
     * @param screen	the game screen
     */
    public GameInputProcessor(GameScreen screen) {
        this.screen = screen;
    }

    /**
     * Handles keyboard events to control chess pieces and game state.
     * 
     * M 		- 	initiates move.
     * SPACE 	- 	confirms move
     * UP 		- 	takes cursor up
     * DOWN 	- 	takes cursor down
     * LEFT 	- 	takes cursor left
     * RIGHT 	- 	takes cursor right
     * ESCAPE 	-	cancels movement, also pause menu (see GameScreen)
     */
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Keys.M:
            	// starts move if piece is selected, and not already moving
                if (!screen.isMoving && screen.currentPiece == null) {
                    screen.currentPiece = screen.board.getPiece(screen.cursorX, screen.cursorY);
                    if (screen.currentPiece != null) {
                        screen.oldX = screen.cursorX;
                        screen.oldY = screen.cursorY;
                        screen.isMoving = true;
                    }
                }
                return true;
            case Keys.SPACE:
                if (screen.isMoving) {
                    screen.confirmMovement();
                }
                return true;
            case Keys.UP:
                screen.cursorY = Math.min(screen.cursorY + 1, 7);
                break;
            case Keys.DOWN:
                screen.cursorY = Math.max(screen.cursorY - 1, 0);
                break;
            case Keys.LEFT:
                screen.cursorX = Math.max(screen.cursorX - 1, 0);
                break;
            case Keys.RIGHT:
                screen.cursorX = Math.min(screen.cursorX + 1, 7);
                break;
            case Keys.ESCAPE:
                if (screen.isMoving) {
                    screen.currentPiece = null;
                    screen.isMoving = false;
                }
                return true;
        }
        return false;
    }
}

