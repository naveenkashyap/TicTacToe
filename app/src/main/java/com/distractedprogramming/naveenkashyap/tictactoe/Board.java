package com.distractedprogramming.naveenkashyap.tictactoe;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Naveen
 *
 * Board
 * Handles the internal representation of the tic tac toe board
 */

public class Board {
    public final int DIMEN = 3;
    private final char EMPTY = ' ';
    protected final char USER = 'X';
    protected final char COMP = 'O';
    private final String LOGTAG = "BOARD";
    private char[][] mBoard = new char[DIMEN][DIMEN];

    public Board(){
        setBoard();
        log(Log.DEBUG, "Board constructed!");
    }

    public boolean isEmptyAt(int row, int col){
        return mBoard[row][col] != USER && mBoard[row][col] != COMP;
    }

    public boolean isDraw(){
        for (int row = 0; row < DIMEN; row++) {
            for (int col = 0; col < DIMEN; col++) {
                if (this.isEmptyAt(row,col))
                    return false;
            }
        }
        return !isUserWin() && !isCompWin();
    }

    public boolean isWin(char player){
        return horizontalWin(player) || verticalWin(player) || diagonalWin(player);
    }

    public boolean isUserWin(){
        return horizontalWin(USER) || verticalWin(USER) || diagonalWin(USER);
    }

    public boolean isCompWin(){
        return horizontalWin(COMP) || verticalWin(COMP) || diagonalWin(COMP);
    }

    private boolean horizontalWin(char player) {
        return mBoard[0][0] == player && mBoard[0][1] == player && mBoard[0][2] == player
                || mBoard[1][0] == player && mBoard[1][1] == player && mBoard[1][2] == player
                || mBoard[2][0] == player && mBoard[2][1] == player && mBoard[2][2] == player;
    }

    private boolean verticalWin(char player) {
        return mBoard[0][0] == player && mBoard[1][0] == player && mBoard[2][0] == player
                || mBoard[0][1] == player && mBoard[1][1] == player && mBoard[2][1] == player
                || mBoard[0][2] == player && mBoard[1][2] == player && mBoard[2][2] == player;
    }

    private boolean diagonalWin(char player){
        return mBoard[0][0] == player && mBoard[1][1] == player && mBoard[2][2] == player
                || mBoard[0][2] == player && mBoard[1][1] == player && mBoard[2][0] == player;
    }

    public void undoClick(int row, int col){
        log(Log.DEBUG, "Undoing click at (" + row + ", " + col + ")");
        mBoard[row][col] = EMPTY;
    }

    public void click(int row, int col, char player){
        mBoard[row][col] = player;
    }

    /*
    userClicked(int, int)
    description: reflects action on internal board
    @param {int row}: row coordinate
    @param {int col}: column coordinate
    @return none
     */
    public void userClicked(int row, int col){
        // set row,col to USER
        log(Log.DEBUG, "User clicked (" + row + ", " + col + ")");
        mBoard[row][col] = USER;
    }

    /*
    compClicked(int, int)
    description: reflects action on internal board
    @param {int row}: row coordinate
    @param {int col}: column coordinate
    @return none
     */
    public void compClicked(int row, int col){
        log(Log.DEBUG, "Computer clicked (" + row + ", " + col + ")");
        mBoard[row][col] = COMP;
    }

    /*
    setBoard()
    description: prepares internal board and UI for a new game
    @return none
     */
    public void setBoard(){
        for (int row = 0; row < DIMEN; row++) {
            for (int col = 0; col < DIMEN; col++) {
                mBoard[row][col] = EMPTY;
            }
        }
        log(Log.DEBUG, "Board set for new game!");
    }

    private void log(int logLevel, String msg){
        Log.println(logLevel, LOGTAG, msg);
    }

}
